package polis.game.gameLogic.tiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import polis.game.gameLogic.TilesModel;
import polis.game.gameLogic.actors.Actor;

import java.io.InputStream;
import java.util.Properties;

public class BuildingTile extends Tile {

    protected int level;
    private final String type;

    protected final TilesModel model;

    protected Properties lvlProps = new Properties();
    protected Properties engineProps = new Properties();

    public BuildingTile(String picture, int r, int c, TilesModel model){
        super(r,c);
        this.model = model;
        this.level = 0;
        setVisual(picture);
        type = picture.substring(0, picture.indexOf(".")-2);

        try(InputStream in = BuildingTile.class.getResourceAsStream("/polis/properties/levels.properties")){
            lvlProps.load(in);
        } catch (Exception e){
            e.printStackTrace();
        }

        try(InputStream in2 = BuildingTile.class.getResourceAsStream("/polis/properties/engine.properties")){
            engineProps.load(in2);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(){}

    public void setVisual(String picture) {
        Image image = new Image("/polis/newtiles/" + picture);
        dx = -0.5 * image.getWidth() + image.getWidth()/2;
        dy = 0.5 * (image.getWidth() - image.getHeight()) - image.getHeight()/2;
        setImage(image);
        setViewOrder(- r - c - 2.0);
    }

    public void levelUp(){
        if (level < 3) {
            level++;
        }
        setVisual(type + "-" + level + ".png");
    }

    public void levelDown(){
        if (level > 0) {
            level--;
        }
        setVisual(type + "-" + level + ".png");
    }

    public void deleteBuilding(Pane pane){
        pane.getChildren().remove(this);
    }

    public String getType(){
        return type;
    }

    public boolean hasRoom(String option){
        return false;
    }

    public void removeWorker(Actor actor){}

    public String statsText(){
        return "";
    }

    public String titleText(){
        return "";
    }

    protected double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
