package polis.game.gameLogic.actors;

import javafx.scene.paint.Paint;
import polis.game.gameLogic.tiles.Residence;

public class Shopper extends Inhabitant{
    public Shopper(int r, int c, int age, float cellSize, Residence house, int inHabnum) {
        super(r, c, age, "Shopper", cellSize, house, r, c, inHabnum);
        setFill(Paint.valueOf("cyan"));
    }
}
