import java.util.Random;

public class Consumer extends Thread {
    private Manager manager;

    Consumer(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        Random random = new Random();

        while(!manager.isBusy) {
            int newItem = manager.getItem();
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException ignored) {}

            manager.incCompleted();
        }
    }

}
