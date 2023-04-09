package ballsholes;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{
            for(int i=1; i<10000; i++){
                if(b.intersectsWithHoles()){
                    BounceFrame.hitScoreInc();
                    System.out.println("Ball Hit Hole = " + Thread.currentThread().getName());
                    this.interrupt();
                }
                b.move();
                System.out.println("Moving Ball = " + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch(InterruptedException ex){

        }
    }
}