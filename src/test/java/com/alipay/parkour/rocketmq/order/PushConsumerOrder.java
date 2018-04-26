package com.alipay.parkour.rocketmq.order;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * @author jiadong
 * @date 2018/4/25 14:31
 */
public class PushConsumerOrder {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("CID_YE_1");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("TOPIC_YE_1", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setConsumeTimestamp(System.currentTimeMillis() + "");
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + msgs);
                throw new RuntimeException("模拟重发...");

                //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });

        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
