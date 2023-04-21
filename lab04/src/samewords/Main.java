package samewords;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        File directory = new File("out/test");
        System.out.println(directory.getAbsolutePath());
        long startTime = System.currentTimeMillis();
        Analyzer task = new Analyzer(directory);
        pool.invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.println("Time: " + (endTime - startTime) + " ms");
        System.out.println("Common words are: " + task.commonWords);
    }
}