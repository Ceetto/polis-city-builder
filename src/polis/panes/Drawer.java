package polis.panes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import polis.panes.tiles.BuildingTile;
import polis.panes.tiles.RoadTile;

public class Drawer extends StackPane {

    protected final int CELL_SIZE = 64;
    protected final int DIM = 32;

    public void addPoly(int r, int k, int size, Pane pane, Color fillColor, Color strokeCollor, Double strokeWidth, Cursor cursor){
        Polygon poly = new Polygon(
                0, 0,
                CELL_SIZE * size, 0.5 * CELL_SIZE * size,
                0, CELL_SIZE * size,
                -CELL_SIZE * size, 0.5 * CELL_SIZE * size
        );
        poly.setFill(fillColor);

        poly.setStrokeWidth(strokeWidth);
        poly.setStroke(strokeCollor);

        int x = CELL_SIZE * (- r + k) ;
        int y = CELL_SIZE * (r + k) / 2;
        poly.setTranslateX(x);
        poly.setTranslateY(y);

        poly.setViewOrder(-r - k - size);

        pane.getChildren().add(poly);
        cursor.getCursorTiles().add(poly);
    }

    public void addRoadTile(RoadTile tile, Pane pane) {

        int x = CELL_SIZE * (- tile.getR() + tile.getC()) ;
        int y = CELL_SIZE * (tile.getR() + tile.getC()) / 2;
        tile.setTranslateX(x);
        tile.setTranslateY(y);

        pane.getChildren().add(tile);
    }

    public void addBuildingTile(BuildingTile tile, Pane pane) {

        int x = CELL_SIZE * (- tile.getR() + tile.getC()) ;
        int y = CELL_SIZE * (tile.getR() + tile.getC()) / 2;

        tile.setTranslateX(x + tile.getDx());
        tile.setTranslateY(y + tile.getDy());

        pane.getChildren().add(tile);
    }
}
