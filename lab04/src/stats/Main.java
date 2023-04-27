package stats;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static stats.Methods.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "src/files/file1";
        ArrayList<String> words = getWords(path);

        //Fork
        long startTimePar = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Word> result1  = pool.invoke(new CounterTask(words));
        long timePar =  (System.nanoTime() - startTimePar)/1000;
        System.out.println("Time Fork: " + timePar + " μs");

        //Single
        long startTimeSingle =  System.nanoTime();
        ArrayList<Word> result2 = countWordsSymbols(words);
        long timeSingle =  (System.nanoTime() - startTimeSingle)/1000;
        System.out.println("Time Single: " + timeSingle + " μs");

        System.out.println("Speed Up: " + (double)timeSingle/timePar + " μs");
        //Stats
        System.out.println();
        System.out.println("Block Of Statistics:");
        System.out.println("General Num Of Words: " + words.size());
        System.out.println("Fork Num Of Words: " + result1.size());
        System.out.println("Single Num Of Words: " + result2.size());
        System.out.println("Overall Symbol Length: " + getWordLengthSum(result1));
        System.out.println("Average Word Length: " + getAverageWordLength(result1));
        System.out.println("Dispersion: " + getDispersion(result1));
        System.out.println("Standard Deviation: " + getStandardDeviation(result1));
    }
}