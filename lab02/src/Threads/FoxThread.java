package Threads;

import Models.Matrix;
import static Models.Matrix.add;
import static Models.Matrix.multiply;

public class FoxThread extends Thread{
    private Matrix[][] blocksMatrix1;
    private Matrix[][] blocksMatrix2;
    private Matrix[][] result;
    private int row;
    private int blockSize;
    public FoxThread(Matrix[][] result, Matrix[][] blocksMatrix1, Matrix[][] blocksMatrix2, int row, int blockSize) {
        this.result = result;
        this.blocksMatrix1 = blocksMatrix1;
        this.blocksMatrix2 = blocksMatrix2;
        this.row = row;
        this.blockSize = blockSize;
    }

    @Override
    public void run() {
        for (int i = 0; i < blocksMatrix2[0].length; i++) {
            Matrix resultBlock = new Matrix(new double[blockSize][blockSize]);
            for (int j = 0; j < blocksMatrix1[0].length; j++) {
                Matrix block1 = new Matrix(blocksMatrix1[row][j].items);
                Matrix block2 = new Matrix(blocksMatrix2[j][i].items);

                Matrix temp = multiply(block1, block2);
                resultBlock = add(resultBlock, temp);
            }
            result[row][i].items = resultBlock.items;
        }
    }
}
