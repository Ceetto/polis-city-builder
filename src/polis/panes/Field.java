package polis.panes;

import javafx.geometry.Pos;
import polis.panes.tiles.Tile;

import java.io.FileNotFoundException;

public class Field extends Drawer {

    public Field() throws FileNotFoundException {
        setAlignment(Pos.TOP_CENTER);

        for(int r = 0; r<DIM; r++){
            for(int c = 0; c<DIM; c++){
                Tile groundTile = new Tile(r, c);
                groundTile.setGroundImage();
                addTile(groundTile, this);
            }
        }
    }
}
