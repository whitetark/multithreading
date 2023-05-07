import mpi.*;
import java.util.Arrays;
import java.util.Random;

public class task16 {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static void main(String[] args) {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int work = 15;
        String[] A = new String[size * work];
        if (rank == 0) {
            for (int i = 0; i < A.length; i++) {
                A[i] = generateRandomWord(10);
            }
        }

        String[] block = new String[work];
        MPI.COMM_WORLD.Scatter(A, 0, work, MPI.OBJECT, block, 0, work, MPI.OBJECT, 0);

        Arrays.sort(block);

        String[] result = new String[work];
        MPI.COMM_WORLD.Alltoall(block, 0, work/size, MPI.OBJECT, result, 0, work/size, MPI.OBJECT);

        if (rank == 0) {
            for (String str : result) {
                System.out.println(str);
            }
        }

        MPI.Finalize();
    }

    public static String generateRandomWord(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHABET.length());
            char c = ALPHABET.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }
}