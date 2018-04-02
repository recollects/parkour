package com.alipay.parkour.asyncmd.manager;

import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 下午2:34
 */
public interface AsynExecutorService {



    /**
     * 获取所有命令列表
     * @return
     */
    List<String> getAsynExecutedCmds();
}
