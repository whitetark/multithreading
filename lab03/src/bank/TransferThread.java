package bank;

import bank.Bank;

public class TransferThread extends Thread {
    private Bank bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 1000;
    public TransferThread(Bank b, int from, int max){
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }
    @Override
    public void run(){
        while (true) {
            for (int i = 0; i < REPS; i++) {
                int toAccount = (int) (bank.size() * Math.random());
                int amount = (int) (maxAmount * Math.random()/REPS);
                //bank.asyncTransfer(fromAccount, toAccount, amount);
                //bank.syncTransfer(fromAccount, toAccount, amount);
                //bank.syncLockTransfer(fromAccount, toAccount, amount);
                //bank.syncBlockTransfer(fromAccount, toAccount, amount);
                bank.waitTransfer(fromAccount, toAccount, amount);
            }
        }
    }
}