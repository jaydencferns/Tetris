package Tetris;

import java.awt.*;
import java.util.Random;

public class Block {

    private int[][] shape;
    private Color color;
    private int offsetX, offsetY;
    private int[][][] shapes;
    private int currentRotation;

    public Block(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;

        initShapes();
    }

    public void initShapes() {
        shapes = new int[4][][];

        for (int i = 0; i < 4; i++) {
            int row = shape[0].length;
            int col = shape.length;

            shapes[i] = new int[row][col];

            for (int y = 0; y < row; y++) {
                for (int x = 0; x < col; x++) {
                    shapes[i][y][x] = shape[col - x - 1][y];
                }
            }

            shape = shapes[i];
        }
    }

    public void spawn(int gridWidth) {
        Random random = new Random();

        currentRotation = random.nextInt(shapes.length);
        shape = shapes[currentRotation];


        offsetX = random.nextInt(gridWidth - getWidth());
        offsetY = 0 - getHeight();
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return shape[0].length;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void moveDown() {
        offsetY++;
    }

    public void moveRight() {
        offsetX++;
    }

    public void moveLeft() {
        offsetX--;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public void rotate() {
        currentRotation++;
        if (currentRotation > 3) currentRotation = 0;
        shape = shapes[currentRotation];

    }

    public void rotateBack() {
        currentRotation--;
        if (currentRotation < 0) currentRotation = 3;
        shape = shapes[currentRotation];
    }

    public int getBottomSide() {
        return offsetY + getHeight();
    }

    public int getRightSide() {return offsetX + getWidth();}
    public int getLeftSide() {return offsetX;}
}
