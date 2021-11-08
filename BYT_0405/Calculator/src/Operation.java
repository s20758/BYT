import java.util.Arrays;

class Operation {
    private final String[] op;

    public Operation(String op) {
        Object[] arr = Arrays.stream(op.split(" ")).toArray();
        this.op = Arrays.copyOf(arr, arr.length, String[].class);
    }

    public double[] getNumbers() {
        return new double[] { Double.parseDouble(op[0]), Double.parseDouble(op[2]) };
    }

    public String getOperator() {
        return op[1];
    }
}