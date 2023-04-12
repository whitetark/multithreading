package Models;

import java.util.Arrays;

public class Matrix {
    public double[][] items;
    public int rows;
    public int cols;

    public Matrix(int rows, int cols) {
        this.items = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public void print() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.printf("%10.1f", this.items[i][j]);
            }
            System.out.println();
        }
    }

    public Matrix transpose() {
        Matrix newMatrix = new Matrix(this.cols,this.rows);
        for (int i = 0; i < items[0].length; i++) {
            for (int j = 0; j < items.length; j++) {
                newMatrix.items[i][j] = this.items[j][i];
            }
        }
        return newMatrix;
    }

    public double[] getRow(int row) {
        return this.items[row];
    }

    public double[] getColumn(int index){
        double[] column = new double[this.cols];
        for (int i = 0; i < this.rows; i++) {
            column[i] = (items[i][index]);
        }
        return column;
    }

    public void generateRandomMatrix() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.items[i][j] = Math.random();
            }
        }
    }
}
