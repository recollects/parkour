package com.alipay.parkour.asyncmd.manager;

import java.util.Map;

import com.alipay.parkour.asyncmd.model.AsynCmdDefinition;
import org.springframework.context.ApplicationContext;

/**
 * @author recollects
 * @version V1.0
 * @date 2018/4/3 15:42
 */
public interface AsynControllerService {

    /**
     *
     * @param applicationContext
     */
    void init(ApplicationContext applicationContext);

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

}
