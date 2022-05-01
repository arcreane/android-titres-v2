
package org.androidforfun.droids.model;


public class ShapeS extends Shape {
    public ShapeS() {
        super(3, 2);
        rotation_block = blocks[1];
        rotation_cycle = 2;
    }

    public Block[] getBlocks() {
        blocks[0].setX(x + 2);
        blocks[0].setY(y);
        blocks[1].setX(x + 1);
        blocks[1].setY(y);
        blocks[2].setX(x + 1);
        blocks[2].setY(y + 1);
        blocks[3].setX(x);
        blocks[3].setY(y + 1);

        apply_rotation();

        for (Block block : blocks) {
            block.setColor(0xff00ff00);
        }
        return blocks;
    }
}
