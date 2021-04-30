package polis.game.gameLogic.tiles;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.game.gameLogic.GameView;

public class Tile extends ImageView {
    protected final int r;
    protected final int c;

    protected double dx;
    protected double dy;


    public Tile(int r, int c){
        this.r = r;
        this.c = c;
    }

    public void setGroundImage(){
        Image image = new Image("/polis/newtiles/ground.png");
        setImage(image);
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}
