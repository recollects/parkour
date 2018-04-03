package com.alipay.parkour.asyncmd.manager.impl;

import com.alipay.parkour.asyncmd.manager.AbstractSchdulerExecuter;
import com.alipay.parkour.asyncmd.manager.AsynControllerService;
import com.alipay.parkour.asyncmd.manager.AsynExecutorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 上午11:30
 */
public class AsynSchdulerExecuter extends AbstractSchdulerExecuter {

    @Autowired
    private AsynExecutorService asynExecutorService;

    @Autowired
    private AsynControllerService asynControllerService;

    @Override
    protected void doExecute() {
        //执行逻辑
        List<String> asynExecutedCmds = asynControllerService.getAsynExecutedCmds();



    }
}
