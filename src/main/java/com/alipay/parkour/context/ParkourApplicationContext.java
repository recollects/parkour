package com.alipay.parkour.context;

import com.alipay.parkour.asyncmd.manager.AsynControllerService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年03月31日 下午2:31
 */

public class ParkourApplicationContext implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ParkourApplicationContext.applicationContext = applicationContext;
    }

    /**
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> clazz) {
        return (T)applicationContext.getBean(clazz);
    }

}
