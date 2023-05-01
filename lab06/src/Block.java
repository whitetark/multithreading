import mpi.*;

import static java.lang.System.exit;
public class Block {
    private static int matrixSize = 1000;
    private static int MASTER = 0;
    private static int FROM_MASTER = 1;
    private static int FROM_WORKER = 1;

    public static void main(String[] args) throws MPIException {
        double[][] matrixA = Methods.generateMatrix(matrixSize);
        double[][] matrixB = Methods.generateMatrix(matrixSize);
        double[][] result = new double[matrixSize][matrixSize];
        double startTime, endTime;

        MPI.Init(args);
        int size = MPI.COMM_WORLD.Size();
        int rank = MPI.COMM_WORLD.Rank();

        int countOfWorkers = size - 1;

        if(size < 2 || matrixSize < countOfWorkers){
            System.out.println("Num Of Workers Is Small");
            MPI.COMM_WORLD.Abort(1);
            exit(1);
        }

        if(rank == MASTER){
            System.out.println("Started with " + size + " tasks");
            startTime = MPI.Wtime();
            int work = matrixSize / countOfWorkers;
            int extra = matrixSize % countOfWorkers;
            for(int worker = 1; worker <= countOfWorkers; worker++){
                int rowStart = (worker - 1) * work;
                int rowFinish = rowStart + work;
                if(worker == countOfWorkers){
                    rowFinish+=extra;
                }

                double[][] blockA = Methods.getBlock(matrixA, rowStart, rowFinish, matrixSize);

                MPI.COMM_WORLD.Send(new int[]{rowStart}, 0,  1, MPI.INT, worker, FROM_MASTER);
                MPI.COMM_WORLD.Send(new int[]{rowFinish}, 0,  1, MPI.INT, worker, FROM_MASTER);
                MPI.COMM_WORLD.Send(blockA, 0, blockA.length , MPI.OBJECT, worker, FROM_MASTER);
                MPI.COMM_WORLD.Send(matrixB, 0, matrixB.length , MPI.OBJECT, worker, FROM_MASTER);
            }

            for(int worker = 1; worker <= countOfWorkers; worker++){
                int[] rowStart = new int[1];
                int[] rowFinish = new int[1];

                MPI.COMM_WORLD.Recv(rowStart, 0, 1, MPI.INT, worker, FROM_WORKER);
                MPI.COMM_WORLD.Recv(rowFinish, 0, 1, MPI.INT, worker, FROM_WORKER);

                double[][] openedResult = new double[rowFinish[0] - rowStart[0] + 1][matrixSize];
                MPI.COMM_WORLD.Recv(openedResult, 0, openedResult.length, MPI.OBJECT, worker, FROM_WORKER);

                Methods.addBlock(openedResult, result, rowStart[0], rowFinish[0]);
            }
            endTime = MPI.Wtime();
            System.out.println("Result: ");
            Methods.print(result);
            System.out.println(endTime-startTime + " seconds");
        } else {
            int[] rowStart = new int[1];
            int[] rowFinish = new int[1];

            MPI.COMM_WORLD.Recv(rowStart, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(rowFinish, 0, 1, MPI.INT, MASTER, FROM_MASTER);

            double[][] openedBlockA = new double[rowFinish[0] - rowStart[0] + 1][matrixSize];
            double[][] openedMatrixB = new double[matrixSize][matrixSize];

            MPI.COMM_WORLD.Recv(openedBlockA, 0, openedBlockA.length, MPI.OBJECT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(openedMatrixB, 0, openedMatrixB.length, MPI.OBJECT, MASTER, FROM_MASTER);
            System.out.println("Row start: " + rowStart[0] + " Row finish " + rowFinish[0] + " From task " + rank);

            double[][] subResult = Methods.multiply(openedBlockA, openedMatrixB);

            MPI.COMM_WORLD.Send(rowStart, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(rowFinish, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(subResult, 0, subResult.length, MPI.OBJECT, MASTER, FROM_WORKER);
        }
        MPI.Finalize();
    }
}