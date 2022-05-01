
package org.titres.titres.view;

import org.titres.framework.Screen;
import org.titres.framework.impl.AndroidGame;

public class TitrominosGame extends AndroidGame {

    public Screen getStartScreen() {
        return new LoadingScreen();
    }
}