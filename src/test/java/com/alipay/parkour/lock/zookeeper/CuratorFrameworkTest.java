package com.alipay.parkour.lock.zookeeper;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * InterProcessMutex 可重入
 * InterProcessSemaphoreMutex 不可重入锁
 *
 * @author jiadong
 * @date 2018/4/12 11:34
 */
public class CuratorFrameworkTest {

    static final String root_path = "/yejiadong";

    static final String connectConf = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        //RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //
        //CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectConf, retryPolicy);
        //
        //curatorFramework.start();
        //
        //new Thread(new LockThread(curatorFramework, UUID.randomUUID().toString())).start();

        ReentrantLock lock = new ReentrantLock();





    }

    static class LockThread implements Runnable {

        InterProcessSemaphoreMutex interProcessMutex;
        String clientName;

        LockThread(CuratorFramework curatorFramework, String clientName) {
            interProcessMutex = new InterProcessSemaphoreMutex(curatorFramework, root_path);
            this.clientName = clientName;
        }

        @Override
        public void run() {
            try {
                interProcessMutex.acquire();

                if (interProcessMutex.isAcquiredInThisProcess()) {
                    System.out.println(Thread.currentThread().getName()+"-"+clientName + " 获得锁");
                } else {
                    System.out.println(clientName + " 未获得锁");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    interProcessMutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
