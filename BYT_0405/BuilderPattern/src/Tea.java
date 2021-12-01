public class Tea {
    private final TeaType type;
    private final Sugar sugar;
    private final Milk milk;
    private final boolean honey, mint;

    public Tea(TeaType type, Sugar sugar, Milk milk, boolean honey, boolean mint) {
        this.type = type;
        this.sugar = sugar;
        this.milk = milk;
        this.honey = honey;
        this.mint = mint;
    }

    @Override
    public String toString() {
        return "Tea {\n" +
                " type = " + (type == null ? "false" : type) +
                ",\n sugar = " +
                (sugar == null ? "false" : sugar.getSugarType() + "; " + sugar.getNum_of_teaspoons()) +
                ",\n milk = " + (milk == null ? "false" : milk) +
                ",\n honey = " + honey +
                ",\n mint = " + mint +
                "\n}";
    }
}

enum TeaType {
    BLACK, GREEN, WHITE, YELLOW, OOLONG
}
