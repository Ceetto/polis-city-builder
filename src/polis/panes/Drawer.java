package polis.panes;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import polis.panes.tiles.BuildingTile;
import polis.panes.tiles.RoadTile;

import java.io.FileNotFoundException;

public class Drawer extends StackPane {

    protected final int CELL_SIZE = 64;
    protected final int DIM = 16;

    public void addPoly(int r, int k, int size, Pane pane, Color fillColor, Color strokeCollor, Double strokeWidth){
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

        pane.getChildren().add(poly);
    }

    public void addRoadTile(int r, int k, Pane pane, int level) throws FileNotFoundException {

        int x = CELL_SIZE * (- r + k) ;
        int y = CELL_SIZE * (r + k) / 2;
        RoadTile tile = new RoadTile(r, k);
        tile.setTranslateX(x);
        tile.setTranslateY(y);

        tile.setVisual(level);
        pane.getChildren().add(tile);
    }

    public void addBuildingTile(int r, int k, Pane pane, String image) throws FileNotFoundException {

        int x = CELL_SIZE * (- r + k) ;
        int y = CELL_SIZE * (r + k) / 2;
        ImageView tile = new BuildingTile(image, r, k);
        tile.setTranslateX(x);
        tile.setTranslateY(y);

        pane.getChildren().add(tile);
    }
}
