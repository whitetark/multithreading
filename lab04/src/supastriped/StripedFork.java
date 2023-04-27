package supastriped;

import java.util.concurrent.RecursiveTask;

import static supastriped.Matrix.add;

public class StripedFork extends RecursiveTask<Matrix> {
    private Matrix matrix1;
    private Matrix matrix2;
    private int start;
    private int end;
    public StripedFork(Matrix matrix1, Matrix matrix2, int start, int end){
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Matrix compute() {
        Matrix result = new Matrix(matrix1.rows, matrix2.cols);
        if (end-start >= 100){
            StripedFork subTask1 = new StripedFork(matrix1, matrix2, start, (start + end) / 2);
            StripedFork subTask2 = new StripedFork(matrix1, matrix2, (start + end) / 2, end);
            invokeAll(subTask1, subTask2);
            add(result, subTask1.join());
            add(result, subTask2.join());
        } else {
            for (int i = start; i < end; i++) {
                for (int j = 0; j < matrix2.cols; j++) {
                    for (int k = 0; k < matrix1.cols; k++) {
                        result.items[i][j] += matrix1.items[i][k] * matrix2.items[k][j];
                    }
                }
            }
        }

        return result;
    }
}
