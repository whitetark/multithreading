package Algorithms;

import Models.Matrix;

public class Standard implements IAlgorithm {
    private Matrix matrix1;
    private Matrix matrix2;

    public Standard(Matrix matrix1, Matrix matrix2){
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
    }
    @Override
    public Matrix multiply(){
        if(matrix1.getCols() != matrix2.getRows()){
            throw new IllegalArgumentException("Matrices cannot be multiplied - invalid dimensions");
        }

        Matrix result = new Matrix(matrix1.getRows(), matrix2.getCols());
        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getCols(); j++) {
                result.items[i][j] = 0;
                for (int k = 0; k < matrix1.getCols(); k++) {
                    result.items[i][j] += matrix1.items[i][k] * matrix2.items[k][j];
                }
            }
        }
        return result;
    }
}
