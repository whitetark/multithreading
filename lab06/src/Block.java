import mpi.*;

import static java.lang.System.exit;
public class Block {
    private static int matrixSize = 1000;
    private static int MASTER = 0;
    private static int FROM_MASTER = 1;
    private static int FROM_WORKER = 1;

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
                    rowFinish+=extra;
                }

                float[][] blockA = Methods.getBlock(matrixA, rowStart, rowFinish, matrixSize);
                byte[] blockABuffer = Methods.createBuffer(blockA);
                byte[] matrixBBuffer = Methods.createBuffer(matrixB);
                int blockAElements = (rowFinish - rowStart + 1) * matrixSize;

                MPI.COMM_WORLD.Send(new int[]{rowStart}, 0,  1, MPI.INT, worker, FROM_MASTER);
                MPI.COMM_WORLD.Send(new int[]{rowFinish}, 0,  1, MPI.INT, worker, FROM_MASTER);
                MPI.COMM_WORLD.Send(blockABuffer, 0, blockAElements * 4, MPI.BYTE, worker, FROM_MASTER);
                MPI.COMM_WORLD.Send(matrixBBuffer, 0, matrixSize*matrixSize * 4, MPI.BYTE, worker, FROM_MASTER);
            }

            for(int worker = 1; worker <= countOfWorkers; worker++){
                int[] rowStart = new int[1];
                int[] rowFinish = new int[1];

                MPI.COMM_WORLD.Recv(rowStart, 0, 1, MPI.INT, worker, FROM_WORKER);
                MPI.COMM_WORLD.Recv(rowFinish, 0, 1, MPI.INT, worker, FROM_WORKER);

                int resultElements = (rowFinish[0] - rowStart[0] + 1) * matrixSize;
                byte[] resultBuffer = new byte[resultElements*4];

                MPI.COMM_WORLD.Recv(resultBuffer, 0, resultElements * 4, MPI.BYTE, worker, FROM_WORKER);

                float[][] openedResult = Methods.openFromBuffer(resultBuffer, rowFinish[0] - rowStart[0], matrixSize);
                Methods.addPart(openedResult, result, rowStart[0], rowFinish[0]);

            }
            endTime = MPI.Wtime();
            System.out.println("Result: ");
            //Methods.print(result);
            System.out.println(endTime-startTime + " seconds");
        } else {
            int[] rowStart = new int[1];
            int[] rowFinish = new int[1];

            MPI.COMM_WORLD.Recv(rowStart, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(rowFinish, 0, 1, MPI.INT, MASTER, FROM_MASTER);

            int blockAElements = (rowFinish[0] - rowStart[0] + 1) * matrixSize;
            byte[] blockABuffer = new byte[blockAElements * 4];
            byte[] matrixBBuffer = new byte[matrixSize * matrixSize * 4];

            MPI.COMM_WORLD.Recv(blockABuffer, 0, blockAElements * 4, MPI.BYTE, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(matrixBBuffer, 0, matrixSize * matrixSize * 4, MPI.BYTE, MASTER, FROM_MASTER);
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