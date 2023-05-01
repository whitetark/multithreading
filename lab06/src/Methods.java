public class Methods {
    public static double[][] generateMatrix(int size){
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            double[] row = new double[size];
            for (int j = 0; j < size; j++) {
                row[j] = 1;
            }
            matrix[i] = row;
        }
        return matrix;
    }

    public static double[][] multiply(double[][] matrix1, double[][] matrix2){
        double[][] result = new double[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < matrix1[0].length; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    public static double[][] getBlock(double[][] matrix, int start, int finish, int cols){
        double[][] result = new double[finish - start + 1][cols];
        int resultIndex = 0;
        for(int i = start; i < finish; i++){
            double[] temp = new double[cols];
            for(int j = 0; j < cols; j++){
                temp[j] = matrix[i][j];
            }
            result[resultIndex] = temp;
            resultIndex++;
        }
        return result;
    }

    public static double[][] addBlock(double[][] part, double[][] matrix, int start, int finish){
        double[][] result = matrix;
        int partRowIndex= 0;
        for(int i = start; i < finish; i++){
            for(int j = 0; j < matrix[0].length; j++){
                result[i][j] = part[partRowIndex][j];
            }
            partRowIndex++;
        }
        return result;
    }

    public static void print(double[][] matrix){
        for(int i = 0 ;i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}