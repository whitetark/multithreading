package stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public static ArrayList<Word> countWordsSymbols(ArrayList<String> words){
        ArrayList<Word> result = new ArrayList<>();
        for(String word: words){
            result.add(new Word(word));
        }
        return result;
    }

    public static double getAverageWordLength(ArrayList<Word> words) {
        double average = (double) getWordLengthSum(words) / words.size();
        return Math.round(average * Math.pow(10, 3)) / Math.pow(10, 3);
    }

    public static double getDispersion(ArrayList<Word> words) {
        double average = getAverageWordLength(words);
        double sumOfSquaredDeviations = 0;
        for(Word word: words){
            sumOfSquaredDeviations += Math.pow(word.length - average, 2);
        }

        return Math.round(sumOfSquaredDeviations / words.size() * Math.pow(10, 3)) / Math.pow(10, 3);
    }

    public static double getStandardDeviation(ArrayList<Word> words) {
        double dispersion = getDispersion(words);
        return Math.round(Math.sqrt(dispersion) * Math.pow(10, 3)) / Math.pow(10, 3);
    }
    public static int getWordLengthSum(ArrayList<Word> words) {
        int result = 0;
        for(Word word: words){
            result += word.length;
        }
        return result;
    }
}
