package task21;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoin extends RecursiveTask<ArrayList<Double>> {
    public static int threshold = 1000;
    List<String> words = new ArrayList<>();

    public ForkJoin(List<String> words){
        this.words = words;
    }

    @Override
    protected ArrayList<Double> compute() {
        ArrayList<ForkJoin> subTasks = new ArrayList<>();
        ArrayList<Double> result = new ArrayList();
        double count = 0;
        double symbols = 0;

        if (words.size() >= threshold){
            ForkJoin subTask1 = new ForkJoin(words.subList(0, words.size()/2));
            ForkJoin subTask2 = new ForkJoin(words.subList(words.size()/2, words.size()));

            subTasks.add(subTask1);
            subTasks.add(subTask2);

            invokeAll(subTasks);
            for(RecursiveTask<ArrayList<Double>> subTask: subTasks){
                ArrayList<Double> subResult = subTask.join();
                count += subResult.get(0);
                symbols += subResult.get(1);
            }
        } else{
            symbols += getWordLengthSum(words);
            count += words.size();
        }
        result.add(count);
        result.add(symbols);

        return result;
    }
    public static int getWordLengthSum(List<String> words) {
        int result = 0;
        for(String word: words){
            result += word.length();
        }
        return result;
    }
}
