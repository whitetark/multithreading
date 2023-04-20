package Threads;

import java.util.concurrent.Callable;

public class StripedCallable implements Callable<Double> {
    private double[] row;
    private double[] column;
    public StripedCallable(double[] row, double[] col) {
        this.row = row;
        this.column = col;
    }

    @Override
    public Double call() {
        double result = 0;
        for (int k = 0; k < row.length; k++) {
            result += row[k] * column[k];
        }
        return result;
    }
}
