class ThreadProcess implements Runnable {
    private final ObjectPool<MyObject> pool;
    private final int num;

    public ThreadProcess(ObjectPool<MyObject> pool, int num){
        this.pool = pool;
        this.num = num;
    }

    public void run() {
        MyObject obj = pool.borrowObject();
        System.out.println("Thread " + num + ": Object with id " + obj.getId() + " was borrowed");
        pool.returnObject(obj);
        System.out.println("Thread " + num + ": Object with id " + obj.getId() + " was returned");
    }
}