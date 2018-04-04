package com.alipay.parkour.asyncmd.schduler.impl;

import com.alipay.parkour.asyncmd.manager.AsynControllerService;
import com.alipay.parkour.asyncmd.manager.AsynExecutorService;
import com.alipay.parkour.asyncmd.schduler.AbstractSchdulerExecuter;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 上午11:30
 */
@Service
public class AsynSchdulerExecuterImpl extends AbstractSchdulerExecuter {

    @Autowired
    private AsynExecutorService asynExecutorService;

    @Autowired
    private AsynControllerService asynControllerService;

    @Override
    protected boolean canProcess() {

        //TODO 多台机器查询任务重复,需要考虑考虑

        return true;
    }

    @Override
    protected void process() {
        //TODO 这里调度需要折中考虑,如果调度过快,命令太快,会调试积压.如果调度太慢命令少则任务延迟过久

        //执行逻辑
        //需要考虑优先级来触发命令调度
        List<String> asynExecutedCmds = asynControllerService.getAsynExecutedCmds();

        Iterators.all(asynExecutedCmds.iterator(), new Predicate<String>() {
            @Override
            public boolean apply(String input) {

                asynExecutorService.pushCmdToExecuter(input);
                return true;
            }
        });

        System.out.println("命令:" + asynExecutedCmds + "正常在调度...");

    }
}
