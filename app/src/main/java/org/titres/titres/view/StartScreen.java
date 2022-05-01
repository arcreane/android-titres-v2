
package org.titres.titres.view;

import org.titres.framework.Rectangle;
import org.titres.titres.model.Settings;
import org.titres.framework.Gdx;
import org.titres.framework.Graphics;
import org.titres.framework.Input;
import org.titres.framework.Screen;

import java.util.List;


public class StartScreen implements Screen {
    private Rectangle backgroundBounds;
    private Rectangle logoBounds;
    private Rectangle soundButtonBounds;
    private Rectangle mainMenuBounds;
    private Rectangle playMenuBounds;
    private Rectangle highscoresMenuBounds;
    private Rectangle quitMenuBounds;

    public StartScreen() {
        backgroundBounds=new Rectangle(0, 0, 320, 480);
        logoBounds=new Rectangle(32, 20, 256, 160);
        soundButtonBounds=new Rectangle(32, 370, 50, 50);
        mainMenuBounds=new Rectangle(84, 220, 153, 124);
        playMenuBounds=new Rectangle(64, 220, 192, 42);
        highscoresMenuBounds=new Rectangle(64, 220 + 42, 192, 42);
        quitMenuBounds=new Rectangle(64, 220 + 84, 192, 42);
    }

    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = Gdx.input.getTouchEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(soundButtonBounds.contains(event.x, event.y)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                if(playMenuBounds.contains(event.x, event.y)) {
                    Gdx.game.setScreen(new GameScreen());
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(highscoresMenuBounds.contains(event.x, event.y)) {
                    Gdx.game.setScreen(new HighscoreScreen());
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(quitMenuBounds.contains(event.x, event.y)) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    System.exit(1);
                    return;
                }
            }
        }
    }

    public void draw(float deltaTime) {
        Graphics g = Gdx.graphics;

        g.drawPixmap(Assets.startscreen, backgroundBounds.getX(), backgroundBounds.getY());
        g.drawPixmap(Assets.logo, logoBounds.getX(), logoBounds.getY());
        g.drawPixmap(Assets.mainmenu, mainMenuBounds.getX(), mainMenuBounds.getY());
        if(Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, soundButtonBounds.getX(), soundButtonBounds.getY(), 0, 0,
                    soundButtonBounds.getWidth()+1, soundButtonBounds.getHeight()+1);
        else
            g.drawPixmap(Assets.buttons, soundButtonBounds.getX(), soundButtonBounds.getY(), 50, 0,
                    soundButtonBounds.getWidth()+1, soundButtonBounds.getHeight()+1);
    }

    public void pause() {
        Settings.save(Gdx.fileIO);
    }

    public void resume() {
    }

    public void dispose() {
    }
}
