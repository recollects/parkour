package com.alipay.parkour.sofa.client;

import com.alipay.parkour.sofa.service.HelloService;
import com.alipay.sofa.rpc.config.ConsumerConfig;

/**
 * @author jiadong
 * @date 2018/4/26 15:52
 */
public class QuickStartClient {

    public static void main(String[] args) {
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
            .setInterfaceId(HelloService.class.getName()) // 指定接口
            .setProtocol("bolt") // 指定协议
            .setDirectUrl("bolt://127.0.0.1:9696"); // 指定直连地址
        // 生成代理类
        HelloService helloService = consumerConfig.refer();
        while (true) {
            System.out.println(helloService.hello("jiadong"));
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}
