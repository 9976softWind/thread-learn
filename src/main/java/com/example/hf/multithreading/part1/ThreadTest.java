package com.example.hf.multithreading.part1;

/**
 * @author tdw
 * @date 2025.3.21
 */
public class ThreadTest {

    public static void main(String[] args) {
        System.out.println("main start");
        Thread thread = new Thread(() -> {
            System.out.println("thread start");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread end");
        });
        thread.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }
}
