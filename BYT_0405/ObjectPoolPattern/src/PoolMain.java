import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PoolMain {
    public static void main(String[] args)  {
        AtomicInteger id = new AtomicInteger(0);

        ObjectPool<MyObject> pool = new ObjectPool<>(5, 10) {
            protected MyObject createObject() {
                return new MyObject(id.incrementAndGet());
            }
        };
        pool.execService.shutdown();

        int nThreads = 3;
        ExecutorService exec = Executors.newFixedThreadPool(nThreads);
        for (int i = 1; i <= nThreads; i++) {
            exec.execute(new ThreadProcess(pool, i));
        }
        exec.shutdown();
    }
}