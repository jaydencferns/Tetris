package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame{
    private Board board;


    public static final int WIDTH = 600, HEIGHT = 630;
    private JPanel panel1;
    private JLabel scoreLabel;
    private JLabel levelLabel;



    public GameFrame (String name) {


        // makes sure that JPanel does not cover whole of JFrame
            this.setLayout(new BorderLayout());
            //this.setSize(WIDTH, HEIGHT);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);


            scoreLabel = new JLabel("Score: 0");
            levelLabel = new JLabel("Level: 1");


            board = new Board(10);
            board.addGameFrame(this);

            this.add(board, BorderLayout.WEST);

            panel1 = new JPanel();
            panel1.setLayout(new GridLayout(12,1));
            panel1.add(scoreLabel);
            panel1.add(levelLabel);
            panel1.setPreferredSize(new Dimension(75,75));
            this.add(panel1, BorderLayout.EAST);

            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);

            initControls();
        }

    private void initControls() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_DOWN:
                        board.moveBlockDownFully();
                        break;
                    case KeyEvent.VK_RIGHT:
                        board.moveBlockRight();
                        break;
                    case KeyEvent.VK_LEFT:
                        board.moveBlockLeft();
                        break;
                    case KeyEvent.VK_UP:
                        board.rotateBlock();
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateLevel(int level) {
        levelLabel.setText("Level: " + level);
    }



}
