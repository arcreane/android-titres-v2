/*
 *  Copyright (C) 2016 Salvatore D'Angelo
 *  This file is part of Droids project.
 *
 *  Droids is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Droids is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License.
 */
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
