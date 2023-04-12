package Algorithms;

import Models.Matrix;
import Models.Result;
import Threads.StripedThread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Striped implements IAlgorithm{
    private Matrix matrix1;
    private Matrix matrix2;
    private int numOfThreads;

    public Striped(Matrix matrix1, Matrix matrix2, int numOfThreads){
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.numOfThreads = numOfThreads;
    }
    @Override
    public Result multiply(){
        if(matrix1.cols != matrix2.rows){
            throw new IllegalArgumentException("Matrices cannot be multiplied - invalid dimensions");
        }

        Matrix result = new Matrix(matrix1.rows, matrix2.cols);
        Matrix transMatrix2 = matrix2.transpose();

        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
        ArrayList<Callable<Object>> tasks = new ArrayList<>(matrix1.rows);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix2.cols; j++) {
                StripedThread thread = new StripedThread(result, matrix1.getRow(i), transMatrix2.getRow(j), i, j);
                tasks.add(Executors.callable(thread));
            }
        }
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();
        long finishTime = System.currentTimeMillis();
        return new Result(result, finishTime - startTime);
    }
}

