public class House {
    private final HouseType type;
    private final WallMaterial material;
    private final String roof;
    private final int num_of_windows;
    private final Garden garden;

    public House(HouseType type, WallMaterial material, String roof, int num_of_windows, Garden garden) {
        this.type = type;
        this.material = material;
        this.roof = roof;
        this.num_of_windows = num_of_windows;
        this.garden = garden;
    }

    @Override
    public String toString() {
        return "House {\n" +
                " type = " + (type == null ? "false" : type) +
                ",\n wall_material = " + (material == null ? "false" : material) +
                ",\n roof = " + (roof == null ? "false" : roof) +
                ",\n number_of_windows = " + num_of_windows +
                ",\n garden = " +
                (garden == null ? "false" : garden.getGardenType() + "; " + garden.getGarden_items()) +
                "\n}";
    }
}

enum HouseType {
    COTTAGE, MANSION, CASTLE
}
