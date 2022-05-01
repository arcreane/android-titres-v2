
package org.titres.titres.model;

import android.os.SystemClock;
import org.titres.framework.Actor;

public abstract class Shape extends Actor {
    protected Block blocks[];
    protected boolean falling;

    protected boolean accelerateFalling;
    protected int softDropScore;

    protected Block rotation_block;
    protected int rotation_cycle, rotation;

    protected long last_fall_update;
    protected long last_move_update;

    protected Shape(int width, int height) {
        super(0, 0, width, height);

        last_fall_update = SystemClock.uptimeMillis();
        last_move_update = SystemClock.uptimeMillis();

        blocks = new Block[4];
        blocks[0] = new Block();
        blocks[1] = new Block();
        blocks[2] = new Block();
        blocks[3] = new Block();


        falling = true;


        rotation_block = blocks[1];

        rotation_cycle = 1;
        rotation = 0;
    }

    public void apply_rotation() {
        int old_x, old_y;
        if (rotation_block != null) {
            for (int i=1; i<=(rotation % rotation_cycle); ++i) {
                for (Block block : blocks) {
                    old_x = block.getX();
                    old_y = block.getY();
                    block.setX(rotation_block.getX() + (rotation_block.getY() - old_y));
                    block.setY(rotation_block.getY() - (rotation_block.getX() - old_x));
                }
            }
        }
    }

    public void rotate() {
        rotation+=1;
    }
    public void undoRotate() {
        rotation-=1;
    }


    public boolean needsFallUpdate() {

        long updateInterval = 500 - TitrominosWorld.getInstance().getLevel()*50;
        if (accelerateFalling)
            updateInterval = 100;
            //updateInterval = 50;

        if (SystemClock.uptimeMillis() - last_fall_update > updateInterval ) {
            last_fall_update = SystemClock.uptimeMillis();
            return true;
        }
        return false;
    }


    public boolean needsMoveUpdate() {
        if (SystemClock.uptimeMillis() - last_move_update > 100) {
            last_move_update = SystemClock.uptimeMillis();
            return true;
        }
        return false;
    }

    public void update() {

        if (falling) {

            if (needsFallUpdate()) {
                moveDown();
            }


            if (collide()) {
                moveUp();
                falling = false;
            }
        }
    }


    public boolean collide() {

        for (Block block : getBlocks()) {
            if (block.collide()) {
                return true;
            }
        }
        return false;
    }

    public void moveDown() {
        for (Block block : blocks) {
            block.moveDown();
        }
        moveBy(0, 1);
        if (accelerateFalling) {
            softDropScore++;
        }
    }

    public void moveUp() {
        for (Block block : blocks) {
            block.moveUp();
        }
        moveBy(0, -1);
        if (accelerateFalling) {
            softDropScore--;
        }
    }

    public void moveLeft() {
        for (Block block : blocks) {
            block.moveLeft();
        }
        moveBy(-1, 0);
    }

    public void moveRight() {
        for (Block block : blocks) {
            block.moveRight();
        }
        moveBy(1, 0);
        //x += 1;
    }

    public abstract Block[] getBlocks();

    public boolean isFalling() {
        return falling;
    }

    public int getSoftDropScore() {
        return softDropScore;
    }

    public void accelerateFalling() {
        this.accelerateFalling = true;
    }
}
