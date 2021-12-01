public class BuilderMain2 {
    public static void main(String[] args) {
        Director director = new Director();
        HouseBuilder builder = new HouseBuilder();
        director.constructCottageHouse(builder);
        House house = builder.getResult();
        System.out.println("House built:\n" + house.toString());
        director.constructMansionHouse(builder);
        House house2 = builder.getResult();
        System.out.println("House built:\n" + house2.toString());
    }
}

interface Builder {
    void setHouseType(HouseType type);
    void setWalls(WallMaterial material);
    void setRoof(String roof);
    void setWindows(int num_of_windows);
    void setGarden(Garden garden);
}
