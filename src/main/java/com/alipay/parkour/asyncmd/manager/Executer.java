package com.alipay.parkour.asyncmd.manager;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月01日 上午11:32
 */
public interface Executer {

    /**
     *
     * @param businessKey
     * @throws Exception
     */
    public void execute(String businessKey) throws Exception;
}
