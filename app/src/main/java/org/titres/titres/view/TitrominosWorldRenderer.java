package org.titres.titres.view;

import org.titres.titres.model.Block;
import org.titres.titres.model.TitrominosWorld;
import org.titres.titres.model.Cube;
import org.titres.titres.model.I;
import org.titres.titres.model.J;
import org.titres.titres.model.L;
import org.titres.titres.model.S;
import org.titres.titres.model.T;
import org.titres.titres.model.Z;
import org.titres.framework.Gdx;


public class TitrominosWorldRenderer {
    public static int BLOCK_WIDTH=20;
    public static int BLOCK_HEIGHT=20;




    //Dessin des titrominos
    public void draw() {
        GameScreen gameScreen = (GameScreen) Gdx.game.getCurrentScreen();


        for (Block block : TitrominosWorld.getInstance().getBlocks()) {
            int x = gameScreen.getWorkingRegion().getX() + block.getX()*BLOCK_WIDTH;
            int y = gameScreen.getWorkingRegion().getY() + block.getY()*BLOCK_HEIGHT;
            Gdx.graphics.drawPixmap(Assets.getBlockByColor(block.getColor()), x, y);
        }


        for (Block block : TitrominosWorld.getInstance().getFallingShape().getBlocks()) {
            int x = gameScreen.getWorkingRegion().getX() + block.getX()*BLOCK_WIDTH;
            int y = gameScreen.getWorkingRegion().getY() + block.getY()*BLOCK_HEIGHT;
            Gdx.graphics.drawPixmap(Assets.getBlockByColor(block.getColor()), x, y);
        }

        for (Block block : TitrominosWorld.getInstance().getNextShape().getBlocks()) {
            int x = block.getX()*16;
            int y = block.getY()*16;

            if (TitrominosWorld.getInstance().getNextShape() instanceof Cube ||
                    TitrominosWorld.getInstance().getNextShape() instanceof J) {
                x+=15;
            } else if (TitrominosWorld.getInstance().getNextShape() instanceof I) {
                x+=25;
            } else if (TitrominosWorld.getInstance().getNextShape() instanceof L) {
                x+=20;
            } else if (TitrominosWorld.getInstance().getNextShape() instanceof S ||
                    TitrominosWorld.getInstance().getNextShape() instanceof T ||
                    TitrominosWorld.getInstance().getNextShape() instanceof Z) {
                x+=5;
            }

            Gdx.graphics.drawPixmap(Assets.getSmallBlockByColor(block.getColor()), gameScreen.getRightRegion().getX() + x, gameScreen.getRightRegion().getY() + 65 + y);
        }
    }
}
