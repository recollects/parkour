package com.alipay.parkour.lock.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 *
 * {@see https://www.cnblogs.com/luxiaoxun/p/4889764.html 扩展使用}
 * {@see https://blog.csdn.net/sunfeizhi/article/details/51926396 锁实现原理}
 *
 * @author recollects
 * @version V1.0
 * @date 2018年04月10日 下午9:23
 */
public class ZookeeperLockTest {

    public static void main(String[] args) throws  Exception{
        Runnable runnable = new Runnable() {
            public void run() {
                ZookeeperLock lock = null;
                try {
                    lock = new ZookeeperLock("127.0.0.1:2181", "yejiadong",new ZookeeperWatcher());
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "正在运行");
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }



    }

}
