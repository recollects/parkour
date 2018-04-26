package com.alipay.parkour.sofa.service.impl;

import com.alipay.parkour.sofa.service.HelloService;

/**
 * @author jiadong
 * @date 2018/4/26 15:47
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String str) {

        return "Hello world:" + str;
    }
}
