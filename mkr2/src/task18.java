import mpi.MPI;
import java.util.Random;

public class task18 {
//Замість Bcast мав бути Scatter, забагато передаєте, неефективно використовуєте ресурс
    public static void main(String[] args) {

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int work = 500;
        int matrixSize = size * work;

        int[][] A = new int[matrixSize][matrixSize];
        int[][] B = new int[matrixSize][matrixSize];
        int[] C = new int[matrixSize];

        if (rank == 0) {
            A = createMatrix(matrixSize);
            B = createMatrix(matrixSize);
        }

        MPI.COMM_WORLD.Bcast(A, 0, matrixSize, MPI.OBJECT, 0);
        MPI.COMM_WORLD.Bcast(B, 0, matrixSize, MPI.OBJECT, 0);

        MPI.COMM_WORLD.Barrier();

        int[] subResult = new int[work];
        int offset = rank * work;

        for (int i = 0; i < work; i++) {
            subResult[i] = addElements(A[offset + i]) * addElements(B[offset + i]);
        }

        MPI.COMM_WORLD.Gather(subResult,0, work, MPI.INT, C, offset, work, MPI.INT, 0);

        if (rank == 0) {
            for (int c: C){
                System.out.println(c);
            }
        }

        MPI.Finalize();
    }

    private static int[][] createMatrix(int size) {
        int[][] matrix = new int[size][size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt(100);
            }
        }
        return matrix;
    }

    private static int addElements(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }
}
