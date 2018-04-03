package com.alipay.parkour.asyncmd.manager.impl;

import com.alipay.parkour.asyncmd.manager.AsynControllerService;
import com.alipay.parkour.utils.ReflectionUtils;
import com.alipay.parkour.context.ParkourApplicationContext;
import com.alipay.parkour.asyncmd.dal.AsynExecutorCmdDAO;
import com.alipay.parkour.asyncmd.dal.dataObject.AsynExecutorCmdObject;
import com.alipay.parkour.asyncmd.manager.AsynExecutorService;
import com.alipay.parkour.asyncmd.model.*;
import com.alipay.parkour.asyncmd.model.AsynCmdDefinition.Builder;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String DEFAULT_TABLE_NAME = "asyn_executor_cmd";

    private static String ASYN_EXECUTOR_CMD_TABLE_NAME;

    private AsynControllerService asynControllerService;

    /**
     * 将带了命令类型的类获取到,类似于springmvc web里的controller
     */
    public void start() {

        ASYN_EXECUTOR_CMD_TABLE_NAME = StringUtils.isNotEmpty(tableNamePrefix) ? ASYN_EXECUTOR_CMD_TABLE_NAME =
            tableNamePrefix + "_" + DEFAULT_TABLE_NAME : DEFAULT_TABLE_NAME;

        logger.info("初始化組裝命令表名:{}", ASYN_EXECUTOR_CMD_TABLE_NAME);
    }

    /**
     * @param cmdType
     */
    @Override
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

        AsynCmdDefinition executedConfig = asynControllerService.getAsynCmdDefinitionMap().get(cmdType);

        List<AsynExecutorCmdObject> asynExecutorCmdObjects = asynExecutorCmdDAO.selectByCmdType(cmdType,
            executedConfig.getSize(), ASYN_EXECUTOR_CMD_TABLE_NAME);

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

    private <T extends AsynExecutorCmd> void beforProcess(T cmd) {
    }

    private <T extends AsynExecutorCmd> void afterProcess(T cmd) {
    }

    /**
     * @param cmd
     * @param <T>
     */
    private <T extends AsynExecutorCmd> void toWork(T cmd) {

        AsynCmdDefinition asynCmdDefinition = asynControllerService.getAsynCmdDefinitionMap().get(cmd.getCmdType());

        asynCmdDefinition.getExecutor().submit(new CmdExecutor(asynCmdDefinition,cmd));

    }

    /**
     *
     * 命令执行具体业务逻辑
     *
     * @param <T>
     */
    private class CmdExecutor<T extends AsynExecutorCmd> implements Runnable {
        private AsynCmdDefinition asynCmdDefinition;
        T cmd;
        CmdExecutor(AsynCmdDefinition asynCmdDefinition,T cmd){
            this.asynCmdDefinition=asynCmdDefinition;
            this.cmd=cmd;
        }

        @Override
        public void run() {

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

    public void setTransactionNewTemplate(TransactionTemplate transactionNewTemplate) {
        this.transactionNewTemplate = transactionNewTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setTableNamePrefix(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix;
    }

    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    public void setAsynControllerService(AsynControllerService asynControllerService) {
        this.asynControllerService = asynControllerService;
    }
}
