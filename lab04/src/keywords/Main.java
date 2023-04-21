package keywords;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        File directory = new File("src/files");

        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("html");
        keywords.add("programming");
        keywords.add("database");
        keywords.add("framework");
        keywords.add("class");

        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        Analyzer task = new Analyzer(directory, keywords);
        pool.invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.println("Time: " + (endTime - startTime) + " ms");
        if(task.foundKeywords != null){
            for (Document document : task.foundKeywords){
                System.out.println("Document is: " + document.path);
                System.out.println("Count of Keywords: " + document.keywords.size());
                System.out.println("Found Keywords: " + document.keywords);
            }
        } else{
            System.out.println("There are no files with keywords");
        }
    }
}