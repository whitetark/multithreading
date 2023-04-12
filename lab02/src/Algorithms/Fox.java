package Algorithms;

import Models.Matrix;
import Models.Result;

public class Fox implements IAlgorithm{
    private Matrix matrix1;
    private Matrix matrix2;
    private int numOfThreads;

    public Fox(Matrix matrix1, Matrix matrix2, int numOfThreads){
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.numOfThreads = numOfThreads;
    }
    @Override
    public Result multiply() {
        return null;
    }
}
