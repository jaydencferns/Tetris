package Tetris;


import javax.swing.*;

public class TetrisGame {



    private GameFrame frame;



    public TetrisGame() {
        frame = new GameFrame("Tetris Game");
        // makes sure that JPanel does not cover whole of JFrame




    }

    public static void main(String[] args) {
        new TetrisGame();
    }
}
