package samewords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class Analyzer extends RecursiveTask<ArrayList<String>> {
    private final File directory;

    public Analyzer(File directory) {
        this.directory = directory;
    }

    @Override
    protected ArrayList<String> compute() {
        ArrayList<Analyzer> subTasks = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();

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
                    if (result.isEmpty()) {
                        result.addAll(words);
                    }
                    else{
                        result.retainAll(words);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Analyzer subTask : subTasks) {
            result.retainAll(subTask.join());
        }
        return result;
    }

    private List<String> getWords(String line){
        String[] words = line.split("\\s+");
        List<String> result = Arrays.asList(words);
        result.replaceAll(String::toLowerCase);
        return result;
    }
}