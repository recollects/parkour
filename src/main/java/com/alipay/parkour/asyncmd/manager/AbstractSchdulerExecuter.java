package com.alipay.parkour.asyncmd.manager;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月03日 下午10:23
 */
public abstract class AbstractSchdulerExecuter implements Executer{

    @Override
    public void execute(String businessKey) throws Exception {

        doExecute();


    }

    protected abstract void doExecute();


}
