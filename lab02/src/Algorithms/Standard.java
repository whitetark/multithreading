package Algorithms;

import Models.IAlgorithm;
import Models.Matrix;

public class Standard implements IAlgorithm {
    private Matrix matrix1;
    private Matrix matrix2;
    @Override
    public Matrix multiply(){
        if(matrix1.getColumns() != matrix2.getRows()){
            throw new IllegalArgumentException("Matrices cannot be multiplied - invalid dimensions");
        }

        Matrix result = new Matrix(matrix1.getRows(), matrix2.getColumns());
        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getColumns(); j++) {
                int temp = 0;
                for (int k = 0; k < matrix1.getColumns(); k++) {
                    temp += matrix1.getItem(i,k) * matrix2.getItem(k,j);
                }
                result.setItem(i,j,temp);
            }
        }
        return result;
    }
}
