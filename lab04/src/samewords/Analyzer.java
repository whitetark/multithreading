package samewords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Analyzer extends RecursiveAction {
    private final File directory;
    public static ArrayList<String> commonWords = new ArrayList<>();

    public Analyzer(File directory) {
        this.directory = directory;
    }

    @Override
    protected void compute() {
        ArrayList<Analyzer> subTasks = new ArrayList<>();

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                Analyzer subTask = new Analyzer(file);
                subTasks.add(subTask);
                subTask.fork();
            } else {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    ArrayList<String> words = new ArrayList<>();
                    while ((line = reader.readLine()) != null) {
                        List<String> lineWords = getWords(line);
                        words.addAll(lineWords);
                    }
                    if (commonWords.isEmpty()) {
                        commonWords.addAll(words);
                    }
                    else{
                        commonWords.retainAll(words);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Analyzer subTask : subTasks) {
            subTask.join();
        }
    }

    private List<String> getWords(String line){
        String[] words = line.split("\\s+");
        return Arrays.asList(words);
    }
}