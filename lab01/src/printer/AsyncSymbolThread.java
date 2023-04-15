package printer;

public class AsyncSymbolThread extends Thread{
    public static final int LINES_NUMBER = 100;
    public static final int LINE_LENGTH = 50;
    private Symbol symbol;

    public AsyncSymbolThread(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int i = 0; i < LINES_NUMBER; i++) {
            for (int j = 0; j < LINE_LENGTH; j++) {
                symbol.print();
            }
            System.out.println();
        }
    }
}
