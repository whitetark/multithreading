package Algorithms;

import Models.Matrix;
import Models.Result;

public class Standard implements IAlgorithm {
    private Matrix matrix1;
    private Matrix matrix2;

    public Standard(Matrix matrix1, Matrix matrix2){
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
    }
    @Override
    public Result multiply(){
        if(matrix1.cols != matrix2.rows){
            throw new IllegalArgumentException("Matrices cannot be multiplied - invalid dimensions");
        }

        Matrix result = new Matrix(matrix1.rows, matrix2.cols);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                for (int k = 0; k < matrix1.cols; k++) {
                    result.items[i][j] += matrix1.items[i][k] * matrix2.items[k][j];
                }
            }
        }
        long finishTime = System.currentTimeMillis();
        return new Result(result,finishTime-startTime);
    }
}
