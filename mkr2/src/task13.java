import mpi.MPI;

import java.util.Random;

public class task13 {
    public static int Master = 0;
    public static int Third_Worker = 2;
    public static int From_Worker = 1;
    public static void main(String[] args) {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (rank == Master) {
            int[] temperature = new int[30];
            MPI.COMM_WORLD.Recv(temperature, 0, 30, MPI.INT, Third_Worker, From_Worker);
            for (int dayTemp : temperature) {
                System.out.println(dayTemp);
            }
        } else if (rank == Third_Worker) {
            Random rnd = new Random();
            int[] temperature = new int[30];
            for (int i = 0; i < temperature.length; i++) {
                temperature[i] = rnd.nextInt(30 - 20 + 1) + 20;
            }
            MPI.COMM_WORLD.Send(temperature, 0, temperature.length, MPI.INT, Master, From_Worker);
        }

        MPI.Finalize();
    }
}
