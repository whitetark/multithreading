public class Analyst extends Thread {
    private Manager manager;
    private int queueSize;
    private int tasksNum;

    Analyst(Manager manager) {
        this.manager = manager;
        this.queueSize = 0;
        this.tasksNum = 0;
    }

    @Override
    public void run() {
        while(!this.manager.isBusy) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}

            this.queueSize += this.manager.getQueueSize();
            this.tasksNum += 1;
        }
    }

    public double getAvgQueueValue() {
        return (double) this.queueSize / this.tasksNum;
    }
    public double skippedChance(){
        return (double) manager.skipped / (manager.skipped + manager.completed);
    }
}