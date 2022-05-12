
package org.titres.titres.view;

import android.util.Log;

import org.titres.titres.model.TitrominosWorld;
import org.titres.titres.model.Settings;
import org.titres.framework.Gdx;
import org.titres.framework.Graphics;
import org.titres.framework.Input.TouchEvent;
import org.titres.framework.Rectangle;
import org.titres.framework.Screen;
import org.titres.framework.TextStyle;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GameScreen implements Screen {
    private static final String LOG_TAG = "Droids.GameScreen";

    private Map<TitrominosWorld.GameState, GameState> states = new EnumMap<>(TitrominosWorld.GameState.class);
    private Rectangle leftRegion;
    private Rectangle rightRegion;
    private Rectangle workingRegion;
    private Rectangle commandRegion;

    private Rectangle gameoverScreenBounds;
    private Rectangle gameScreenBounds;
    private Rectangle pauseButtonBounds;
    private Rectangle leftButtonBounds;
    private Rectangle rightButtonBounds;
    private Rectangle rotateButtonBounds;
    private Rectangle downButtonBounds;
    private Rectangle xButtonBounds;
    private Rectangle pauseMenuBounds;
    private Rectangle readyMenuBounds;
    private Rectangle homeMenuBounds;

    private TitrominosWorldRenderer renderer;

    public GameScreen() {
        Log.i(LOG_TAG, "constructor -- begin");

        states.put(TitrominosWorld.GameState.Paused, new GamePaused());
        states.put(TitrominosWorld.GameState.Ready, new GameReady());
        states.put(TitrominosWorld.GameState.Running, new GameRunning());
        states.put(TitrominosWorld.GameState.GameOver, new GameOver());

        leftRegion = new Rectangle(0, 0, 60, 400);
        workingRegion = new Rectangle(60, 20, 200, 400);
        rightRegion = new Rectangle(260, 0, 60, 400);
        commandRegion = new Rectangle(0, 400, 320, 80);

        gameoverScreenBounds=new Rectangle(0, 0, 320, 480);
        gameScreenBounds=new Rectangle(0, 0, 320, 480);
        pauseButtonBounds=new Rectangle(5, 20, 50, 50);
        leftButtonBounds=new Rectangle(30, 425, 50, 50);
        rightButtonBounds=new Rectangle(240, 425, 50, 50);
        rotateButtonBounds=new Rectangle(100, 425, 50, 50);
        downButtonBounds=new Rectangle(170, 425, 50, 50);
        pauseMenuBounds=new Rectangle(100, 100, 160, 48);
        readyMenuBounds=new Rectangle(65, 100, 188, 70);
        homeMenuBounds=new Rectangle(80, 148, 160, 48);
        xButtonBounds=new Rectangle(128, 200, 50, 50);

        renderer = new TitrominosWorldRenderer();
    }

    public void update(float deltaTime) {
        Log.i(LOG_TAG, "update -- begin");
        List<TouchEvent> touchEvents = Gdx.input.getTouchEvents();
        Gdx.input.getKeyEvents();
        states.get(TitrominosWorld.getInstance().getState()).update(touchEvents, deltaTime);
    }

    public void draw(float deltaTime) {
        Log.i(LOG_TAG, "draw -- begin");
        Gdx.graphics.drawPixmap(Assets.gamescreen, gameScreenBounds.getX(), gameScreenBounds.getY());
        renderer.draw();
        Gdx.graphics.drawPixmap(Assets.buttons, leftButtonBounds.getX(), leftButtonBounds.getY(), 50, 50,
                leftButtonBounds.getWidth()+1, leftButtonBounds.getHeight()+1);  // left button
        Gdx.graphics.drawPixmap(Assets.buttons, rightButtonBounds.getX(), rightButtonBounds.getY(), 0, 50,
                rightButtonBounds.getWidth()+1, rightButtonBounds.getHeight()+1); // right button
        Gdx.graphics.drawPixmap(Assets.buttons, rotateButtonBounds.getX(), rotateButtonBounds.getY(), 50, 150,
                rotateButtonBounds.getWidth()+1, rotateButtonBounds.getHeight()+1); // rotate button
        Gdx.graphics.drawPixmap(Assets.buttons, downButtonBounds.getX(), downButtonBounds.getY(), 0, 150,
                downButtonBounds.getWidth()+1, downButtonBounds.getHeight()+1); // down button

        TextStyle style = new TextStyle();
        style.setColor(0xffffffff);
        style.setTextSize(10);
        style.setAlign(TextStyle.Align.CENTER);

        Gdx.graphics.drawText("" + TitrominosWorld.getInstance().getScore(), 30 + rightRegion.getX(), 265 + rightRegion.getY(), style);

        states.get(TitrominosWorld.getInstance().getState()).draw();
    }


    public void drawText(String text, int x, int y) {
        Log.i(LOG_TAG, "drawText -- begin");
        int len = text.length();
        for (int i = 0; i < len; i++) {
            char character = text.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX;
            int srcWidth;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            Gdx.graphics.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }


    public void pause() {
        Log.i(LOG_TAG, "pause -- begin");

        if(TitrominosWorld.getInstance().getState() == TitrominosWorld.GameState.Running)
            TitrominosWorld.getInstance().setState(TitrominosWorld.GameState.Paused);

        if(TitrominosWorld.getInstance().getState() == TitrominosWorld.GameState.GameOver) {
            Settings.addScore(TitrominosWorld.getInstance().getScore());
            Settings.save(Gdx.fileIO);
        }
    }


    abstract class GameState {
        abstract void update(List<TouchEvent> touchEvents, float deltaTime);
        abstract void draw();
    }

    class GameRunning extends GameState {

        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameRunning.update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                switch(event.type) {
                    case TouchEvent.TOUCH_UP:
                        if(pauseButtonBounds.contains(event.x, event.y)) {
                            if(Settings.soundEnabled)
                                Assets.click.play(1);
                            TitrominosWorld.getInstance().setState(TitrominosWorld.GameState.Paused);
                            return;
                        }
                        break;
                    case TouchEvent.TOUCH_DOWN:
                        if(leftButtonBounds.contains(event.x, event.y)) {
                            TitrominosWorld.getInstance().getFallingShape().moveLeft();
                            if (TitrominosWorld.getInstance().getFallingShape().collide())
                                TitrominosWorld.getInstance().getFallingShape().moveRight();
                        }
                        if(rightButtonBounds.contains(event.x, event.y)) {
                            TitrominosWorld.getInstance().getFallingShape().moveRight();
                            if (TitrominosWorld.getInstance().getFallingShape().collide())
                                TitrominosWorld.getInstance().getFallingShape().moveLeft();
                        }
                        if(rotateButtonBounds.contains(event.x, event.y)) {
                            TitrominosWorld.getInstance().getFallingShape().rotate();
                            if (TitrominosWorld.getInstance().getFallingShape().collide())
                                TitrominosWorld.getInstance().getFallingShape().undoRotate();
                        }
                        if(downButtonBounds.contains(event.x, event.y)) {
                            TitrominosWorld.getInstance().getFallingShape().accelerateFalling();
                        }
                        break;
                }
            }

            TitrominosWorld.getInstance().update(deltaTime);
            if (TitrominosWorld.getInstance().getState() == TitrominosWorld.GameState.GameOver) {
                if(Settings.soundEnabled)
                    Assets.bitten.play(1);
            }
            if(Settings.soundEnabled)
                if (!Assets.music.isPlaying()) {
                    Assets.music.setLooping(true);
                    Assets.music.play();
            }
        }


        void draw() {
            Log.i(LOG_TAG, "GameRunning.draw -- begin");
            Gdx.graphics.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
        }
    }


    class GamePaused extends GameState {

        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GamePaused.update -- begin");

            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(pauseMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        TitrominosWorld.getInstance().setState(TitrominosWorld.GameState.Running);
                        return;
                    }
                    if(homeMenuBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        Gdx.game.setScreen(new StartScreen());
                        return;
                    }
                }
            }
            if(Settings.soundEnabled)
                if (Assets.music.isPlaying())
                    Assets.music.pause();
        }

        void draw() {
            Log.i(LOG_TAG, "GamePaused.draw -- begin");
            Gdx.graphics.drawPixmap(Assets.pausemenu, pauseMenuBounds.getX(), pauseMenuBounds.getY());
        }
    }


    class GameReady extends GameState {

        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameReady.update -- begin");
            if(touchEvents.size() > 0)
                TitrominosWorld.getInstance().setState(TitrominosWorld.GameState.Running);
        }


        void draw() {
            Log.i(LOG_TAG, "GameReady.draw -- begin");
            Graphics g = Gdx.graphics;

            g.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
            g.drawPixmap(Assets.readymenu, readyMenuBounds.getX(), readyMenuBounds.getY());
        }
    }


    class GameOver extends GameState {

        void update(List<TouchEvent> touchEvents, float deltaTime) {
            Log.i(LOG_TAG, "GameOver.update -- begin");
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(xButtonBounds.contains(event.x, event.y)) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        Gdx.game.setScreen(new StartScreen());
                        TitrominosWorld.getInstance().clear();
                        return;
                    }
                }
            }
            if(Settings.soundEnabled)
                if (Assets.music.isPlaying())
                    Assets.music.stop();
        }

        void draw() {
            Log.i(LOG_TAG, "GameOver.draw -- begin");
            Graphics g = Gdx.graphics;

            Gdx.graphics.drawPixmap(Assets.buttons, pauseButtonBounds.getX(), pauseButtonBounds.getY(), 50, 100,
                    pauseButtonBounds.getWidth()+1, pauseButtonBounds.getHeight()+1); // pause button
            g.drawPixmap(Assets.gameoverscreen, gameoverScreenBounds.getX(), gameoverScreenBounds.getY());
            g.drawPixmap(Assets.buttons, xButtonBounds.getX(), xButtonBounds.getY(), 0, 100,
                    xButtonBounds.getWidth()+1, xButtonBounds.getHeight()+1); // down button
            drawText("" + TitrominosWorld.getInstance().getScore(), 180, 280);
        }
    }


    public void resume() {
    }

    public void dispose() {
    }

    public Rectangle getLeftRegion() {
        return leftRegion;
    }
    public Rectangle getRightRegion() {
        return rightRegion;
    }
    public Rectangle getWorkingRegion() {
        return workingRegion;
    }
    public Rectangle getCommandRegion() { return commandRegion; }
}