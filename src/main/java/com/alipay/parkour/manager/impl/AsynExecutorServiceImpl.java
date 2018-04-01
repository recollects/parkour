package com.alipay.parkour.manager.impl;

import com.alipay.parkour.common.utils.ReflectionUtils;
import com.alipay.parkour.context.ParkourApplicationContext;
import com.alipay.parkour.dal.AsynExecutorCmdDAO;
import com.alipay.parkour.dal.dataObject.AsynExecutorCmdObject;
import com.alipay.parkour.manager.AsynExecutorService;
import com.alipay.parkour.model.*;
import com.alipay.parkour.model.AsynCmdDefinition.Builder;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 上午9:39
 */
@Service
public class AsynExecutorServiceImpl implements AsynExecutorService {

    /**
     * logger
     */
    protected static final Logger logger = LoggerFactory.getLogger(AsynExecutorServiceImpl.class);

    /**
     * 命令容器[包涵所有的命令类型]
     */
    private Map<String, AsynCmdDefinition> asynCmdDefinitionMap = Maps.newConcurrentMap();

    /**
     * 命令列表
     */
    private List<String> asynExecutedCmds = Lists.newArrayList();

    /**
     * 数据库表前缀
     */
    private String tableNamePrefix;

    /**
     * DAO
     */
    private AsynExecutorCmdDAO asynExecutorCmdDAO;

    /**
     * 事务模板
     */
    private TransactionTemplate transactionNewTemplate;

    /**
     * 事务模板
     */
    private TransactionTemplate transactionTemplate;

    /**
     * 线程池
     */
    protected ThreadPoolTaskExecutor executor;

    /**
     * 将带了命令类型的类获取到,类似于springmvc web里的controller
     */
    public void start() {
        Map<String, Object> beansWithAnnotation = ParkourApplicationContext.getApplicationContext()
            .getBeansWithAnnotation(AsynExecuted.class);

        if (MapUtils.isNotEmpty(beansWithAnnotation)) {
            Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();

            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();

            Iterators.all(iterator, new Predicate<Entry<String, Object>>() {
                @Override
                public boolean apply(Entry<String, Object> stringObjectEntry) {
                    addToAsynCmdMap(iterator.next());
                    return false;
                }
            });

            logger.info("装载成功命令列表:{}", asynExecutedCmds);
        }
    }

    /**
     * @param cmdType
     */
    public void pushCmdToExecuter(String cmdType) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //加事务控制
                doExecute(cmdType);
            }
        });
    }

    /**
     * @param cmdType
     */
    private void doExecute(String cmdType) {

        AsynCmdDefinition executedConfig = asynCmdDefinitionMap.get(cmdType);

        List<AsynExecutorCmdObject> asynExecutorCmdObjects = asynExecutorCmdDAO.selectByCmdType(cmdType,
            executedConfig.getSize(), tableNamePrefix);

        List<AsynExecutorCmd> asynExecutorCmds = convertTOCmd(asynExecutorCmdObjects);

        transactionNewTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //TODO 将任务状态修改为PROCESSING状态
            }
        });

        Iterators.all(asynExecutorCmds.iterator(), new Predicate<AsynExecutorCmd>() {
            @Override
            public boolean apply(AsynExecutorCmd asynExecutorCmd) {
                //TODO 存在线程池满状态,如果没有添加成功,将状态重置
                executor.execute(new AsynExecutorRunner(asynExecutorCmd));
                return false;
            }
        });

    }

    /**
     *
     */
    class AsynExecutorRunner<T extends AsynExecutorCmd> implements Runnable {

        private T cmd;

        AsynExecutorRunner(T cmd) {
            this.cmd = cmd;
        }

        @Override
        public void run() {
            beforProcess(cmd);
            toWork(cmd);
            afterProcess(cmd);
        }
    }

    private <T extends AsynExecutorCmd> void beforProcess(T cmd) {}

    private <T extends AsynExecutorCmd> void afterProcess(T cmd) {}

    /**
     * @param cmd
     * @param <T>
     */
    private <T extends AsynExecutorCmd> void toWork(T cmd) {

        AsynCmdDefinition asynCmdDefinition = asynCmdDefinitionMap.get(cmd.getCmdType());

        Method method = asynCmdDefinition.getMethod();

        Object obj = asynCmdDefinition.getObject();

        try {
            method.invoke(obj, cmd);
        } catch (IllegalAccessException e) {
            //TODO 将异常信息写入这条任务的exceptionContext里
            logger.error("执行任务方法异常，cmd:{}", cmd, e);
        } catch (InvocationTargetException e) {
            //TODO 将异常信息写入这条任务的exceptionContext里,将这里的异常信息拼接进去。然后将状态改成异常状态
            //然后根据重试次数来进行重试
            logger.error("执行任务方法异常，cmd:{}", cmd, e);
        } catch (Error e) {
            //TODO 同上，将任务状态改为ERROR
            logger.error("执行任务方法错误，cmd:{}", cmd, e);
        }
    }

    /**
     * 对每个标识的AsynExecuted注解的类解析命令出来
     *
     * @param entry
     */
    private void addToAsynCmdMap(Map.Entry<String, Object> entry) {
        //标识了AsynExecuted注解的类
        Object value = entry.getValue();

        List<Method> methods = ReflectionUtils.findMethod(value.getClass(), true);

        Iterators.all(methods.iterator(), new Predicate<Method>() {
            @Override
            public boolean apply(Method input) {

                AsynExecutedHandle executedHandle = input.getAnnotation(AsynExecutedHandle.class);

                if (executedHandle != null) {
                    if (asynExecutedCmds.contains(executedHandle.value())) {
                        throw new RuntimeException(executedHandle.value() + "命令类型存在重复...");
                    }

                    //解析对任务的配置信息
                    AsynCmdDefinition builder = new Builder()
                        .cmdType(executedHandle.value())
                        .coreSize(executedHandle.coreSize())
                        .size(executedHandle.size())
                        .maxSize(executedHandle.maxSize())
                        .object(value)
                        .method(input)
                        .backup(executedHandle.backup())
                        .builder();

                    //存在map里，KEY是命令类型，VALUE是这条命令的配置信息
                    asynCmdDefinitionMap.put(executedHandle.value(), builder);
                    asynExecutedCmds.add(executedHandle.value());
                    logger.debug("初始装载命令信息,cmdType:{},cmdDefinition:{}", executedHandle.value(), builder);
                }

                return false;
            }
        });
    }

    /**
     * @param asynExecutorCmdObjects
     * @return
     */
    private List<AsynExecutorCmd> convertTOCmd(List<AsynExecutorCmdObject> asynExecutorCmdObjects) {

        List<AsynExecutorCmd> cmds = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(asynExecutorCmdObjects)) {
            Iterators.any(asynExecutorCmdObjects.iterator(), new Predicate<AsynExecutorCmdObject>() {
                AsynExecutorCmd cmd = null;

                @Override
                public boolean apply(AsynExecutorCmdObject input) {
                    cmd = new AsynExecutorCmd();
                    cmd.setBusinessNo(input.getBusinessNo());
                    cmd.setCmdType(input.getCmdType());
                    cmd.setContext(input.getContext());
                    cmd.setExceptionContext(input.getExceptionContext());
                    cmd.setRetryCount(input.getRetryCount());
                    cmd.setHostname(input.getHostName());
                    cmd.setId(input.getId());
                    cmd.setNextExecuteTime(input.getNextExecuteTime());
                    cmd.setStatus(AsynCmdStatusEnum.valueOf(input.getStatus()));
                    return false;
                }
            });

        }
        return cmds;
    }

    @Override
    public List<String> getAsynExecutedCmds() {
        return asynExecutedCmds;
    }

    public Map<String, AsynCmdDefinition> getAsynCmdDefinitionMap() {
        return asynCmdDefinitionMap;
    }

    public void clear() {
        asynCmdDefinitionMap.clear();
    }

    public void setTransactionNewTemplate(TransactionTemplate transactionNewTemplate) {
        this.transactionNewTemplate = transactionNewTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setTableNamePrefix(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix;
    }

}
