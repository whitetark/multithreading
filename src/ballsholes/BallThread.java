package ballsholes;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball){
        b = ball;
        switch(ball.getPriority()){
            case -1:
                setPriority(Thread.MIN_PRIORITY);
                break;
            case 1:
                setPriority(Thread.MAX_PRIORITY);
                break;
            default:
                break;
        }
    }
    @Override
    public void run(){
        try {
            while (true){
                if (b.intersectsWithHoles()) {
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