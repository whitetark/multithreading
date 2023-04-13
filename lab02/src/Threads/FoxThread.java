package Threads;

import Models.BlockMatrix;
import Models.Matrix;

import static Models.BlockMatrix.add;
import static Models.BlockMatrix.multiply;

public class FoxThread extends Thread{
    private Matrix[][] blocks1;
    private Matrix[][] blocks2;
    private Matrix[][] result;
    private int row;
    private int blockSize;
    public FoxThread(Matrix[][] result, Matrix[][] blocks1, Matrix[][] blocks2, int row, int blockSize) {
        this.result = result;
        this.blocks1 = blocks1;
        this.blocks2 = blocks2;
        this.row = row;
        this.blockSize = blockSize;
    }

    @Override
    public void run() {
        for (int i = 0; i < blocks2[0].length; i++) {
            BlockMatrix resultBlock = new BlockMatrix(new double[blockSize][blockSize]);
            for (int j = 0; j < blocks1[0].length; j++) {
                BlockMatrix subBlockA = new BlockMatrix(blocks1[row][j].items);
                BlockMatrix subBlockB = new BlockMatrix(blocks2[j][i].items);

                BlockMatrix subResult = multiply(subBlockA, subBlockB);
                resultBlock = add(resultBlock, subResult);
            }
            result[row][i].items = resultBlock.items;
        }
    }
}
