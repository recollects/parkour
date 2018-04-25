package com.alipay.parkour.asyncmd.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月06日 上午10:25
 */
public class BeanPostProcessorDemo implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("------BeanPostProcessor#postProcessBeforeInitialization------");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("------BeanPostProcessor#postProcessAfterInitialization------");
        return bean;
    }
}
