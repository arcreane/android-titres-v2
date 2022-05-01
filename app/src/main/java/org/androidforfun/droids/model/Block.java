
package org.androidforfun.droids.model;

import org.androidforfun.framework.Actor;

public class Block extends Actor {
    private boolean falling;

    private int color;

    public Block() {

        super(0, 0, 1, 1);
        color = 0xffffffff;
    }

    public boolean collide() {
        for (Block block : DroidsWorld.getInstance().getBlocks()) {
            if (collide(block)) {
                return true;
            }
        }
        if (x > DroidsWorld.WORLD_WIDTH-1 || y > DroidsWorld.WORLD_HEIGHT-1 ||
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
