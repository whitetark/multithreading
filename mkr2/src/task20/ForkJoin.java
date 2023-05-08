package task20;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

//Коментар:
// пул десяти потоків не створений
//
//if(numbers.size() > 2){ // Ви реалізували звичайний каскадний, а не модифікований каскадний

public class ForkJoin extends RecursiveTask<Integer> {
    public List<Integer> numbers;
    public ForkJoin(List<Integer> numbers){
        this.numbers = numbers;
    }
    @Override
    protected Integer compute() {
        ArrayList<ForkJoin> subTasks = new ArrayList<>();
        int result = 0;
        if(numbers.size() > 2){
            ForkJoin subTask1 = new ForkJoin(numbers.subList(0, numbers.size()/2));
            ForkJoin subTask2 = new ForkJoin(numbers.subList(numbers.size()/2, numbers.size()));

            subTasks.add(subTask1);
            subTasks.add(subTask2);

            invokeAll(subTasks);
            for(RecursiveTask<Integer> subTask: subTasks){
                result += subTask.join();
            }
        } else{
            result = numbers.stream().mapToInt(Integer::valueOf).sum();
        }
        return result;
    }
}
