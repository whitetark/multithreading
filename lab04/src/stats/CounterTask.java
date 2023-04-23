package stats;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class CounterTask extends RecursiveTask<ArrayList<Word>> {
    public List<String> words;
    private int threshold = 1000;
    public CounterTask(List<String> words) {
        this.words = words;
    }

    @Override
    protected ArrayList<Word> compute() {
        ArrayList<CounterTask> subTasks = new ArrayList<>();
        ArrayList<Word> result = new ArrayList<>();

        if (words.size() >= threshold){
            CounterTask subTask1 = new CounterTask(words.subList(0, words.size()/4));
            CounterTask subTask2 = new CounterTask(words.subList(words.size()/4, words.size()/2));
            CounterTask subTask3 = new CounterTask(words.subList(words.size()/2, (words.size()/4)*3));
            CounterTask subTask4 = new CounterTask(words.subList(((words.size()/4)*3), words.size()));

            subTasks.add(subTask1);
            subTasks.add(subTask2);
            subTasks.add(subTask3);
            subTasks.add(subTask4);

            invokeAll(subTasks);
            for(RecursiveTask<ArrayList<Word>> subTask: subTasks){
                result.addAll(subTask.join());
            }
        } else{
            for(String word: words){
                result.add(new Word(word));
            }
        }
        return result;
    }

    private ArrayList<CounterTask> createSubTasks(int numOfSubtask){

        ArrayList<CounterTask> result = new ArrayList<>();
        for(int i = 0; i<numOfSubtask; i++){
            CounterTask subTask = new CounterTask(words.subList((words.size()/numOfSubtask)*i, (words.size()/numOfSubtask)*(i+1)));
            result.add(subTask);
        }
        return result;
    }

}