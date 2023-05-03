import Algorithms.Fox;
import Algorithms.Standard;
import Algorithms.Striped2;
import Models.Matrix;
import Models.Result;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        int sizeOfMatrix = 1000;
        int numOfThreads = 8;
        int blockSize = 25;

        Matrix matrix1 = new Matrix(sizeOfMatrix,sizeOfMatrix);
        matrix1.generateRandomMatrix();
        System.out.println("Matrix1 Before:");
        //matrix1.print();
        System.out.println();

        Matrix matrix2 = new Matrix(sizeOfMatrix,sizeOfMatrix);
        matrix2.generateRandomMatrix();
        System.out.println("Matrix2 Before:");
        //matrix2.print();
        System.out.println();

        Standard standard = new Standard(matrix1, matrix2);
        Result resultOfStandard = standard.multiply();
        System.out.println("Matrix After Standard Multiply:");
        //resultOfStandard.matrix.print();
        System.out.println("Time:" + resultOfStandard.time);
        System.out.println();

        Striped2 striped2 = new Striped2(matrix1, matrix2, numOfThreads);
        Result resultOfStriped2 = striped2.multiply();
        System.out.println("Matrix After Striped Callable Multiply:");
        //resultOfStriped2.matrix.print();
        System.out.println("Time:" + resultOfStriped2.time);
        System.out.println();

        Fox fox = new Fox(matrix1, matrix2, numOfThreads, blockSize);
        Result resultOfFox = fox.multiply();
        System.out.println("Matrix After Fox Multiply:");
        //resultOfFox.matrix.print();
        System.out.println("Time:" + resultOfFox.time);
        System.out.println();

    }
}