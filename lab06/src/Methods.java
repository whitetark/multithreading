import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Methods {
    public static float[][] generateMatrix(int size){
        float[][] matrix = new float[size][size];
        for (int i = 0; i < size; i++) {
            float[] row = new float[size];
            for (int j = 0; j < size; j++) {
                row[j] = 1;
            }
            matrix[i] = row;
        }
        return matrix;
    }

    public static void print(float[][] matrix){
        for(int i = 0 ;i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static float[][] getBlock(float[][] matrix, int rowStart, int rowFinish, int cols){
        float[][] result = new float[rowFinish - rowStart + 1][cols];
        int resultIndex = 0;
        for(int i = rowStart; i < rowFinish; i++){
            float[] temp = new float[cols];
            for(int j = 0; j < cols; j++){
                temp[j] = matrix[i][j];
            }
            result[resultIndex] = temp;
            resultIndex++;
        }
        return result;
    }

    public static byte[] createBuffer(float[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        ByteBuffer buffer = ByteBuffer.allocate(rows * cols * 4);
        buffer.order(ByteOrder.nativeOrder());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buffer.putFloat(array[i][j]);
            }
        }
        return buffer.array();
    }

    public static float[][] openFromBuffer(byte[] bytes, int rows, int cols) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.nativeOrder());
        float[][] array = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = buffer.getFloat();
            }
        }
        return array;
    }

    public static float[][] multiply(float[][] matrix1, float[][] matrix2){
        float[][] result = new float[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                float sum = 0;
                for (int k = 0; k < matrix1[0].length; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    public static float[][] addPart(float[][] part, float[][] matrix, int start, int finish){
        float[][] result = matrix;
        int partRowIndex= 0;
        for(int i = start; i < finish; i++){
            for(int j = 0; j < matrix[0].length; j++){
                result[i][j] = part[partRowIndex][j];
            }
            partRowIndex++;
        }
        return result;
    }
}