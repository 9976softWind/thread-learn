package com.example.hf.multithreading.part1;

/**
 * @author tdw
 * @date 2025.3.21
 * 线程状态：
 * New：新创建的线程，尚未执行；
 *      Runnable：运行中的线程，正在执行run()方法的Java代码；
 *      Blocked：运行中的线程，因为某些操作被阻塞而挂起；
 *      Waiting：运行中的线程，因为某些操作在等待中；
 *      Timed Waiting：运行中的线程，因为执行sleep()方法正在计时等待；
 * Terminated：线程已终止，因为run()方法执行完毕。
 *
 * 当线程启动后，它可以在Runnable、Blocked、Waiting和Timed Waiting这几个状态之间切换，直到最后变成Terminated状态，线程终止。
 *
 *
 */
public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello");
        });
        System.out.println("start");
        t.start(); // 启动t线程
        t.join(1000); // 此处main线程会等待t结束,超过等待时间1000后就不再继续等待。
        System.out.println("end");
    }

}
