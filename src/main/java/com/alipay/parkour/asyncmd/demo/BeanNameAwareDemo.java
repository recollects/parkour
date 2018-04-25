package com.alipay.parkour.asyncmd.demo;

import org.springframework.beans.factory.BeanNameAware;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月06日 上午10:30
 */
public class BeanNameAwareDemo implements BeanNameAware {
    @Override
    public void setBeanName(String name) {
        System.out.println("-----BeanNameAware#setBeanName------");
    }
}
