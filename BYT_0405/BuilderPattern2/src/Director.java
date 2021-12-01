import java.util.Map;

public class Director {

    public void constructCottageHouse(Builder builder) {
        builder.setHouseType(HouseType.COTTAGE);
        builder.setWalls(WallMaterial.BRICK);
        builder.setRoof("Flat");
        builder.setWindows(8);
        builder.setGarden(new Garden(GardenType.FRUIT, Map.of("Tree", 10, "Bush", 15, "Flower", 25)));
    }

    public void constructMansionHouse(Builder builder) {
        builder.setHouseType(HouseType.MANSION);
        builder.setWalls(WallMaterial.GLASS);
        builder.setRoof("Mansard");
        builder.setWindows(20);
        builder.setGarden(new Garden(GardenType.FLOWER, Map.of("Tree", 50, "Flower", 150)));
    }
}