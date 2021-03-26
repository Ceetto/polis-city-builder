package polis.panes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Drawer extends StackPane {

    protected final int CELL_SIZE = 64;
    protected final int DIM = 32;

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
}
