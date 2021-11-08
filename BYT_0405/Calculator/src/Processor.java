abstract class Processor {
    private final Processor processor;

    public Processor(Processor processor) {
        this.processor = processor;
    }

    public void process(Operation request) {
        if (processor != null)
            processor.process(request);
    }
}