
package org.titres.titres.view;

import org.titres.framework.Gdx;
import org.titres.framework.Rectangle;
import org.titres.titres.model.Settings;
import org.titres.framework.Graphics;
import org.titres.framework.Input.TouchEvent;
import org.titres.framework.Screen;

import java.util.List;

//screen du classement des meilleurs scores
public class HighscoreScreen implements Screen {
    private Rectangle backgroundBounds;
    private Rectangle backButtonBounds;

    String lines[] = new String[5];

    //Zones du background et bouton pour revenir en arrière
    public HighscoreScreen() {
        for (int i = 0; i < 5; i++) {
            lines[i] = "" + (i + 1) + ". " + Settings.highscores[i];
        }

        backgroundBounds=new Rectangle(0, 0, 320, 480);
        backButtonBounds=new Rectangle(32, 370, 50, 50);
    }

    //update du screen quand on clique sur un bouton
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = Gdx.input.getTouchEvents();
        Gdx.input.getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (backButtonBounds.contains(event.x, event.y)) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    Gdx.game.setScreen(new StartScreen());
                    return;
                }
            }
        }
    }
    //Dessin des assets
    public void draw(float deltaTime) {
        Graphics g = Gdx.graphics;

        g.drawPixmap(Assets.highscoresscreen, backgroundBounds.getX(), backgroundBounds.getY());
        int y = 100;
        for (int i = 0; i < 5; i++) {
            drawText(g, lines[i], 20, y);
            y += 50;
        }
        g.drawPixmap(Assets.buttons, backButtonBounds.getX(), backButtonBounds.getY(), 50, 50,
                backButtonBounds.getWidth()+1, backButtonBounds.getHeight()+1);
    }

    //Dessin des textes
    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
    
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
    
            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
