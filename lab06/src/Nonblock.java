import mpi.*;

import static java.lang.System.exit;

public class Nonblock {
    private static int matrixSize = 1000;
    private static int MASTER = 0;
    private static  int FROM_MASTER = 1;
    private static  int FROM_WORKER = 1;

    public static void main(String[] args) throws MPIException {
        double[][] matrixA = Methods.generateMatrix(matrixSize);
        double[][] matrixB = Methods.generateMatrix(matrixSize);
        double[][] result = new double[matrixSize][matrixSize];
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

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
            System.out.println("Matrix Size is " + matrixSize);
            System.out.println("Started with " + size + " tasks");
            int work = matrixSize / countOfWorkers;
            int extra = matrixSize % countOfWorkers;
            for(int worker = 1; worker <= countOfWorkers; worker++){
                int rowStart = (worker - 1) * work;
                int rowFinish = rowStart + work;
                if(worker == countOfWorkers){
                    rowFinish += extra;
                }
                Request send1 = MPI.COMM_WORLD.Isend(new int[]{rowStart}, 0,  1, MPI.INT, worker, FROM_MASTER);
                Request send2 =  MPI.COMM_WORLD.Isend(new int[]{rowFinish}, 0,  1, MPI.INT, worker, FROM_MASTER);

                double[][] blockA = Methods.getBlock(matrixA, rowStart, rowFinish, matrixSize);

                Request send3 =  MPI.COMM_WORLD.Isend(blockA, 0, blockA.length, MPI.OBJECT, worker, FROM_MASTER);
                Request send4 = MPI.COMM_WORLD.Isend(matrixB, 0, matrixB.length, MPI.OBJECT, worker, FROM_MASTER);

                send1.Wait();
                send2.Wait();
                send3.Wait();
                send4.Wait();
            }

            for(int worker = 1; worker <= countOfWorkers; worker++){
                int[] rowStart = new int[1];
                int[] rowFinish = new int[1];

                Request rec1 = MPI.COMM_WORLD.Irecv(rowStart, 0, 1, MPI.INT, worker, FROM_WORKER);
                Request rec2 = MPI.COMM_WORLD.Irecv(rowFinish, 0, 1, MPI.INT, worker, FROM_WORKER);

                rec1.Wait();
                rec2.Wait();

                double[][] openedResult = new double[rowFinish[0] - rowStart[0] + 1][matrixSize];

                Request rec3 = MPI.COMM_WORLD.Irecv(openedResult, 0, openedResult.length, MPI.OBJECT, worker, FROM_WORKER);
                rec3.Wait();

                Methods.addBlock(openedResult, result, rowStart[0], rowFinish[0]);
            }
            endTime = System.currentTimeMillis();
            System.out.println("Result :");
            //Methods.print(result);
            System.out.println("Time: " +(endTime-startTime) + " ms");
        } else {
            int[] rowStart = new int[1];
            int[] rowFinish = new int[1];
            Request rec1 = MPI.COMM_WORLD.Irecv(rowStart, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            Request rec2 = MPI.COMM_WORLD.Irecv(rowFinish, 0,  1, MPI.INT, MASTER, FROM_MASTER);

            rec1.Wait();
            rec2.Wait();

            double[][] openedBlockA = new double[rowFinish[0] - rowStart[0] + 1][matrixSize];
            double[][] openedMatrixB = new double[matrixSize][matrixSize];

            Request rec3 = MPI.COMM_WORLD.Irecv(openedBlockA, 0, openedBlockA.length, MPI.OBJECT, MASTER, FROM_MASTER);
            Request rec4 = MPI.COMM_WORLD.Irecv(openedMatrixB, 0, openedMatrixB.length, MPI.OBJECT, MASTER, FROM_MASTER);

            rec3.Wait();
            rec4.Wait();
            System.out.println("Row start: " + rowStart[0] + " Row finish: " + rowFinish[0] + " From task " + rank);

            double[][] subResult = Methods.multiply(openedBlockA, openedMatrixB);

            MPI.COMM_WORLD.Send(rowStart, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(rowFinish, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(subResult, 0, subResult.length, MPI.OBJECT, MASTER, FROM_WORKER);
        }
        MPI.Finalize();
    }
}
