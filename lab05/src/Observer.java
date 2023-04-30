public class Observer extends Thread {
    private Manager manager;
    Observer(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        while(!manager.isBusy) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}

            System.out.println("Queue Size: " + this.manager.getQueueSize() + ", Skipped %: " + ((double) manager.skipped / (manager.skipped + manager.completed))*100);
        }
    }
}
