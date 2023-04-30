import java.util.ArrayDeque;
import java.util.Queue;

public class Manager {
    private int queueSize = 100;
    public int skipped;
    public int completed;
    private Queue<Integer> queue;
    public boolean isBusy;

    Manager() {
        this.completed = 0;
        this.skipped = 0;
        this.isBusy = false;
        this.queue = new ArrayDeque<>();
    }

    public synchronized void addItem(int item) {
        if(this.queue.size() >= this.queueSize) {
            this.skipped += 1;
            return;
        }

        this.queue.add(item);
        notifyAll();
    }

    public synchronized int getItem() {
        while(this.queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        return this.queue.poll();
    }

    public synchronized void incCompleted() {
        this.completed += 1;
    }

    public synchronized int getQueueSize() {
        return this.queue.size();
    }
}
