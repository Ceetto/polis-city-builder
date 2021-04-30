package polis.game.gameLogic.tiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import polis.game.gameLogic.TilesModel;
import polis.game.gameLogic.actors.Actor;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * bovenklasse voor gebouwen.
 */
public class BuildingTile extends Tile {

    protected int level;
    private final String type;

    protected final TilesModel model;

    protected Properties lvlProps = new Properties();
    protected Properties engineProps = new Properties();

    protected double minCapacity;
    protected double capacity;
    protected double maxCapacity;
    protected double lvl1to2;
    protected double lvl2to1;
    protected double lvl2to3;
    protected double lvl3to2;

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
    
    public void setProperties( double minCapacity, double capacity, double maxCapacity, double lvl1to2, double lvl2to1,
                              double lvl2to3, double lvl3to2){
        this.minCapacity = minCapacity;
        this.capacity = capacity;
        this.maxCapacity = maxCapacity;
        this.lvl1to2 = lvl1to2;
        this.lvl2to1 = lvl2to1;
        this.lvl2to3 = lvl2to3;
        this.lvl3to2 = lvl3to2;
    }

    /**
     * initialisatie om waarden door te geven aan het stats paneel
     */
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

    public void updateLevel(List<Actor> actors){
        if(capacity < minCapacity){
            capacity = minCapacity;
        } else if (capacity > maxCapacity) {
            capacity = maxCapacity;
        }

        if((level == 0 && actors.size() > 0) ||
                (level == 1 && capacity >= lvl1to2) ||
                (level == 2 && capacity >= lvl2to3)
        ){
            model.levelUpTile(r, c);
        } else if((level == 2 && capacity <= lvl2to1) ||
                level == 3 && capacity <= lvl3to2){
            model.levelDownTile(r, c);
        }
    }

    /**
     * verwijdert het gebouw
     */
    public void deleteBuilding(Pane pane){
        pane.getChildren().remove(this);
    }

    public String getType(){
        return type;
    }

    /**
     *  checkt of een gebouw nog plaats heeft voor een bepaald soort actor
     */
    public boolean hasRoom(String option){
        return false;
    }

    public void removeWorker(Actor actor){}

    /**
     * text voor stats paneel
     */
    public String statsText(){
        return "";
    }
    public String titleText(){
        return "";
    }

    /**
     * afrondingsmethode voor stats paneel
     */
    protected double round(double value) {
        int precision = 2;
        int scale = (int) Math.pow(10, precision);
        return Math.floor(value * scale) / scale;
    }
}
