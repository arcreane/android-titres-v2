
package org.titres.titres.view;

import org.titres.framework.Music;
import org.titres.framework.Pixmap;
import org.titres.framework.Sound;

//Tous les assets de l'app
//Pixmap = pixel format
public class Assets {
    public static Pixmap logo;

    public static Pixmap gamescreen;
    public static Pixmap startscreen;
    public static Pixmap highscoresscreen;
    public static Pixmap gameoverscreen;

    public static Pixmap mainmenu;
    public static Pixmap pausemenu;
    public static Pixmap readymenu;


    public static Pixmap redblock;
    public static Pixmap greenblock;
    public static Pixmap blueblock;
    public static Pixmap cyanblock;
    public static Pixmap yellowblock;
    public static Pixmap magentablock;
    public static Pixmap orangeblock;

    public static Pixmap smallredblock;
    public static Pixmap smallgreenblock;
    public static Pixmap smallblueblock;
    public static Pixmap smallcyanblock;
    public static Pixmap smallyellowblock;
    public static Pixmap smallmagentablock;
    public static Pixmap smallorangeblock;

    public static Pixmap buttons;
    public static Pixmap numbers;

    public static Sound click;
    public static Sound bitten;

    public static Music music;

    public static Pixmap getBlockByColor(int color) {
        switch(color) {
            case 0xffffff00: return Assets.yellowblock;
            case 0xffb2ffff: return Assets.cyanblock;
            case 0xff0000ff: return Assets.blueblock;
            case 0xffff7f00: return Assets.orangeblock;
            case 0xff00ff00: return Assets.greenblock;
            case 0xffff00ff: return Assets.magentablock;
            case 0xffff0000: return Assets.redblock;
            default: return Assets.redblock;
        }
    }

    public static Pixmap getSmallBlockByColor(int color) {
        switch(color) {
            case 0xffffff00: return Assets.smallyellowblock;
            case 0xffb2ffff: return Assets.smallcyanblock;
            case 0xff0000ff: return Assets.smallblueblock;
            case 0xffff7f00: return Assets.smallorangeblock;
            case 0xff00ff00: return Assets.smallgreenblock;
            case 0xffff00ff: return Assets.smallmagentablock;
            case 0xffff0000: return Assets.smallredblock;
            default: return Assets.smallredblock;
        }
    }
}
