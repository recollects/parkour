package com.alipay.parkour.manager.impl;

import com.alipay.parkour.manager.AsynExecutorService;
import com.alipay.parkour.manager.Executer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 上午11:30
 */
public class AsynSchdulerExecuter implements Executer{

    @Autowired
    private AsynExecutorService asynExecutorService;

    @Override
    public void execute(String businessKey) throws Exception {

    }
}
