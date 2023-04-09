package printer;

public class Syncer {
    private Symbol starter;
    private Symbol[] symbols;
    private int runs = 0;

    public Syncer(Symbol starter, Symbol[] symbols) {
        this.starter = starter;
        this.symbols = symbols;
    }

    public synchronized void then(Symbol symbol, Runnable runnable) {
        while (this.starter != symbol) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int index = findIndex(symbols, symbol);
        try{
            this.starter = symbols[index + 1];
        } catch(Exception e){
            this.starter = symbols[0];
        }
        runnable.run();
        runs++;

        if (runs % SyncSymbolThread.LINE_LENGTH == 0) {
            System.out.println();
        }

        notifyAll();
    }

    public int findIndex(Symbol[] arr, Symbol t)
    {
        if (arr == null) {
            return -1;
        }

        int len = arr.length;
        int i = 0;

        while (i < len) {

            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }
}
