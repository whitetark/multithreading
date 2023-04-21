package stats;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static stats.Methods.countWordsSymbols;
import static stats.Methods.getWords;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "src/files/file1";
        ArrayList<String> words = getWords(path);

        long startTimePar = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        List<Integer> result1 = pool.submit(new Counter(words)).join();
        long endTimePar =  System.nanoTime();

        System.out.println("Time Fork: " + (endTimePar - startTimePar)/1000 + " nms");

        long startTimeSingle =  System.nanoTime();
        ArrayList<Integer> result2 = countWordsSymbols(words);
        long endTimeSingle =  System.nanoTime();

        System.out.println("Time Single: " + (endTimeSingle - startTimeSingle)/1000 + " nms");
    }

}