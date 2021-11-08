class Chain {
    Processor chain;

    public Chain() {
        buildChain();
    }

    private void buildChain() {
        chain = new AddProcessor(new SubtractProcessor(new MultiplyProcessor(new DivideProcessor(null))));
    }

    public void process(Operation request) {
        chain.process(request);
    }
}