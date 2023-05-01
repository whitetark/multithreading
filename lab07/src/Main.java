import mpi.MPI;
import mpi.MPIException;

public class Main {
    public static int MASTER = 0;
    public static int matrixSize = 1000;
    public static void main(String[] args) {
        double[][] matrixA = new double[matrixSize][matrixSize];
        double[][] matrixB = new double[matrixSize][matrixSize];
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if(rank == MASTER){
            matrixA = Methods.generateMatrix(matrixSize);
            matrixB = Methods.generateMatrix(matrixSize);
            startTime = System.currentTimeMillis();
        }

        int count = matrixA.length/size;

        double[][] blockA = new double[count][matrixSize];
        MPI.COMM_WORLD.Scatter(matrixA, 0, count, MPI.OBJECT, blockA, 0, count, MPI.OBJECT, 0);
        MPI.COMM_WORLD.Bcast(matrixB,0, matrixB.length, MPI.OBJECT,0);

        MPI.COMM_WORLD.Barrier();
        double[][] subResult = Methods.multiply(blockA, matrixB);

        double[][] result = new double[matrixSize][matrixSize];
        MPI.COMM_WORLD.Gather(subResult,0, subResult.length, MPI.OBJECT, result,0, subResult.length, MPI.OBJECT,0);

        if(rank == MASTER){
            endTime = System.currentTimeMillis();
            Methods.print(result);
            System.out.println("time " + (endTime-startTime));
            System.out.println(result.length);
        }
        MPI.Finalize();
    }
}
