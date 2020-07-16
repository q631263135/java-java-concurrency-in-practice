package 七_取消与关闭.shutdownNow的局限性;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ycz on 2020/7/16 21:53.
 */
public class TrackingExecutor extends AbstractExecutorService {
    private final ExecutorService exec;

    private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());

    public TrackingExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    public List<Runnable> getCancelldTasks() {
        if (exec.isTerminated()) {
            throw new IllegalArgumentException("");
        }
        return new ArrayList<>(tasksCancelledAtShutdown);
    }


    @Override
    public void execute(Runnable command) {

        exec.execute(() -> {
            try {
                command.run();
            } finally {
                if (isShutdown() && Thread.currentThread().isInterrupted()) {
                    tasksCancelledAtShutdown.add(command);
                }
            }
        });
    }

    @Override
    public void shutdown() {
        exec.shutdownNow();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();

    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }

}
