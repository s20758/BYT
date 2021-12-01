public class HouseBuilder implements Builder {
    private HouseType type;
    private WallMaterial material;
    private String roof;
    private int num_of_windows;
    private Garden garden;

    @Override
    public void setHouseType(HouseType type) {
        this.type = type;
    }

    @Override
    public void setWalls(WallMaterial material) {
        this.material = material;
    }

    @Override
    public void setRoof(String roof) {
        this.roof = roof;
    }

    @Override
    public void setWindows(int num_of_windows) {
        this.num_of_windows = num_of_windows;
    }

    @Override
    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public House getResult() {
        return new House(type, material, roof, num_of_windows, garden);
    }
}
