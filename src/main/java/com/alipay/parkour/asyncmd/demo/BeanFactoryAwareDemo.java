package com.alipay.parkour.asyncmd.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月06日 上午10:31
 */
public class BeanFactoryAwareDemo implements BeanFactoryAware {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("-----BeanFactoryAware#setBeanFactory------");
    }
}
