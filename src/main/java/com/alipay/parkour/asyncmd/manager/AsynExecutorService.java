package com.alipay.parkour.asyncmd.manager;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 下午2:34
 */
public interface AsynExecutorService {

    /**
     * 定时来触发哪个类型的命令执行
     * @param cmd
     */
    void pushCmdToExecuter(String cmd);
}
