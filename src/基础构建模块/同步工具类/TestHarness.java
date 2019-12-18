package 基础构建模块.同步工具类;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ycz on 2019/12/18 21:38.
 */
public class TestHarness {
    public static void main(String[] args) {

    }

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {

                    try {
                        startGate.await();
                        task.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        endGate.countDown();
                    }
                }
            };

            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;
    }
}
