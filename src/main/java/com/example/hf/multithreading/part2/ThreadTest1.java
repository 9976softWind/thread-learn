package com.example.hf.multithreading.part2;

/**
 * @author tdw
 * @date 2025.3.21
 * 线程同步:    https://liaoxuefeng.com/books/java/threading/synchronize/index.html
 *  多个线程同时读写共享变量，会出现数据不一致的问题。此时需要线程同步
 */
public class ThreadTest1 {

    public static void main(String[] args) throws InterruptedException {
        Thread addThread = new AddThread();
        Thread decThread = new DecThread();
        addThread.start();
        decThread.start();
        addThread.join();
        decThread.join();
        System.out.println(Counter.count);
    }
}
class Counter {
    public static final Object lock = new Object();
    public static int count = 0;
}

class AddThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (Counter.lock){
                Counter.count += 1;
            }
        }
    }
}
class DecThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (Counter.lock){
                Counter.count -= 1;
            }
        }
    }
}


