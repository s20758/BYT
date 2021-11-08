class MyObject {
    private final int id;

    public MyObject(int id)  {
        this.id = id;
        System.out.println("Object with id " + id + " was created");
    }

    public int getId() { return this.id; }
}