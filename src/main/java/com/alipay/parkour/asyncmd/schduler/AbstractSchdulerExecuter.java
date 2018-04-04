package com.alipay.parkour.asyncmd.schduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月03日 下午10:23
 */
public abstract class AbstractSchdulerExecuter implements Executer {

    /**
     * logger
     */
    protected static final Logger logger = LoggerFactory.getLogger(AbstractSchdulerExecuter.class);

    @Override
    public void execute() throws Exception {

        /**
         * FIXME jiadong 存在多台机器调度,待优化
         */
        if (canProcess()) {
            logger.debug("进入任务调度处理");
            process();
        }

    }

    protected abstract boolean canProcess();

    protected abstract void process();


}
