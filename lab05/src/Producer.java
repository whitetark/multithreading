import java.util.Random;

public class Producer extends Thread {
    private Manager manager;

    Producer(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        long currentTime = 0;

        while (currentTime < 10_000) {
            this.manager.addItem(random.nextInt(100));

            try {
                Thread.sleep(random.nextInt(15));
            } catch (InterruptedException ignored) {}

            currentTime = System.currentTimeMillis() - startTime;
        }

        this.manager.isBusy = true;
    }
}