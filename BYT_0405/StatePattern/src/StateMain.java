public class StateMain {
    public static void main(String[] args) {
        StateContext stateContext = new StateContext();
        stateContext.getState();
        stateContext.getState();
        stateContext.setState(new Processing());
        stateContext.getState();
        stateContext.setState(new Working());
        stateContext.getState();
        stateContext.getState();
        stateContext.setState(new Processing());
        stateContext.getState();
        stateContext.setState(new Waiting());
        stateContext.getState();
    }
}

interface State {
    void alert(StateContext ctx);
}

class StateContext {
    private State currentState;

    public StateContext() {
        currentState = new Waiting();
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void getState() {
        currentState.alert(this);
    }
}

class Waiting implements State {
    @Override
    public void alert(StateContext ctx) {
        System.out.println("waiting for work...");
    }
}

class Processing implements State {
    @Override
    public void alert(StateContext ctx) {
        System.out.println("processing...");
    }
}

class Working implements State {
    @Override
    public void alert(StateContext ctx) {
        System.out.println("working...");
    }
}