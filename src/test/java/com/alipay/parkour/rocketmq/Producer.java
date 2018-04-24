package com.alipay.parkour.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * @author jiadong
 * @date 2018/4/24 16:18
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("PID_YEJIADONG_1");

        producer.setNamesrvAddr("30.5.133.212:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            try {
                {
                    Message msg = new Message("TOPIC_YE_1",
                        "TAG_YE_1",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = producer.send(msg);
                    System.out.printf("%s%n", sendResult);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        producer.shutdown();
    }
}
