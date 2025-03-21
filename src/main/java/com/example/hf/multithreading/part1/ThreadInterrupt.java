package com.example.hf.multithreading.part1;

/**
 * @author tdw
 * @date 2025.3.21
 * 线程中断     https://liaoxuefeng.com/books/java/threading/interrupt/index.html
 *  其他线程给该线程发一个信号，该线程收到信号后结束执行run()方法，使得自身线程能立刻结束运行。
 *  假设从网络下载一个100M的文件，如果网速很慢，用户等得不耐烦，就可能在下载过程中点“取消”，这时，程序就需要中断下载线程的执行。
 *  中断一个线程非常简单，只需要在其他线程中对目标线程调用interrupt()方法，目标线程需要反复检测自身状态是否是interrupted状态，如果是，就立刻结束运行。
 */
public class ThreadInterrupt {
    /**
     * main线程通过调用t.interrupt()从而通知t线程中断，而此时t线程正位于downLoadFileThread.join()的等待中，
     * 此方法会立刻结束等待并抛出InterruptedException。由于我们在t线程中捕获了InterruptedException，因此，就可以准备结束该线程。
     * 在t线程结束前，对downLoadFileThread线程也进行了interrupt()调用通知其中断。如果去掉这一行代码，可以发现downLoadFileThread线程仍然会继续运行，且JVM不会退出。
     *
     *
     *
     * 另一个常用的中断线程的方法是设置标志位。我们通常会用一个running标志位来标识线程是否应该继续运行，在外部线程中，通过把DownLoadFileThread.running置为false，就可以让线程结束：
     *  public volatile boolean running = true;
     *  线程间共享变量需要使用volatile关键字标记，确保每个线程都能读取到更新后的变量值。
     *
     *  小结：
     *      对目标线程调用interrupt()，可中断，若目标线程处于等待状态，目标线程中会捕获到InterruptedException异常；
     *      目标线程检测到isInterrupted()为true或者捕获了InterruptedException都应该立刻结束自身线程；
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t = new CustomThread();
        t.start();
        Thread.sleep(1000);
        t.interrupt();
        t.join();
        System.out.println("end");
    }
}
class CustomThread extends Thread {
    @Override
    public void run() {
        Thread downLoadFileThread = new DownLoadFileThread();
        downLoadFileThread.start();
        try {
            downLoadFileThread.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
        }
        downLoadFileThread.interrupt();
    }
}
class DownLoadFileThread extends Thread{
    @Override
    public void run() {
        int n = 0;
        while (!isInterrupted()){
            System.out.println("文件大小："+ n++ + "kb");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
