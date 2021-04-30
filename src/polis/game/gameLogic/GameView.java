package polis.game.gameLogic;

import javafx.geometry.Pos;

/**
 * View waar alles van het spel op getekend wordt.
 */
public class GameView extends Drawer {
    public GameView(int DIM){
        super(DIM);
        setAlignment(Pos.TOP_CENTER);
    }
}
