package stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Methods {
    public static ArrayList<String> getWords(String path) throws IOException {
        ArrayList<String> wordList = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            String[] words = line.split("\\s+");

            wordList.addAll(Arrays.stream(words).toList());
        }
        return wordList;
    }

    public static ArrayList<Integer> countWordsSymbols(ArrayList<String> words){
        ArrayList<Integer> result = new ArrayList<>();
        for(String word: words){
            result.add(word.length());
        }
        return result;
    }
    /*
    private void setWordCount(){
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : wordLengths.entrySet()) {
            count += entry.getValue();
        }
        this.wordCount = count;
    }

    private void setWordLengthSum(){
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : wordLengths.entrySet()) {
            sum += entry.getKey() * entry.getValue();
        }
        this.wordLengthSum = sum;
    }
    public double getAverageWordLength() {
        double average = (double) wordLengthSum / wordCount;
        return Math.round(average * Math.pow(10, 3)) / Math.pow(10, 3);
    }

    public double getDispersion() {
        double average = getAverageWordLength();
        double sumOfSquaredDeviations = 0;
        for (Map.Entry<Integer, Integer> entry : wordLengths.entrySet()) {
            sumOfSquaredDeviations += Math.pow(entry.getKey() - average, 2) * entry.getValue();
        }
        return Math.round(sumOfSquaredDeviations / wordCount * Math.pow(10, 3)) / Math.pow(10, 3);
    }

    public double getStandardDeviation() {
        double dispersion = getDispersion();
        return Math.round(Math.sqrt(dispersion) * Math.pow(10, 3)) / Math.pow(10, 3);
    }
    public int getWordLengthSum() {
        return wordLengthSum;
    }
    public int getWordCount() {
        return wordCount;
    }
    */
}
