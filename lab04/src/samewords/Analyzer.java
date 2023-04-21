package samewords;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class Analyzer extends RecursiveAction {
    private final File directory;
    public static ArrayList<String> commonWords = new ArrayList<>();

    public Analyzer(File directory) {
        this.directory = directory;
    }

    @Override
    protected void compute() {

    }
}