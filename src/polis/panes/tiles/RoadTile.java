package polis.panes.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RoadTile extends ImageView {
    private final int r;
    private final int c;
    private int level;

    public RoadTile(int r, int c, int level) throws FileNotFoundException {
        this.r = r;
        this.c = c;
        this.level = level;
        setVisual();
    }

    public void setVisual() throws FileNotFoundException {
        setImage(new Image(new FileInputStream("resources/polis/tiles/road-" + level + ".png")));
    }

    public void updateLevel(int[][] roadsPlaced, int DIM) throws FileNotFoundException {
        level = 0;
        if(r-1 >= 0 && roadsPlaced[r-1][c] == 1)
            level++;
        if(c+1 < DIM && roadsPlaced[r][c+1] == 1)
            level+=2;
        if(r+1 < DIM && roadsPlaced[r+1][c] == 1)
            level+=4;
        if(c-1 >= 0 && roadsPlaced[r][c-1] == 1)
            level+=8;
        setVisual();
    }

    public void deleteRoad(Pane pane){
        pane.getChildren().remove(this);
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

}
