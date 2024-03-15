package Tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Runtime {
    private Timer timer;
    private Board board;
    private int delay = 900;
    private static int delayReduction = 50;


    public Runtime(Board board) {
        this.board = board;

    }

    public void run() {
        board.spawnBlock();
        timer = new Timer(delay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                if (board.checkBottom() ) {
                    board.moveBlockDown();
                }

                else {
                    if (board.isOutOfBounds()) {
                        System.out.println("GAME OVER.");
                        timer.stop();
                    }
                    else {
                        board.moveBlockToBackground();
                        board.clearLines();
                        board.spawnBlock();
                        board.repaint();
                    }
                }
            }

        });
        timer.start();
    }

    public void increaseGameSpeed() {
        delay -= delayReduction;
        timer.setDelay(delay);
    }
}
