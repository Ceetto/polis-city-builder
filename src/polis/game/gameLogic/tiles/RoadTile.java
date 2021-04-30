package polis.game.gameLogic.tiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * basic road tile
 */
public class RoadTile extends Tile {
    private int level;
    private final boolean unbreakable;

    public RoadTile(int r, int c, int level, boolean unbreakable){
        super(r,c);
        this.dx = 0;
        this.dy = 0;
        this.unbreakable = unbreakable;
        this.level = level;
        setVisual();
    }

    public void setVisual(){
        setImage(new Image("/polis/newtiles/road-" + level + ".png"));
    }

    public void updateLevel(RoadTile[][] roadsPlaced, int DIM){
        level = 0;
        if(r-1 >= 0 && roadsPlaced[r-1][c] != null)
            level++;
        if(c+1 < DIM && roadsPlaced[r][c+1] != null)
            level+=2;
        if(r+1 < DIM && roadsPlaced[r+1][c] != null)
            level+=4;
        if(c-1 >= 0 && roadsPlaced[r][c-1] != null)
            level+=8;
        setVisual();
    }

    public void deleteRoad(Pane pane){
        if(!unbreakable) {
            pane.getChildren().remove(this);
        }
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }
}
