package com.alipay.parkour.asyncmd.demo;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月06日 上午10:23
 */
public class DisposableBeanDemo implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("------DisposableBean------");
    }
}
