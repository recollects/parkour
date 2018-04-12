package com.alipay.parkour.lock.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月12日 下午10:04
 */
public class ZookeeperCuratorLockDemo {

    private static CuratorFramework client;

    private static final String connectStr = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        client = CuratorFrameworkFactory.newClient(connectStr, new ExponentialBackoffRetry(1000, 10));

        client.start();

//        InterProcessMutex lock = new InterProcessMutex(client, "/yejiadong");

        MyThread myThread = new MyThread();

        new Thread(myThread).start();
        new Thread(myThread).start();

        Thread.currentThread().join();

    }


    /**
     *
     */
    static class MyThread extends Thread {

        InterProcessMutex lock = new InterProcessMutex(client, "/yejiadong");

        public static int i = 0;

        @Override
        public void run() {
            try {
                for (int j = 0; j < 100000; j++) {
                    lock.acquire();
                    i++;
                    System.out.println("lock--->" + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
