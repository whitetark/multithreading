package Threads;

import Models.Matrix;

public class StripedThread extends Thread{
    private double[] rowMatrix1;
    private double[] rowMatrix2;
    private int i;
    private int j;
    private Matrix result;
    public StripedThread(Matrix result, double[] rowMatrix1, double[] rowMatrix2, int i, int j) {
        this.result = result;
        this.rowMatrix1 = rowMatrix1;
        this.rowMatrix2 = rowMatrix2;
        this.i = i;
        this.j = j;
    }

    @Override
    public void run() {
        for (int k = 0; k < rowMatrix1.length; k++) {
            result.items[i][j] += rowMatrix1[k] * rowMatrix2[k];
        }
    }
}