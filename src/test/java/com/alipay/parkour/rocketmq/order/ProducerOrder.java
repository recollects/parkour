package com.alipay.parkour.rocketmq.order;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月25日 下午9:07
 */
public class ProducerOrder {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("PID_YE_1");

        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        int orderId = 123456;
        for (int i = 0; i < 3; i++) {
            Message msg = new Message("TOPIC_YE_1", "TAG_YE_1", "OrderID188", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    int index = ((Integer) o) % list.size();
                    return list.get(index);
                }
            }, orderId);

            System.out.println(sendResult);
        }

        producer.shutdown();
    }
}
