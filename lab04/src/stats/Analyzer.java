package stats;

import java.io.File;
import java.util.*;
import java.util.concurrent.RecursiveAction;

public class Analyzer extends RecursiveAction {
    private final File directory;

    public Analyzer(File directory) {
        this.directory = directory;
    }

    @Override
    protected void compute() {

    }
    private List<String> getWords(String line){
        String[] words = line.split("\\s+");
        return Arrays.asList(words);
    }
}