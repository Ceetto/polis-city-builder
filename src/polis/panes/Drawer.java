package polis.panes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import polis.panes.tiles.Tile;

import java.io.FileReader;
import java.util.Properties;

public class Drawer extends StackPane {

    protected final int CELL_SIZE = 64;
    protected int DIM;

    public Drawer() {
        try(FileReader reader = new FileReader("resources/polis/properties/settings.properties")){
            Properties props = new Properties();
            props.load(reader);
            this.DIM = Integer.parseInt(props.get("field.size").toString());
        } catch (Exception e){
            this.DIM = 32;
            e.printStackTrace();
        }
    }

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

        if(cursor != null) {
            cursor.getCursorTiles().add(poly);
        }
    }

    public void addTile(Tile tile, Pane pane) {

        int x = CELL_SIZE * (- tile.getR() + tile.getC()) ;
        int y = CELL_SIZE * (tile.getR() + tile.getC()) / 2;
        tile.setTranslateX(x + tile.getDx());
        tile.setTranslateY(y + tile.getDy());

        pane.getChildren().add(tile);
    }
}
