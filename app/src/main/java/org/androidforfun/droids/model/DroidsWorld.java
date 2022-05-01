
package org.androidforfun.droids.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DroidsWorld {
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 20;

    public enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    GameState state = GameState.Ready;

    private List<Block> blocks = new ArrayList<>();
    private int level = 0;
    private Shape fallingShape;
    private Shape nextShape;
    private int goal;
    private int score = 0;

    private static DroidsWorld instance = null;
    
    private DroidsWorld() {
        spawnNextShape();
        makeNextShapeFalling();
        goal=5;
    }

    public static DroidsWorld getInstance() {
        if (instance == null) {
            instance = new DroidsWorld();
        }
        return instance;
    }

    public int getGoal() {
        return goal;
    }


    public void spawnNextShape() {
        Random r = new Random();
        int index = r.nextInt(7);

        Shape shapes[] = { new ShapeI(), new ShapeL(), new ShapeJ(), new ShapeCube(), new ShapeZ(), new ShapeT(), new ShapeS()};
        nextShape = shapes[index];
    }


    public void makeNextShapeFalling() {
        if (fallingShape != null) {

            for (Block block : fallingShape.getBlocks())
                blocks.add(block);
        }

        fallingShape = nextShape;
        spawnNextShape();
    }

    public void update(float deltaTime) {
        if (state == GameState.GameOver)
            return;

        if (state == GameState.Running ) {
            if (fallingShape.collide()) {
                state = GameState.GameOver;
            } else {
                fallingShape.update();
                if (!fallingShape.isFalling()) {
                    Shape layingShape = fallingShape;
                    score+=layingShape.softDropScore;
                    makeNextShapeFalling();
                    deleteLinesOf(layingShape);
                }
            }
        }
    }


    void deleteLinesOf(Shape shape) {
        List <Integer> deletedLines = new ArrayList<>();

        for (Block shapeBlock : shape.getBlocks()) {
            if (lineComplete(shapeBlock.getY())) {
                deletedLines.add(shapeBlock.getY());
                for (Iterator<Block> itr=blocks.iterator(); itr.hasNext();) {
                    Block block = itr.next();
                    if (block.getY() == shapeBlock.getY()) itr.remove();
                }
            }
        }

        goal-=deletedLines.size();
        switch (deletedLines.size()) {
            case 1: score+=40*(level+1); break;
            case 2: score+=100*(level+1); break;
            case 3: score+=300*(level+1); break;
            case 4: score+=1200*(level+1); break;
        }
        if (goal<=0) {
            ++level;
            goal+=5*level + 5;
        }


        for (Block block : blocks) {
            int count = 0;
            for (int y : deletedLines) {
                if (y > block.getY()) ++count;
            }
            block.setY(block.getY()+count) ;
        }
    }

    private boolean lineComplete(int y) {

        int count = 0;
        for (Block block : blocks) {
            if (block.getY() == y) ++count;
        }
        if (count==WORLD_WIDTH) {
            return true;
        }
        return false;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void clear() {
        blocks.clear();
        fallingShape=null;
        level = 0;
        score = 0;
        spawnNextShape();
        makeNextShapeFalling();
        state = GameState.Ready;
        goal=5;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Shape getNextShape() {
        return nextShape;
    }

    public int getLevel() {
        return level;
    }

    public Shape getFallingShape() {
        return fallingShape;
    }
}
