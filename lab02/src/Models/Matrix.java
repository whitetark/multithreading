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
        Matrix result = new Matrix(this.cols,this.rows);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.items[j][i] = this.items[i][j];
            }
        }
        return result;
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

    public static Matrix[][] generateBlocks(Matrix matrix, int blockSize){
        int numOfBlocks = matrix.items.length / blockSize;
        Matrix[][] blocks = new Matrix[numOfBlocks][numOfBlocks];
        for(int i = 0; i < numOfBlocks; i++){
            for(int j = 0; j < numOfBlocks; j++){
                blocks[i][j] = new Matrix(blockSize, blockSize);
                for(int k = 0; k < blockSize; k++){
                    System.arraycopy(matrix.items[i * blockSize + k], j * blockSize, blocks[i][j].items[k], 0, blockSize);
                }
            }
        }
        return blocks;
    }
}
