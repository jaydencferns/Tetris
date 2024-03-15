package Tetris.TetrisBlocks;

import Tetris.Block;

import java.awt.*;

public class IShape extends Block {

    public IShape() {
        super(new int[][] {{1,1,1,1}}, Color.CYAN);
    }


    @Override
    public void rotate() {
        super.rotate();

        if (this.getWidth() == 1) {
            this.setOffsetX(getOffsetX() + 1);
            this.setOffsetY(getOffsetY() - 1);
        } else {
            this.setOffsetX(getOffsetX() - 1);
            this.setOffsetY(getOffsetY() + 1);
        }


    }
}
