package com.alipay.parkour.asyncmd.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.beans.PropertyDescriptor;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月06日 上午10:27
 */
public class InstantiationAwareBeanPostProcessorAdapterDemo extends InstantiationAwareBeanPostProcessorAdapter {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("-----InstantiationAwareBeanPostProcessorAdapter#postProcessBeforeInstantiation-----");
        return super.postProcessBeforeInstantiation(beanClass, beanName);
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        System.out.println("-----InstantiationAwareBeanPostProcessorAdapter#postProcessPropertyValues-----");
        return super.postProcessPropertyValues(pvs, pds, bean, beanName);
    }

}
