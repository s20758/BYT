public class TeaBuilder implements Builder {
    private TeaType type;
    private Sugar sugar;
    private Milk milk;
    private boolean honey, mint;

    @Override
    public void setTeaType(TeaType type) {
        this.type = type;
    }

    @Override
    public void setSugar(Sugar sugar) {
        this.sugar = sugar;
    }

    @Override
    public void setMilk(Milk milk) {
        this.milk = milk;
    }

    @Override
    public void setHoney(boolean hon_added) {
        this.honey = hon_added;
    }

    @Override
    public void setMintLeaves(boolean mint_added) {
        this.mint = mint_added;
    }

    public Tea getResult() {
        return new Tea(type, sugar, milk, honey, mint);
    }
}
