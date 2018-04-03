package com.alipay.parkour.bean;

import com.alipay.parkour.asyncmd.model.AsynController;
import com.alipay.parkour.asyncmd.model.AsynWork;
import com.alipay.parkour.asyncmd.model.AsynExecutorCmd;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 下午2:40
 */
@AsynController
public class DemoAAsynExecutor {


    @AsynWork("TP_S_1000:DEMO_A_ASYNEXECUTOR")
    public void doWork(AsynExecutorCmd cmd) {
        System.out.println("DEMO   B  .....!!!!"+cmd);
    }

}
