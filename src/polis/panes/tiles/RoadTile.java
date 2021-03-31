package polis.panes.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RoadTile extends ImageView {
    private final int r;
    private final int c;

    public RoadTile(int r, int c) throws FileNotFoundException {
        this.r = r;
        this.c = c;
        setVisual(0);
    }

    public void setVisual(int level) throws FileNotFoundException {
        setImage(new Image(new FileInputStream("resources/polis/tiles/road-" + level + ".png")));
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }
}
