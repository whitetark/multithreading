package Algorithms;

import Models.BlockMatrix;
import Models.Matrix;
import Models.Result;
import Threads.FoxThread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Models.Matrix.generateBlocks;

public class Fox implements IAlgorithm{
    private Matrix matrix1;
    private Matrix matrix2;
    private int numOfThreads;
    private int blockSize = 5;

    public Fox(Matrix matrix1, Matrix matrix2, int numOfThreads){
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.numOfThreads = numOfThreads;
    }
    @Override
    public Result multiply() {
        if(matrix1.cols != matrix2.rows){
            throw new IllegalArgumentException("Matrices cannot be multiplied - invalid dimensions");
        }

        long startTime = System.currentTimeMillis();
        Matrix result = new Matrix(matrix1.rows, matrix2.cols);
        Matrix[][] blocksMatrix1 = generateBlocks(matrix1, blockSize);
        Matrix[][] blocksMatrix2 = generateBlocks(matrix2, blockSize);
        Matrix[][] blocksResult = generateBlocks(result, blockSize);

        ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
        ArrayList<Callable<Object>> tasks = new ArrayList<>(matrix1.rows);
        int numOfThreads = matrix1.rows / blockSize;
        for (int i = 0; i < numOfThreads; i++) {
            FoxThread thread = new FoxThread(blocksResult, blocksMatrix1, blocksMatrix2, i, blockSize);
            tasks.add(Executors.callable(thread));
        }
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();

        for (int i = 0; i < blocksResult.length; i++) {
            for (int j = 0; j < blocksResult[i].length; j++) {
                for(int k = 0; k < blocksResult[i][j].rows; k++){
                    for(int l = 0; l < blocksResult[i][j].cols; l++){
                        if (i * blocksResult[i][j].rows + k < result.rows && j * blocksResult[i][j].cols + l < result.cols) {
                            result.items[i * blocksResult[i][j].rows + k][j * blocksResult[i][j].cols + l] = blocksResult[i][j].items[k][l];
                        }
                    }
                }
            }
        }
        long finishTime = System.currentTimeMillis();
        return new Result(result, finishTime-startTime);
    }
}
