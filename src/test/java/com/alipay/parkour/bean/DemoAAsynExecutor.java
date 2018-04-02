package com.alipay.parkour.bean;

import com.alipay.parkour.asyncmd.model.AsynExecuted;
import com.alipay.parkour.asyncmd.model.AsynExecutedHandle;
import com.alipay.parkour.asyncmd.model.AsynExecutorCmd;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 下午2:40
 */
@AsynExecuted
public class DemoAAsynExecutor {


    @AsynExecutedHandle("TP_S_1000:DEMO_B_ASYNEXECUTOR")
    public void doWork(AsynExecutorCmd cmd) {
        System.out.println("DEMO   B  .....!!!!"+cmd);
    }

}
