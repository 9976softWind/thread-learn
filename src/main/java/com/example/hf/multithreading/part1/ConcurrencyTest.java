package com.example.hf.multithreading.part1;

/**
 * @author tdw
 * 多线程一定快吗？下面的代码演示串行serial()和并发执行concurrency()执行累加的操作时间；
 * 答：不一定，当并发执行累加操作不超过百万次，速度比串行要慢，这是因为线程的创建以及上下文切换存在时间开销。
 * @date 2024/10/23
 */
public class ConcurrencyTest {

    private static final long count = 100001;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    /**
     * 并发执行
     */
    static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                }
            }
        });
        thread.start();
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("concurrency：" + time + "ms,b=" + b);
    }

    /**
     * 串行
     */
    static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial：" + time + "ms，b=" + b + ",a=" + a);
    }
}
