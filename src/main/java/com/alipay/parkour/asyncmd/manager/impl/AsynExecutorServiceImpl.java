package com.alipay.parkour.asyncmd.manager.impl;

import com.alipay.parkour.asyncmd.common.utils.SystemUtils;
import com.alipay.parkour.asyncmd.dal.AsynExecutorCmdDAO;
import com.alipay.parkour.asyncmd.dal.dataObject.AsynExecutorCmdDO;
import com.alipay.parkour.asyncmd.manager.AsynControllerService;
import com.alipay.parkour.asyncmd.manager.AsynExecutorService;
import com.alipay.parkour.asyncmd.model.AsynCmdDefinition;
import com.alipay.parkour.asyncmd.model.AsynCmdStatusEnum;
import com.alipay.parkour.asyncmd.model.AsynExecutorCmd;
import com.alipay.parkour.asyncmd.model.query.AsynExecutorCmdQuery;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

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
    @Autowired
    private AsynExecutorCmdDAO asynExecutorCmdDAO;

    /**
     * 事务模板
     */
    @Autowired
    private TransactionTemplate transactionNewTemplate;

    /**
     * 事务模板
     */
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 线程池
     */
    @Autowired
    protected ThreadPoolTaskExecutor executor;

    private static final String DEFAULT_TABLE_NAME = "asyn_executor_cmd";

    private static String ASYN_EXECUTOR_CMD_TABLE_NAME;

    @Autowired
    private AsynControllerService asynControllerService;

    /**
     * 队列最大容量
     */
    private static final Integer MAX_QUEUE_CAPACITY = 2 << 16;

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

        //TODO 需要考虑任务的优先级[折中处理方式,优先级高的线程数成比例,例如,10,5优先级,对应的线程数,就是高是低的一倍线程]
        AsynExecutorCmdQuery cmdObject = new AsynExecutorCmdQuery();
        cmdObject.setPageSize(executedConfig.getSize());
        cmdObject.setCmdType(cmdType);
        cmdObject.setTableName(ASYN_EXECUTOR_CMD_TABLE_NAME);
        cmdObject.setStatus(AsynCmdStatusEnum.INIT.name());

        List<AsynExecutorCmdDO> asynExecutorCmdDOs = asynExecutorCmdDAO.selectByCmdType(cmdObject);

        List<AsynExecutorCmd> asynExecutorCmds = convertTOCmd(asynExecutorCmdDOs);

        transactionNewTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                batUpdateStatus(asynExecutorCmds.toArray(new AsynExecutorCmd[asynExecutorCmds.size()]),AsynCmdStatusEnum.PROCESSING);
            }
        });

        Iterators.all(asynExecutorCmds.iterator(), new Predicate<AsynExecutorCmd>() {
            @Override
            public boolean apply(AsynExecutorCmd asynExecutorCmd) {
                try {
                    executor.execute(new AsynExecutorRunner(asynExecutorCmd));
                } catch (RejectedExecutionException ree) {
                    logger.error("线程池任务已满,命令类型:{}", asynExecutorCmd.getCmdType(), ree);
                    asynExecutorCmd.setExceptionContext(ree.toString());
                    batUpdateStatus(ArrayUtils.toArray(asynExecutorCmd),AsynCmdStatusEnum.INIT);
                } catch (Exception e) {
                    logger.error("任务执行异常,asynExecutorCmd:{}", asynExecutorCmd, e);
                    asynExecutorCmd.setExceptionContext(e.toString());
                    batUpdateStatus(ArrayUtils.toArray(asynExecutorCmd),AsynCmdStatusEnum.EXCEPTION);
                }
                return true;
            }
        });

        batUpdateStatus(asynExecutorCmds.toArray(new AsynExecutorCmd[asynExecutorCmds.size()]),AsynCmdStatusEnum.FINISHED);

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
     * @param asynExecutorCmds
     */
    private void batUpdateStatus(AsynExecutorCmd[] asynExecutorCmds,AsynCmdStatusEnum statusEnum) {

        if (ArrayUtils.isEmpty(asynExecutorCmds)) return;

        List<AsynExecutorCmd> cmdList = Arrays.asList(asynExecutorCmds);

        Iterators.all(cmdList.iterator(), new Predicate<AsynExecutorCmd>() {
            @Override
            public boolean apply(AsynExecutorCmd input) {
                AsynExecutorCmdDO cmdDO = new AsynExecutorCmdDO();
                cmdDO.setTableName(ASYN_EXECUTOR_CMD_TABLE_NAME);
                cmdDO.setId(input.getId());
                cmdDO.setStatus(statusEnum.name());
                cmdDO.setModifier(SystemUtils.getHostNameNew());

                asynExecutorCmdDAO.update(cmdDO);

                return true;
            }
        });

    }


    /**
     * @param cmd
     * @param <T>
     */
    private <T extends AsynExecutorCmd> void toWork(T cmd) {

        AsynCmdDefinition asynCmdDefinition = asynControllerService.getAsynCmdDefinitionMap().get(cmd.getCmdType());

        //TODO 极端情况出现任务添加不进去
        asynCmdDefinition.getExecutor().submit(new CmdExecutor(asynCmdDefinition, cmd));

    }

    /**
     * 命令执行具体业务逻辑
     *
     * @param <T>
     */
    private class CmdExecutor<T extends AsynExecutorCmd> implements Runnable {
        private AsynCmdDefinition asynCmdDefinition;
        T cmd;

        CmdExecutor(AsynCmdDefinition asynCmdDefinition, T cmd) {
            this.asynCmdDefinition = asynCmdDefinition;
            this.cmd = cmd;
            logger.debug("开始处理业务逻辑,asynCmdDefinition:{},命令数据:{}", asynCmdDefinition, cmd);
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
     * @param asynExecutorCmdDOs
     * @return
     */
    private List<AsynExecutorCmd> convertTOCmd(List<AsynExecutorCmdDO> asynExecutorCmdDOs) {

        List<AsynExecutorCmd> cmds = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(asynExecutorCmdDOs)) {
            Iterators.all(asynExecutorCmdDOs.iterator(), new Predicate<AsynExecutorCmdDO>() {
                AsynExecutorCmd cmd = null;

                @Override
                public boolean apply(AsynExecutorCmdDO input) {
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
                    cmds.add(cmd);
                    return true;
                }
            });

        }
        return cmds;
    }

    public void setTableNamePrefix(String tableNamePrefix) {
        this.tableNamePrefix = tableNamePrefix;
    }

}
