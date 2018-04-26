package com.alipay.parkour.rocketmq.order;

import java.util.List;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * @author jiadong
 * @date 2018/4/25 14:31
 */
public class ProducerOrder {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("PID_YE_1");

        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        int orderId = 12345678;
        for (int i = 0; i < 3; i++) {

            Message msg = new Message("TOPIC_YE_1", "TAG_YE_1", "OrderID188", ("Hello world" + i).getBytes(
                RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult =producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    int index = ((Integer)o) % list.size();
                    MessageQueue queue = list.get(index);
                    return queue;
                }
            }, orderId);

            //SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);

            Thread.sleep(1000);

        }

        producer.shutdown();
    }
}
