package polis.panes.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BuildingTile extends ImageView {

    private final int r;
    private final int c;
    private int level;
    private final String type;
    private final String picture;

    public BuildingTile(String image, int r, int c) throws FileNotFoundException {
        this.r = r;
        this.c = c;
        this.picture = image;
        setVisual(picture);
        type = image.substring(0, image.indexOf(".")-2);
    }

    public void setVisual(String image) throws FileNotFoundException {
        setImage(new Image(new FileInputStream("resources/polis/tiles/" + image)));
    }

    public void levelUp() throws FileNotFoundException {
        if (level < 3) {
            level++;
            setImage(new Image(new FileInputStream("resources/polis/tiles/" + type + "-" + level + ".png")));
        }
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public int getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public String getPicture() {
        return picture;
    }
}
