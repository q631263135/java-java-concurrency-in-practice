package 基础构建模块.同步工具类;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by ycz on 2019/12/18 22:00.
 */
public class PreLoader {
    private final FutureTask<Object> futureTask = new FutureTask<Object>(new Callable<Object>() {
        @Override
        public Object call() throws Exception {
            return loadData();
        }

        private Object loadData() {
            return null;
        }
    });

    private final Thread thread = new Thread(futureTask);

    public void start() {
        thread.start();
    }

    public Object get() {
        try {
            return futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
