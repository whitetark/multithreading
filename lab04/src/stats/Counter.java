package stats;

import java.util.*;
import java.util.concurrent.RecursiveTask;

import static stats.Methods.countWordsSymbols;

public class Counter extends RecursiveTask<List<Integer>> {
    public List<String> words;
    private int threshold = 5000;
    public Counter(List<String> words) {
        this.words = words;
    }

    @Override
    protected List<Integer> compute() {
        ArrayList<Integer> result = new ArrayList<>();
        if (words.size() > threshold){
            Counter subTask1 = new Counter(words.subList(0, words.size()/2));
            Counter subTask2 = new Counter(words.subList(words.size()/2, words.size()));

            result.addAll(subTask1.invoke());
            result.addAll(subTask2.invoke());

            subTask1.join();
            subTask2.join();
        } else{
            for(String word: words){
                result.add(word.length());
            }
        }
        return result;
    }
}