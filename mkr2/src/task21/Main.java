package task21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "src/task21/file";
        ArrayList<String> words = getWords(path);

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        List<Double> result = pool.invoke(new ForkJoin(words));
        System.out.println(result.get(0));
        System.out.println(result.get(1));
        Double average = result.get(1)/result.get(0);
        System.out.println(average);
    }
    public static ArrayList<String> getWords(String path) throws IOException {
        ArrayList<String> wordList = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            String[] words = line.split("\\s+");

            wordList.addAll(Arrays.asList(words));
        }
        return wordList;
    }
}
