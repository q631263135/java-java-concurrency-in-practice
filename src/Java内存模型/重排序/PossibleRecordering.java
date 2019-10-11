package Java内存模型.重排序;

/**
 * <br/>
 *
 * @author yangchaozheng
 * @date 2019/10/11 16:59
 */
public class PossibleRecordering {

    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                a = 1;
                x = b;
            }
        });

        Thread other = new Thread(new Runnable() {
            @Override
            public void run() {
                b = 1;
                y = a;
            }
        });

        one.start();
        other.start();

        one.join();
        other.join();
        System.out.printf("(%1$s, %2$s)", x, y);
    }

}
