import java.util.Map;

public class Garden {
    private final GardenType gardenType;
    private final Map<String, Integer> garden_items;

    public Garden(GardenType gardenType1, Map<String, Integer> garden_items) {
        this.gardenType = gardenType1;
        this.garden_items = garden_items;
    }

    public GardenType getGardenType() {
        return gardenType;
    }

    public Map<String, Integer> getGarden_items() {
        return garden_items;
    }
}

enum GardenType {
    FRUIT, FLOWER
}
