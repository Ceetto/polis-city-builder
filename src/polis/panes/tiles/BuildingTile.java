package polis.panes.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BuildingTile extends ImageView {

    private final int r;
    private final int c;
    private int level = 1;
    private final String type;
    private final String picture;
    private double dx;
    private double dy;

    private Image image;

    public BuildingTile(String picture, int r, int c) throws FileNotFoundException {
        this.r = r;
        this.c = c;
        this.picture = picture;
        setVisual(picture);
        type = picture.substring(0, picture.indexOf(".")-2);

    }

    public void setVisual(String picture) throws FileNotFoundException {
        image = new Image(new FileInputStream("resources/polis/tiles/" + picture));

        System.out.println("dimensies");
        System.out.println(image.getWidth() + ", " + image.getHeight());
        dx = -0.5 * image.getWidth() + image.getWidth()/2;
        dy = 0.5 * (image.getWidth() - image.getHeight()) - image.getHeight()/2;
        System.out.println("referentiepunt:");
        System.out.println(getX() + ", " + getY());
        System.out.println("place:");
        System.out.println(getTranslateX() + ", " + getTranslateY());
        System.out.println();
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

    public Image getPicture() {
        return image;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}
