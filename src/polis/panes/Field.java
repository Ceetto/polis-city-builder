package polis.panes;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Field extends Drawer {

    Polygon playingField = new Polygon(
            0, 0,
            CELL_SIZE * DIM, 0.5 * CELL_SIZE * DIM,
            0, CELL_SIZE * DIM,
            -CELL_SIZE * DIM, 0.5 * CELL_SIZE * DIM
    );



    public Field(){
        setAlignment(Pos.TOP_CENTER);

        playingField.setFill(Color.rgb(204,249,170,1));
        getChildren().add(playingField);
    }
}
