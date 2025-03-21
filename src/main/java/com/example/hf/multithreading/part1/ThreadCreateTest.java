package com.example.hf.multithreading.part1;

/**
 * @author tdw
 * 创建新线程：
 * 创建线程的几种方式，（未列举线程池获取）
 *
 * Java用Thread对象表示一个线程，通过调用start()启动一个新线程；
 *
 * 一个线程对象只能调用一次start()方法；
 *
 * 线程的执行代码写在run()方法中；
 *
 * 线程调度由操作系统决定，程序本身无法决定调度顺序；
 *
 * Thread.sleep()可以把当前线程暂停一段时间。
 * @date 2025/3/21
 */
public class ThreadCreateTest {

    public static void main(String[] args) {
        Thread myThread = new MyThread();
        /**
         * 必须调用Thread实例的start()方法才能启动新线程;
         * 直接调用run()方法，相当于调用了一个普通的Java方法，当前线程并没有任何改变，也不会启动新线程
         */
        myThread.start();
        Thread thread = new Thread(new MyRunnable());
        thread.start();

        Thread thread1 = new Thread(() -> {
            System.out.println("JDK8：start new thread!");
        });
        thread1.start();

    }

}
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("extend Thread：start new thread!");
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("implements Runnable：start new thread!");
    }
}
