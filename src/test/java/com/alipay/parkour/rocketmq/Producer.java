package com.alipay.parkour.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * {@see https://github.com/apache/rocketmq/tree/master/example/src/main/java/org/apache/rocketmq/example}
 * @author recollects
 * @version V1.0
 * @date 2018年04月24日 下午9:46
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("PID_YE_1");

        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i < 10; i++)
            try {
                {
                    Message msg = new Message("TOPIC_YE_1", "TAG_YE_1", "OrderID188", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = producer.send(msg);
                    System.out.printf("%s%n", sendResult);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        producer.shutdown();
    }
}
