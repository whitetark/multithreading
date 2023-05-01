import mpi.*;

import static java.lang.System.exit;

public class Nonblock {
    private static int matrixSize = 1000;
    private static int MASTER = 0;
    private static  int FROM_MASTER = 1;
    private static  int FROM_WORKER = 1;

    public static void main(String[] args) throws MPIException {
        float[][] matrixA = Methods.generateMatrix(matrixSize);
        float[][] matrixB = Methods.generateMatrix(matrixSize);
        float[][] result = new float[matrixSize][matrixSize];
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
                    rowFinish += extra;
                }
                Request send1 = MPI.COMM_WORLD.Isend(new int[]{rowStart}, 0,  1, MPI.INT, worker, FROM_MASTER);
                Request send2 =  MPI.COMM_WORLD.Isend(new int[]{rowFinish}, 0,  1, MPI.INT, worker, FROM_MASTER);

                float[][] blockA = Methods.getBlock(matrixA, rowStart, rowFinish, matrixSize);
                byte[] blockABuffer = Methods.createBuffer(blockA);
                byte[] matrixBBuffer = Methods.createBuffer(matrixB);
                int blockAElements = (rowFinish - rowStart + 1) * matrixSize;

                Request send3 =  MPI.COMM_WORLD.Isend(blockABuffer, 0, blockAElements * 4, MPI.BYTE, worker, FROM_MASTER);
                Request send4 = MPI.COMM_WORLD.Isend(matrixBBuffer, 0, matrixSize * matrixSize * 4, MPI.BYTE, worker, FROM_MASTER);

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

                int resultElements = (rowFinish[0] - rowStart[0] + 1) * matrixSize;
                byte[] resultBuffer = new byte[resultElements * 4];

                Request rec3 = MPI.COMM_WORLD.Irecv(resultBuffer, 0, resultElements * 4, MPI.BYTE, worker, FROM_WORKER);

                rec3.Wait();

                float[][] openedResult = Methods.openFromBuffer(resultBuffer, rowFinish[0] - rowStart[0], matrixSize);
                Methods.addPart(openedResult, result, rowStart[0], rowFinish[0]);
            }
            endTime = MPI.Wtime();
            System.out.println("Result :");
            Methods.print(result);
            System.out.println(endTime-startTime + " seconds");
        } else {
            int[] rowStart = new int[1];
            int[] rowFinish = new int[1];
            Request rec1 = MPI.COMM_WORLD.Irecv(rowStart, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            Request rec2 = MPI.COMM_WORLD.Irecv(rowFinish, 0,  1, MPI.INT, MASTER, FROM_MASTER);

            rec1.Wait();
            rec2.Wait();

            int blockAElements = (rowFinish[0] - rowStart[0] + 1) * matrixSize;
            byte[] blockABuffer = new byte[blockAElements*4];
            byte[] matrixBBuffer = new byte[matrixSize * matrixSize * 4];

            Request rec3 = MPI.COMM_WORLD.Irecv(blockABuffer, 0, blockAElements * 4, MPI.BYTE, MASTER, FROM_MASTER);
            Request rec4 = MPI.COMM_WORLD.Irecv(matrixBBuffer, 0, matrixSize * matrixSize * 4, MPI.BYTE, MASTER, FROM_MASTER);

            rec3.Wait();
            rec4.Wait();
            System.out.println("Row start: " + rowStart[0] + " Row finish " + rowFinish[0] + " From task " + rank);

            float[][] openedMatrixB = Methods.openFromBuffer(matrixBBuffer, matrixSize, matrixSize);
            float[][] openedBlockA = Methods.openFromBuffer(blockABuffer, rowFinish[0] - rowStart[0], matrixSize);
            float[][] subResult = Methods.multiply(openedBlockA, openedMatrixB);

            byte[] subResultBuffer = Methods.createBuffer(subResult);

            MPI.COMM_WORLD.Send(rowStart, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(rowFinish, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(subResultBuffer, 0, subResultBuffer.length, MPI.BYTE, MASTER, FROM_WORKER);
        }
        MPI.Finalize();
    }
}
