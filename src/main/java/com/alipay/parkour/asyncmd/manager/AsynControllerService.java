package com.alipay.parkour.asyncmd.manager;

import com.alipay.parkour.asyncmd.model.AsynCmdDefinition;

import java.util.List;
import java.util.Map;

/**
 * @author recollects
 * @version V1.0
 * @date 2018/4/3 15:42
 */
public interface AsynControllerService {

    /**
     * 获取所有命令
     * @return
     */
    Map<String, AsynCmdDefinition> getAsynCmdDefinitionMap();

    /**
     *
     * @param cmd
     * @return
     */
    AsynCmdDefinition getAsynCmdDefinition(String cmd);

    /**
     *
     * @return
     */
    List<AsynCmdDefinition> getAsynExecutedCmds();
}
