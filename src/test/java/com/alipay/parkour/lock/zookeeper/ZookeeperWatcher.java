package com.alipay.parkour.lock.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月10日 下午9:50
 */
public class ZookeeperWatcher implements Watcher{

    // 计数器
//    private CountDownLatch countDownLatch;

    public ZookeeperWatcher(){
//         countDownLatch = new CountDownLatch(1);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
//        if (this.countDownLatch != null) {
//            this.countDownLatch.countDown();
//        }
        System.out.println("监视对象---->"+watchedEvent);

    }
}
