import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

abstract class ObjectPool<T> {
    private final ConcurrentLinkedQueue<T> pool;
    public final ScheduledExecutorService execService;
    public int maxObj;

    public ObjectPool(int minObjects, int maxObjects) {
        this.maxObj = maxObjects;
        pool = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < minObjects; i++) {
            pool.add(createObject());
        }

        execService = Executors.newSingleThreadScheduledExecutor();
        execService.scheduleWithFixedDelay(() -> {
            int size = pool.size();

            if (size < minObjects) {
                int sizeToBeAdded = minObjects - size;
                for (int i = 0; i < sizeToBeAdded; i++) {
                    pool.add(createObject());
                }
            } else if (size > maxObjects) {
                int sizeToBeRemoved = size - maxObjects;
                for (int i = 0; i < sizeToBeRemoved; i++) {
                    pool.poll();
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public T borrowObject() {
        T object = this.pool.poll();
        if (object == null) {
            object = createObject();
        }
        return object;
    }

    public void returnObject(T object) {
        if (object == null) {
            return;
        }
        this.pool.add(object);
    }

    protected abstract T createObject();
}