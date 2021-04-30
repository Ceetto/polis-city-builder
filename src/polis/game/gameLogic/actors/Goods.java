package polis.game.gameLogic.actors;

import javafx.scene.paint.Paint;
import polis.game.gameLogic.tiles.BuildingTile;
import polis.game.gameLogic.tiles.Industry;

public class Goods extends Actor{

    private Industry origin;
    public Goods(int r, int c, int age, float cellSize, Industry origin) {
        super(r, c, age, "Goods", cellSize);
        setFill(Paint.valueOf("yellow"));
        this.origin = origin;
    }

    @Override
    public BuildingTile getLocation(){
        return origin;
    }
}
