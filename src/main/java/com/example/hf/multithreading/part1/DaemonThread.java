package com.example.hf.multithreading.part1;

import java.time.LocalTime;

/**
 * @author tdw
 * @date 2025.3.21
 *  https://liaoxuefeng.com/books/java/threading/daemon/index.html
 * 守护线程是指为其他线程服务的线程。在JVM中，所有非守护线程都执行完毕后，无论有没有守护线程，虚拟机都会自动退出。
 *
 * 创建守护线程方法和普通线程一样，只是在调用start()方法前，调用setDaemon(true)把该线程标记为守护线程：
 *
 * 在守护线程中，编写代码要注意：守护线程不能持有任何需要关闭的资源，例如打开文件等，因为虚拟机退出时，守护线程没有任何机会来关闭文件，这会导致数据丢失。
 */
public class DaemonThread {

    public static void main(String[] args) {
        Thread timerThread = new TimerThread();
        timerThread.setDaemon(true);
        timerThread.start();
    }

}
class TimerThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println(LocalTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
