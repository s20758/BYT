public class BuilderMain {
    public static void main(String[] args) {
        Director director = new Director();
        TeaBuilder builder = new TeaBuilder();
        director.constructBlackTea(builder);
        Tea tea = builder.getResult();
        System.out.println("Tea built:\n" + tea.toString());
        director.constructWhiteTea(builder);
        Tea tea2 = builder.getResult();
        System.out.println("Tea built:\n" + tea2.toString());
    }
}

interface Builder {
    void setTeaType(TeaType type);
    void setSugar(Sugar sugar);
    void setMilk(Milk milk);
    void setHoney(boolean hon_added);
    void setMintLeaves(boolean mint_added);
}
