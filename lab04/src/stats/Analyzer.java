package stats;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class Analyzer extends RecursiveAction {
    private final File directory;

    public Analyzer(File directory) {
        this.directory = directory;
    }

    @Override
    protected void compute() {

    }
}