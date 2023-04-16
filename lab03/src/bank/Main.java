package bank;

public class Main {
    public static final int NUM_OF_ACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;
    public static void main(String[] args) {
        Bank b = new Bank(NUM_OF_ACCOUNTS, INITIAL_BALANCE);
        int i;
        for (i = 0; i < NUM_OF_ACCOUNTS; i++){
            TransferThread t = new TransferThread(b, i, INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start () ;
        }
    }
}
