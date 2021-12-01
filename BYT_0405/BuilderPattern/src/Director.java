public class Director {

    public void constructBlackTea(Builder builder) {
        builder.setTeaType(TeaType.BLACK);
        builder.setSugar(new Sugar(SugarType.WHITE, 1.5));
        builder.setMilk(null);
        builder.setHoney(false);
        builder.setMintLeaves(true);
    }

    public void constructWhiteTea(Builder builder) {
        builder.setTeaType(TeaType.WHITE);
        builder.setSugar(null);
        builder.setMilk(Milk.ALMOND);
        builder.setHoney(true);
        builder.setMintLeaves(false);
    }
}