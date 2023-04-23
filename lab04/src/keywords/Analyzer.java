package keywords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.RecursiveAction;

public class Analyzer extends RecursiveAction {
    private final File directory;
    private ArrayList<String> keywords;
    public ArrayList<Document> foundKeywords = new ArrayList<>();

    public Analyzer(File directory, ArrayList<String> keywords) {
        this.directory = directory;
        keywords.replaceAll(String::toLowerCase);
        this.keywords = keywords;
    }

    @Override
    protected void compute() {
        ArrayList<Analyzer> subTasks = new ArrayList<>();

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                Analyzer subTask = new Analyzer(file, keywords);
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
                    words.retainAll(keywords);
                    if(!words.isEmpty()){
                        Document document = new Document();
                        document.path = file.getPath();
                        document.keywords = words;
                        foundKeywords.add(document);
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
