package com.alipay.parkour.asyncmd.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月06日 上午10:24
 */
public class BeanFactoryPostProcessorDemo implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("------BeanFactoryPostProcessor------");
    }
}
