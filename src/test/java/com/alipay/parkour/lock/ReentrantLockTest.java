package com.alipay.parkour.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁测试
 *
 * @author recollects
 * @version V1.0
 * @date 2018年04月12日 下午10:28
 */
public class ReentrantLockTest {

    public static void main(String[] args) throws Exception {

        MyThread myThread = new MyThread();

        new Thread(myThread).start();
        new Thread(myThread).start();

        Thread.currentThread().join();
    }

    /**
     *
     */
    static class MyThread extends Thread {

        public static ReentrantLock lock = new ReentrantLock();

        public static int i = 0;

        @Override
        public void run() {
            for (int j = 0; j < 100000; j++) {
                lock.lock();
                try {
                    i++;
                    System.out.println("lock--->" + i);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
