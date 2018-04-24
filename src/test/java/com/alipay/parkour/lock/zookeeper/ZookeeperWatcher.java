package com.alipay.parkour.lock.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;

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
        if (watchedEvent.getType()== EventType.NodeCreated){
            System.out.println("结点创建");
        }else if(watchedEvent.getType()==EventType.NodeDeleted){
            System.out.println("结点删除");
        }else if(watchedEvent.getType()==EventType.NodeDataChanged){
            System.out.println("结点变更");
        }else if(watchedEvent.getType()==EventType.NodeChildrenChanged){
            System.out.println("子结点变更");
        }else {
            //空
        }

        System.out.println("监视对象---->"+watchedEvent);

    }
}
