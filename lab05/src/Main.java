import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException{
        //task1();
        //task2();
        task3();
    }
    public static void task1() {
        QueueCallable task = new QueueCallable();
        Analyst analyst = task.call();

        System.out.println("Skipped Messages %: " + analyst.skippedChance()*100);
        System.out.println("Average Queue Value: " + analyst.getAvgQueueValue());
    }
    public static void task2() throws InterruptedException, ExecutionException {
        int poolsCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Callable<Analyst>> tasks = new ArrayList<>();

        for (int i = 0; i < poolsCount; i++) {
            QueueCallable task = new QueueCallable();
            tasks.add(task);
        }

        List<Future<Analyst>> result = executor.invokeAll(tasks);
        executor.shutdown();

        double totalMessages = 0;
        double totalChance = 0;
        for(Future<Analyst> taskResult : result) {
            Analyst analyst = taskResult.get();
            totalMessages += analyst.getAvgQueueValue();
            totalChance += analyst.skippedChance();
        }

        System.out.println("Skipped Messages % After " + poolsCount + " Parallel Runs: " + (totalChance / result.size())*100);
        System.out.println("Average Queue Value After " + poolsCount + " Parallel Runs: " + totalMessages / result.size());
    }
    public static void task3() {
        QueueCallable task = new QueueCallable(true);
        Analyst analyst = task.call();

        System.out.println("Skipped Messages %: " + analyst.skippedChance()*100);
        System.out.println("Average Queue Value: " + analyst.getAvgQueueValue());
    }
}