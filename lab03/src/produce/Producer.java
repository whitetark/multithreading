package produce;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        int importantInfo[] = fillArray(100);
        Random random = new Random();

        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) {}
        }
        drop.put(-1);
    }

    private int[] fillArray(int length){
        int[] result = new int[length];
        for(int i = 0; i < result.length; i++){
            result[i] = i;
        }
        return result;
    }
}