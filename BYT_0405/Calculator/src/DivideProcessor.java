class DivideProcessor extends Processor {
    public DivideProcessor(Processor processor){
        super(processor);
    }

    public void process(Operation request) {
        if (request.getOperator().equals("/")) {
            System.out.println(request.getNumbers()[0] / request.getNumbers()[1]);
        } else {
            super.process(request);
        }
    }
}