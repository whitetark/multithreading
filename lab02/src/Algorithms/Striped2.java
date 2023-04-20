package Algorithms;

import Models.Matrix;
import Models.Result;
import Threads.StripedCallable;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Striped2 implements IAlgorithm{
    private Matrix matrix1;
    private Matrix matrix2;
    private int numOfThreads;

    public Striped2(Matrix matrix1, Matrix matrix2, int numOfThreads){
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
        ArrayList<StripedCallable> tasks = new ArrayList<>();
        ArrayList<Future<Double>> futures = new ArrayList<>();

        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix2.cols; j++) {
                StripedCallable task = new StripedCallable(matrix1.getRow(i), transMatrix2.getRow(j));
                tasks.add(task);
            }
        }
        try {
            futures.addAll(executor.invokeAll(tasks));
            tasks.clear();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();

        try{
            for (int i = 0; i < result.rows; i++) {
                for (int j = 0; j < result.cols; j++) {
                    Future<Double> future = futures.get(i * result.cols + j);
                    result.items[i][j] = future.get();
                }
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        long finishTime = System.currentTimeMillis();
        return new Result(result, finishTime - startTime);
    }
}

