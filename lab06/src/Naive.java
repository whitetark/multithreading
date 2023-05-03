
public class Naive {
    private static int matrixSize = 3000;
    public static void main(String[] args) {
        double[][] matrixA = Methods.generateMatrix(matrixSize);
        double[][] matrixB = Methods.generateMatrix(matrixSize);
        double[][] result = new double[matrixSize][matrixSize];

        long startTime = System.currentTimeMillis();
        result = Methods.multiply(matrixA, matrixB);
        long endTime = System.currentTimeMillis();

        System.out.println("Matrix Size is " + matrixSize);
        System.out.println("Time: " + (endTime-startTime) +" ms");
    }
}
