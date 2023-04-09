package ballsholes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {

    private BallCanvas canvas;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;

    private static int hitScore = 0;
    private static JLabel hitScoreLabel;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Balls&Holes");

        this.canvas = new BallCanvas();
        canvas.add(new Hole(380, 10)); //N
        canvas.add(new Hole(380, 270)); //S
        canvas.add(new Hole(10, 10)); //NW
        canvas.add(new Hole(10, 270)); //SW
        canvas.add(new Hole(760, 10)); //NE
        canvas.add(new Hole(760, 270)); //SE

        JPanel hitScorePanel =  new JPanel();
        hitScorePanel.setBackground(Color.lightGray.brighter());
        JLabel hitScoreLabel = new JLabel(String.valueOf(BounceFrame.hitScore));
        BounceFrame.hitScoreLabel = hitScoreLabel;
        hitScorePanel.add(hitScoreLabel);
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(hitScorePanel, BorderLayout.NORTH);
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray.brighter());

        JButton buttonStart = new JButton("Add 1");
        JButton buttonAddTen = new JButton("Add 10");
        JButton buttonAddHundred = new JButton("Add 100");
        JButton buttonAddThousand = new JButton("Add 1000");

        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Ball b = new Ball(canvas);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Created Ball = " + thread.getName());
            }
        });

        buttonAddTen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i<10; i++){
                    Ball b = new Ball(canvas);
                    canvas.add(b);

                    BallThread thread = new BallThread(b);
                    thread.start();
                    System.out.println("Created Ball = " + thread.getName());
                }
            }
        });

        buttonAddHundred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i<100; i++){
                    Ball b = new Ball(canvas);
                    canvas.add(b);

                    BallThread thread = new BallThread(b);
                    thread.start();
                    System.out.println("Created Ball = " + thread.getName());
                }
            }
        });

        buttonAddThousand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i<1000; i++){
                    Ball b = new Ball(canvas);
                    canvas.add(b);

                    BallThread thread = new BallThread(b);
                    thread.start();
                    System.out.println("Created Ball = " + thread.getName());
                }
            }
        });

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonAddTen);
        buttonPanel.add(buttonAddHundred);
        buttonPanel.add(buttonAddThousand);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
    public static synchronized void hitScoreInc() {
        hitScore++;
        hitScoreLabel.setText(String.valueOf(hitScore));
    }
}
