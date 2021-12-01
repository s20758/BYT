public class Sugar {
    private final SugarType sugarType;
    private final double num_of_teaspoons;

    public Sugar(SugarType sugarType, double num_of_teaspoons) {
        this.sugarType = sugarType;
        this.num_of_teaspoons = num_of_teaspoons;
    }

    public SugarType getSugarType() {
        return sugarType;
    }

    public double getNum_of_teaspoons() {
        return num_of_teaspoons;
    }
}

enum SugarType {
    WHITE, BROWN
}
