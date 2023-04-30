import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args){
        task1();
    }

    public static void task1() {
        QueueCallable task = new QueueCallable();
        Analyst analyst = task.call();

        System.out.println("Skipped Messages %: " + analyst.skippedChance()*100);
        System.out.println("Average Queue Value: " + analyst.getAvgQueueValue());
    }
}