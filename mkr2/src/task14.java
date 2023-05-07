import mpi.MPI;

import java.util.Random;

public class task14 {
    public static void main(String[] args){
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        Random rnd = new Random();
        int[] arr = new int[5];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt(10);
            System.out.println("Element " + i + ": " + arr[i] + " rank: "+ rank);
        }
        //int[] sum = new int[5];
        int[] sum = new int[1];
        int subSum = 0;
        for (int i = 0; i < arr.length; i++){
            subSum += arr[i];
        }
        MPI.COMM_WORLD.Allreduce(new int[]{ subSum }, 0, sum, 0, 1, MPI.INT, MPI.SUM);
        System.out.println(sum[0]);
        //MPI.COMM_WORLD.Allreduce(arr, 0, sum, 0, arr.length, MPI.INT, MPI.SUM);

        if(rank == 0){
            /*
            for (int i = 0; i < sum.length; i++) {
                System.out.println("Element " + i + " Sum: " + sum[i] + " rank: "+ rank);
            }
             */
            System.out.println(sum[0]);
        }
        MPI.Finalize();
    }
}
