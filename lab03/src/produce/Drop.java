package produce;

public class Drop {
    private int message;
    private boolean empty = true;

    public int take() {
        synchronized (this){
            while (empty) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
            empty = true;
            notifyAll();
            return message;
        }
    }

    public void put(int message) {
        synchronized (this){
            while (!empty) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
            empty = false;
            this.message = message;
            notifyAll();
        }
    }
}