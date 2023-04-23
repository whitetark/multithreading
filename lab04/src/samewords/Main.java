package samewords;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        File directory = new File("src/files");

        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Analyzer task = new Analyzer(directory);
        ArrayList<String> result = pool.invoke(task);
        Set<String> commonWords = new HashSet<>(result);
        long endTime = System.currentTimeMillis();

        if(commonWords.size() != 0) {
            System.out.println("Time: " + (endTime - startTime) + " ms");
            System.out.println("Count of Common Words: " + commonWords.size());
            System.out.println("Common Words: " + commonWords);
        } else{
            System.out.println("There are no common words");
        }
    }
}