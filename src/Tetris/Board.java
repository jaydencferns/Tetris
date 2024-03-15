package Tetris;

import Tetris.TetrisBlocks.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel  {

    public static final int BOARD_WIDTH = 401, BOARD_HEIGHT = 481;
    private int cellSize;
    private int numOfColumns;
    private int numOfRows;
    private Color[][] background;



    private Block block;

    private Block[] blocks;

    private Runtime runtime;
    private GameFrame gameFrame;

    private int score = 0;
    private int level = 1;
    private int totalLinesCleared = 0;




    public Board(int columns) {
        this.numOfColumns = columns;
        cellSize = BOARD_WIDTH / numOfColumns;
        numOfRows = BOARD_HEIGHT / cellSize;

//        this.setBounds(50,50,BOARD_WIDTH,BOARD_HEIGHT) ;
        setPreferredSize(new Dimension(numOfColumns* cellSize, numOfRows*cellSize));
        this.setBackground(Color.gray);

        background = new Color[numOfRows][numOfColumns];

        blocks = new Block[]{new IShape(),
                            new JShape(),
                            new LShape(),
                            new OShape(),
                            new SShape(),
                            new TShape(),
                            new ZShape()};


        runtime = new Runtime(this);
        runtime.run();

    }

    public void addGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
    public void clearLines() {
        int numOfLinesCleared = 0;
        for (int row = numOfRows - 1; row>=0; row--) {

            boolean islineFilled = true;

            for (int col = 0; col < numOfColumns; col++) {
                if (background[row][col] == null) {
                    islineFilled = false;
                    break;
                }
            }

            if (islineFilled) {
                totalLinesCleared++;

                if (totalLinesCleared >= 5) {
                    level++;
                    gameFrame.updateLevel(level);
                    totalLinesCleared = 0;
                    runtime.increaseGameSpeed();

                }

                numOfLinesCleared++;
                clearLine(row);
                shiftLineDown(row);
                clearLine(0);

                row++;
                repaint();
            }
        }

        computeScore(numOfLinesCleared);
    }



    private void computeScore(int numOfLinesCleared) {
        int scoring;

        switch (numOfLinesCleared) {
            case 1:
                scoring = 40 * level;
                break;
            case 2:
                scoring = 100 * level;
                break;
            case 3:
                scoring = 300 * level;
                break;
            case 4:
                scoring = 1200 * level;
                break;
            default:
                scoring = 0;

        }

        score += scoring;
        gameFrame.updateScore(score);
    }


    private void shiftLineDown(int row) {
        for (int r = row; r>0 ; r--) {

            for (int col = 0; col < numOfColumns; col++) {

                background[r][col] = background[r-1][col];
            }
            repaint();

        }
    }

    public void clearLine(int row) {
        for (int col = 0; col < numOfColumns; col++) {
            background[row][col] = null;
        }
    }


    public void spawnBlock() {
        Random r = new Random();

        block = blocks[r.nextInt(blocks.length)];

        block.spawn(numOfColumns);

    }

    public void moveBlockDown() {
        if (block == null) return;
        block.moveDown();
        repaint();
    }

    public void moveBlockDownFully() {
        if (block == null) return;

        while ( checkBottom()) {
            block.moveDown();
        }
        repaint();
    }

    public void moveBlockRight() {
        if (block == null) return;

        if (checkRightBoundary()) {
            block.moveRight();
        }
        repaint();
    }

    public void moveBlockLeft() {
        if (block == null) return;

        if (checkLeftBoundary()) {
            block.moveLeft();
        }
        repaint();
    }

    public void rotateBlock() {
        if (block == null) return;

        block.rotate();

        if (block.getLeftSide() < 0) block.setOffsetX(0);
        if (block.getRightSide() >= numOfColumns) block.setOffsetX(numOfColumns - block.getWidth());
        if (block.getBottomSide() >= numOfRows) block.setOffsetY(numOfRows - block.getHeight());
        if (overlapsBlocks()) {
            block.rotateBack();
        }

        repaint();
    }

    private boolean overlapsBlocks() {
        if ((block.getLeftSide() != 0 && !overlapsBlockLeft())
                || (block.getBottomSide() != numOfRows && !overlapsBlockBelow())
                || (block.getRightSide() != numOfColumns && !overlapsBlockRight())) {
            return true;
        }
        return false;
    }

    public boolean checkBottom() {
        if (block.getBottomSide() == numOfRows) {
           return false;
        }

        return overlapsBlockBelow();




    }

    private boolean overlapsBlockBelow() {
        int[][] shape = block.getShape();
        int height = block.getHeight();
        int width = block.getWidth();

        for (int col = 0; col < width; col++) {
            for (int row = height-1; row>=0;row--){
                if (shape[row][col] != 0) {
                    int x = block.getOffsetX() + col;
                    int y = block.getOffsetY() + row + 1;
                    if (y<0) break;
                    if (background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }

    public boolean checkLeftBoundary() {
        if (block.getLeftSide() == 0) {
            return false;
        }

        return overlapsBlockLeft();



    }

    private boolean overlapsBlockLeft() {
        int[][] shape = block.getShape();
        int height = block.getHeight();
        int width = block.getWidth();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width;col++){
                if (shape[row][col] != 0) {
                    int x = block.getOffsetX() + col - 1;
                    int y = block.getOffsetY() + row;
                    if (y<0) break;
                    if (background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }

    public boolean checkRightBoundary() {
        if (block.getRightSide() == numOfColumns) {
            return false;
        }

        return overlapsBlockRight();


    }

    private boolean overlapsBlockRight() {
        int[][] shape = block.getShape();
        int height = block.getHeight();
        int width = block.getWidth();

        for (int row = 0; row < height; row++) {
            for (int col = width - 1; col >= 0;col--){
                if (shape[row][col] != 0) {
                    int x = block.getOffsetX() + col + 1;
                    int y = block.getOffsetY() + row;
                    if (y<0) break;
                    if (background[y][x] != null) return false;
                    break;
                }
            }
        }
        return true;
    }


    private void drawBlock(Graphics g) {
        int x = block.getOffsetX();
        int y = block.getOffsetY();
        Color color = block.getColor();

        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (block.getShape()[row][col] == 1) {
                    g.setColor(color);
                    g.fillRect((x+ col) * cellSize, (y+row) * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    public boolean isOutOfBounds() {
        if  (block.getOffsetY() < 0) {
            block = null;
            return true;
        }
        else return false;
    }

    public void moveBlockToBackground(){
        int [][] shape = block.getShape();
        int height = block.getHeight();
        int width = block.getWidth();
        int x = block.getOffsetX();
        int y = block.getOffsetY();
        Color color = block.getColor();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width;col++) {
                if (shape[row][col] == 1){
                    background[row + y][col + x] = color;
                }
            }
        }
    }

    private void drawBackground(Graphics g){
        Color color;

        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfColumns; col++) {
                color = background[row][col];

                if (color != null) {
                    int x = col * cellSize;
                    int y = row * cellSize;

                    g.setColor(color);
                    g.fillRect(x,y,cellSize,cellSize);
                }
            }
        }
    }


    public int getNumOfRows() {
        return numOfRows;
    }

    public Block getBlock() {
        return block;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        g.fillRect(0,0,50,50);


        drawBackground(g);
        drawBlock(g);

        // to draw grids
        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfColumns; col++) {
                g.setColor(Color.darkGray);
                g.drawRect(col * cellSize, row* cellSize, cellSize, cellSize);
            }
        }

    }


}
