package supastriped;

public class Matrix {
    public double[][] items;
    public int rows;
    public int cols;

    public Matrix(int rows, int cols) {
        this.items = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(double[][] items) {
        this.items = items;
        this.rows = items.length;
        this.cols = items[0].length;
    }

    public void print() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.printf("%10.1f", this.items[i][j]);
            }
            System.out.println();
        }
    }

    public Matrix transpose() {
        Matrix result = new Matrix(this.cols, this.rows);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.items[j][i] = this.items[i][j];
            }
        }
        return result;
    }

    public static Matrix add(Matrix matrix1, Matrix matrix2) {
        Matrix result = new Matrix(matrix1.rows, matrix1.rows);

        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.items[i][j] = matrix1.items[i][j] + matrix2.items[i][j];
            }
        }
        return result;
    }

    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        Matrix result = new Matrix(matrix1.rows, matrix2.cols);

        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                for (int k = 0; k < matrix1.cols; k++) {
                    result.items[i][j] += matrix1.items[i][k] * matrix2.items[k][j];
                }
            }
        }
        return result;
    }

    public double[] getRow(int row) {
        return this.items[row];
    }

    public double[] getColumn(int index) {
        double[] column = new double[this.cols];
        for (int i = 0; i < this.rows; i++) {
            column[i] = (items[i][index]);
        }
        return column;
    }

    public void generateRandomMatrix() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.items[i][j] = Math.random();
            }
        }
    }
}
