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
            System.out.println("Started with " + size + " tasks");
            matrixA = Methods.generateMatrix(matrixSize);
            matrixB = Methods.generateMatrix(matrixSize);
            startTime = System.currentTimeMillis();
        }

        int work = matrixSize / size;
        int extra = matrixSize % size;

        int[] sendcount = new int[size];
        int[] displace = new int[size];

        displace[0] = 0;
        for(int i = 1; i < size; i++){
            sendcount[i-1] = work;
            if(i == size - 1){
                sendcount[i-1] += extra;
            }
            displace[i] = sendcount[i-1] + displace[i-1];
        }
        sendcount[size-1] = work;

        int currentWork = sendcount[rank];

        double[][] blockA = new double[currentWork][matrixSize];
        MPI.COMM_WORLD.Scatterv(matrixA, 0, sendcount, displace, MPI.OBJECT, blockA, 0, currentWork, MPI.OBJECT, 0);
        MPI.COMM_WORLD.Bcast(matrixB,0, matrixB.length, MPI.OBJECT,0);

        MPI.COMM_WORLD.Barrier();
        double[][] subResult = Methods.multiply(blockA, matrixB);

        double[][] result = new double[matrixSize][matrixSize];
        MPI.COMM_WORLD.Gatherv(subResult,0, subResult.length, MPI.OBJECT, result,0, sendcount, displace, MPI.OBJECT,0);

        if(rank == MASTER){
            endTime = System.currentTimeMillis();
            //Methods.print(result);
            System.out.println("Time: " + (endTime-startTime));
        }
        MPI.Finalize();
    }
}
