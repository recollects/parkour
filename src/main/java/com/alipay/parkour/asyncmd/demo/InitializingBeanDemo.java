package com.alipay.parkour.asyncmd.demo;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月06日 上午10:22
 */
public class InitializingBeanDemo implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-------InitializingBean-------");
    }
}
