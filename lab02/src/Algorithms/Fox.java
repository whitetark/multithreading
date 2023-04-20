package Algorithms;

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
    private int blockSize;

    public Fox(Matrix matrix1, Matrix matrix2, int numOfThreads, int blockSize){
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.numOfThreads = numOfThreads;
        this.blockSize = blockSize;
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
        ArrayList<Callable<Object>> tasks = new ArrayList<>();
        int numOfBlocks = matrix1.rows / blockSize;
        for (int i = 0; i < numOfBlocks; i++) {
            FoxThread thread = new FoxThread(blocksResult, blocksMatrix1, blocksMatrix2, i, blockSize);
            tasks.add(Executors.callable(thread));
        }
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();

        for (int i = 0; i < numOfBlocks; i++) {
            for (int j = 0; j < numOfBlocks; j++) {
                double[][] blockMatrix = blocksResult[i][j].items;
                for (int k = 0; k < blockSize; k++) {
                    for(int l = 0; l < blockSize; l++){
                        result.items[k + (i * blockSize)][l + (j * blockSize)] = blockMatrix[k][l];
                    }
                }
            }
        }
        long finishTime = System.currentTimeMillis();
        return new Result(result, finishTime-startTime);
    }
}
