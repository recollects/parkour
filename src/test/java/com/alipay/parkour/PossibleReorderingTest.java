package com.alipay.parkour;

/**
 * @author recollects
 * @version V1.0
 * @date 2018年04月14日 下午4:03
 */
public class PossibleReorderingTest {
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            public void run() {
                a = 1;
                x = b;
            }
        });

        Thread other = new Thread(new Runnable() {
            public void run() {
                b = 1;
                y = a;
            }
        });

        one.start();
        other.start();
        one.join();
        other.join();
        System.out.println(" (" + x + "," + y + ")");
    }
}
