package bank;

import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    private ReentrantLock locker = new ReentrantLock();
    public Bank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }
    public void asyncTransfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();

    }
    public void syncLockTransfer(int from, int to, int amount) {
        locker.lock();
        try{
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        }finally {
            locker.unlock();
        }
    }
    public void syncBlockTransfer(int from, int to, int amount) {
        synchronized (this){
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        }
    }
    public synchronized void syncTransfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();

    }
    public synchronized void waitTransfer(int from, int to, int amount) {
        while (accounts[from] < amount) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        notifyAll();
        if (ntransacts % NTEST == 0) test();
    }
    public void test(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);

    }
    public int size(){
        return accounts.length;
    }
}
