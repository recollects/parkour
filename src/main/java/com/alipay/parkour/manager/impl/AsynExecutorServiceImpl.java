package com.alipay.parkour.manager.impl;

import com.alipay.parkour.common.utils.ReflectionUtils;
import com.alipay.parkour.context.ParkourApplicationContext;
import com.alipay.parkour.dal.AsynExecutorCmdDAO;
import com.alipay.parkour.dal.dataObject.AsynExecutorCmdObject;
import com.alipay.parkour.manager.AsynExecutorService;
import com.alipay.parkour.model.*;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
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
import java.util.Set;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 上午9:39
 */
@Service
public class AsynExecutorServiceImpl implements AsynExecutorService {

    /**
     * 命令容器[包涵所有的命令类型]
     */
    private Map<AsynExecutedHandle, Method> asynExecutedCmdMap = Maps.newConcurrentMap();
    private Map<String, AsynExecutedConfig> asynCmdConfigMap = Maps.newConcurrentMap();
    private Map<String, Object> asynCmdObjectMap = Maps.newConcurrentMap();

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

    /**线程池*/
    protected ThreadPoolTaskExecutor executor;

    /**
     * 将带了命令类型的类获取到,类似于springmvc web里的controller
     */
    public void start() {
        Map<String, Object> beansWithAnnotation = ParkourApplicationContext.getApplicationContext().getBeansWithAnnotation(AsynExecuted.class);

        if (MapUtils.isNotEmpty(beansWithAnnotation)) {
            Set<Map.Entry<String, Object>> entries = beansWithAnnotation.entrySet();

            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();

            while (iterator.hasNext()) {

                addToAsynCmdMap(iterator.next());

            }
        }
    }

    /**
     *
     * @param cmdType
     */
    protected void pushCmdToExecuter(String cmdType) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult(){

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //加事务控制
                doExecute(cmdType);
            }
        });
    }

    /**
     *
     * @param cmdType
     */
    private void doExecute(String cmdType) {

        AsynExecutedConfig executedConfig = asynCmdConfigMap.get(cmdType);

        List<AsynExecutorCmdObject> asynExecutorCmdObjects = asynExecutorCmdDAO.selectByCmdType(cmdType, executedConfig.getSize());

        List<AsynExecutorCmd> asynExecutorCmds = convertTOCmd(asynExecutorCmdObjects);



        transactionNewTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //TODO 将任务状态修改为PROCESSING状态
            }
        });

        for(AsynExecutorCmd cmd:asynExecutorCmds){
            //TODO 存在线程池满状态,如果没有添加成功,将状态重置
            executor.execute(new AsynExecutorRunner(cmd));
        }
    }

    /**
     *
     */
    class AsynExecutorRunner<T extends AsynExecutorCmd> implements Runnable{

        private T cmd;

        AsynExecutorRunner(T cmd){
            this.cmd=cmd;
        }

        @Override
        public void run() {
            beforProcess(cmd);
            toWork(cmd);
            afterProcess(cmd);
        }
    }

    private <T extends AsynExecutorCmd> void beforProcess(T cmd){}
    private <T extends AsynExecutorCmd> void afterProcess(T cmd){}

    /**
     *
     * @param cmd
     * @param <T>
     */
    private <T extends AsynExecutorCmd> void toWork(T cmd){

        Method method = asynExecutedCmdMap.get(cmd.getCmdType());

        Object obj = asynCmdObjectMap.get(cmd.getCmdType());

        try {
            method.invoke(obj,cmd);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
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

        Iterators.any(methods.iterator(), new Predicate<Method>() {
            @Override
            public boolean apply(Method input) {

                AsynExecutedHandle executedHandle = input.getAnnotation(AsynExecutedHandle.class);

                if (executedHandle != null) {
                    if (asynExecutedCmds.contains(executedHandle.value())) {
                        throw new RuntimeException(executedHandle.value() + "命令类型存在重复...");
                    }
                    asynExecutedCmdMap.put(executedHandle, input);
                    AsynExecutedConfig config = new AsynExecutedConfig();
                    config.setCmdType(executedHandle.value());
                    config.setSize(executedHandle.size());
                    asynCmdConfigMap.put(executedHandle.value(), config);
                    asynCmdObjectMap.put(executedHandle.value(),value);
                    asynExecutedCmds.add(executedHandle.value());
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
                    cmd.setExecuteTimes(input.getExecuteTimes());
                    cmd.setHostname(input.getHostname());
                    cmd.setId(input.getId());
                    cmd.setNextExecuteTime(input.getNextExecuteTime());
                    cmd.setStatus(AsynExecutorCmdEnum.valueOf(input.getStatus()));
                    return false;
                }
            });

        }
        return cmds;
    }


    public Map<AsynExecutedHandle, Method> getAsynExecutedCmdMap() {
        return asynExecutedCmdMap;
    }

    public void clear() {
        asynExecutedCmdMap.clear();
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
