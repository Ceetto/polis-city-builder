package polis.game.gameLogic;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import polis.game.gameLogic.actors.Actor;
import polis.game.gameLogic.actorsLogic.ActorsModel;
import polis.game.gameLogic.tiles.Tile;
import polis.game.mouse.Cursor;

/**
 * bovenklasse van veel panes die dingen moeten tekenen en/of DIM en CELL_SIZE values nodig hebben.
 */
public class Drawer extends StackPane {
    protected static final int CELL_SIZE = 64;
    protected static int DIM;

    public Drawer(int DIM){
        Drawer.DIM = DIM;
    }

    //tekent een gewone polygon
    public void addPoly(int r, int c, int size, Pane pane, Color fillColor, Color strokeCollor, Double strokeWidth, Cursor cursor){
        Polygon poly = new Polygon(
                0, 0,
                CELL_SIZE * size, 0.5 * CELL_SIZE * size,
                0, CELL_SIZE * size,
                -CELL_SIZE * size, 0.5 * CELL_SIZE * size
        );
        poly.setFill(fillColor);

        poly.setStrokeWidth(strokeWidth);
        poly.setStroke(strokeCollor);

        int x = CELL_SIZE * (- r + c) ;
        int y = CELL_SIZE * (r + c) / 2;
        poly.setTranslateX(x);
        poly.setTranslateY(y);

        poly.setViewOrder(-r - c - size);

        pane.getChildren().add(poly);

        if(cursor != null) {
            cursor.getCursorTiles().add(poly);
        }
    }

    //tekent een tile (RoadTile, BuildingTile of gewoon Tile (voor field tiles)
    public void addTile(Tile tile) {
        int x = CELL_SIZE * (- tile.getR() + tile.getC()) ;
        int y = CELL_SIZE * (tile.getR() + tile.getC()) / 2;
        tile.setTranslateX(x + tile.getDx());
        tile.setTranslateY(y + tile.getDy());

        this.getChildren().add(tile);
    }

    //voegt een actor toe
    public void addActor(Actor actor, Pane pane){
        int dx = 0;
        int dy = 0;
        if(actor.getDir() == ActorsModel.Dirs.NORTH){
            dx = 25;
            dy = 13;
        }
        if(actor.getDir() == ActorsModel.Dirs.EAST){
            dx = -25;
            dy = 13;
        }
        int x = CELL_SIZE * (- actor.getR() + actor.getC());
        int y = CELL_SIZE * (actor.getR() + actor.getC()) / 2;
        actor.setTranslateX(x + dx);
        actor.setTranslateY(y + dy);

        pane.getChildren().add(actor);
    }

    public int getCELL_SIZE() {
        return CELL_SIZE;
    }

    public int getDIM() {
        return DIM;
    }
}
