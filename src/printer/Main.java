package printer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            int choose = System.in.read();
            Symbol minusSymbol = new Symbol('-');
            Symbol dashSymbol = new Symbol('|');

            if(choose == (int)'0'){
                AsyncSymbolThread minusSymbolThread = new AsyncSymbolThread(minusSymbol);
                AsyncSymbolThread dashSymbolThread = new AsyncSymbolThread(dashSymbol);

                minusSymbolThread.start();
                dashSymbolThread.start();
            } else{
                Syncer syncer = new Syncer(false);

                SyncSymbolThread minusSymbolThread = new SyncSymbolThread(syncer, false, minusSymbol);
                SyncSymbolThread dashSymbolThread = new SyncSymbolThread(syncer, true, dashSymbol);

                minusSymbolThread.start();
                dashSymbolThread.start();
            }
        } catch(IOException e) {
        }
    }
}
