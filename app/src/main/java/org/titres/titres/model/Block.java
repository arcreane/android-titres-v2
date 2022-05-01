
package org.titres.titres.model;

import org.titres.framework.Actor;

public class Block extends Actor {
    private boolean falling;

    private int color;

    public Block() {

        super(0, 0, 1, 1);
        color = 0xffffffff;
    }

    public boolean collide() {
        for (Block block : TitrominosWorld.getInstance().getBlocks()) {
            if (collide(block)) {
                return true;
            }
        }
        if (x > TitrominosWorld.WORLD_WIDTH-1 || y > TitrominosWorld.WORLD_HEIGHT-1 ||
                x < 0 || y < 0)
            return true;
        return false;
    }

    public void moveDown() {
        moveBy(0, 1);
    }
    public void moveUp() {
        moveBy(0, -1);
    }
    public void moveLeft() {
        moveBy(-1, 0);
    }
    public void moveRight() {
        moveBy(1, 0);
    }

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
}
