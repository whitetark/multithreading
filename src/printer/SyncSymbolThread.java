package printer;

public class SyncSymbolThread extends Thread {
    public static final int LINES_NUMBER = 1;
    public static final int LINE_LENGTH = 50;
    private Symbol symbol;
    private Syncer syncer;
    private boolean permission;

    public SyncSymbolThread(Syncer syncer, boolean permission, Symbol symbol) {
        this.syncer = syncer;
        this.permission = permission;
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int i = 0; i < LINES_NUMBER; i++) {
            for (int j = 0; j < LINE_LENGTH; j++) {
                syncer.then(permission, () -> symbol.print());
            }
        }
    }
}