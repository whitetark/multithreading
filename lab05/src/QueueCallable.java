import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class QueueCallable implements Callable<Analyst> {
    private int numOfConsumers;

    QueueCallable() {
        this.numOfConsumers = 5;
    }
    QueueCallable(int queueCapacity) {
        this.numOfConsumers = queueCapacity;
    }

    public Analyst call() {

        Manager manager = new Manager();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < numOfConsumers; i++) {
            Consumer thread = new Consumer(manager);
            executor.execute(thread);
        }

        Analyst analyst = new Analyst(manager);
        executor.execute(analyst);

        Producer producer = new Producer(manager);
        executor.execute(producer);

        executor.shutdown();

        System.out.println("Queue in process...");

        try {
            boolean ok = executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Something Happened");
            throw new RuntimeException();
        }

        return analyst;
    }
}