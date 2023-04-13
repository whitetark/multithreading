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

    public static Matrix[][] generateBlocks(Matrix matrix, int blockSize){
        Matrix[][] blocks = new Matrix[blockSize][blockSize];
        int numOfBlocks = matrix.cols / blockSize;

        for(int i = 0; i < blockSize; i++){
            for(int j = 0; j < blockSize; j++){
                blocks[i][j] = new Matrix(numOfBlocks, numOfBlocks);
                for(int k = 0; k < numOfBlocks; k++){
                    for(int l = 0; l < numOfBlocks; l++){
                        double element = 0;
                        if(i * numOfBlocks + k >= matrix.rows || j * numOfBlocks + l >= matrix.cols){
                            element = matrix.items[i * numOfBlocks + k][j * numOfBlocks + l];
                        }
                        blocks[i][j].items[k][l] = element;
                    }
                }
            }
        }
        return blocks;
    }
}
