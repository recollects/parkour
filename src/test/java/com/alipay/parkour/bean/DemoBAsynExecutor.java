package com.alipay.parkour.bean;

import com.alipay.parkour.model.AsynExecuted;
import com.alipay.parkour.model.AsynExecutedHandle;
import com.alipay.parkour.model.AsynExecutorCmd;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 下午2:40
 */
@AsynExecuted
public class DemoBAsynExecutor {

    @AsynExecutedHandle("TP_S_1000:DEMO_B_ASYNEXECUTOR")
    public void doWork(AsynExecutorCmd cmd){
        System.out.println("DEMO   A  .....!!!!"+cmd.toString());
    }

}
