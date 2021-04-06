package polis.panes.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BuildingTile extends Tile {

    private int level = 0;
    private final String type;

    public BuildingTile(String picture, int r, int c) throws FileNotFoundException {
        super(r,c);
        setVisual(picture);
        type = picture.substring(0, picture.indexOf(".")-2);

    }

    public void setVisual(String picture) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("resources/polis/newtiles/" + picture));
        dx = -0.5 * image.getWidth() + image.getWidth()/2;
        dy = 0.5 * (image.getWidth() - image.getHeight()) - image.getHeight()/2;
        setImage(image);
        setViewOrder(- r - c - 2.0);
    }

    public void levelUp() throws FileNotFoundException {
        if (level < 3) {
            level++;
        } else {
            level = 0;
        }
        setVisual(type + "-" + level + ".png");
    }

    public void deleteBuilding(Pane pane){
        pane.getChildren().remove(this);
    }

}
