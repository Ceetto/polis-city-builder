package polis.game.gameLogic.tiles;

import javafx.scene.layout.Pane;
import polis.game.gameLogic.GameView;
import polis.game.gameLogic.TilesModel;
import polis.game.gameLogic.actors.Actor;

import java.util.ArrayList;
import java.util.List;

public class Residence extends BuildingTile{

    private double capacity = Double.parseDouble(engineProps.getProperty("residential.capacity.initial"));
    private final double minCapacity = Double.parseDouble(engineProps.getProperty("residential.capacity.minimal"));
    private final double jobFound = Double.parseDouble(engineProps.getProperty("factor.job.found"));
    private final double jobNotFound = Double.parseDouble(engineProps.getProperty("factor.job.not.found"));
    private final double shopFound = Double.parseDouble(engineProps.getProperty("factor.shop.found"));
    private final double shopNotFound = Double.parseDouble(engineProps.getProperty("factor.shop.not.found"));
    private final double lvl1to2 = Double.parseDouble(lvlProps.getProperty("residential.level1to2"));
    private final double lvl2to1 = Double.parseDouble(lvlProps.getProperty("residential.level2to1"));
    private final double lvl2to3 = Double.parseDouble(lvlProps.getProperty("residential.level2to3"));
    private final double lvl3to2 = Double.parseDouble(lvlProps.getProperty("residential.level3to2"));

    List<Actor> inhabitants = new ArrayList<>();

    public Residence(String picture, int r, int c, TilesModel model) {
        super(picture, r, c, model);
    }

    public void init(){
        model.getStats().addMaxInhabs(capacity);
        model.getStats().addInhabs(inhabitants.size());
    }

    public void updateLevel(){
        if(capacity < minCapacity){
            capacity = minCapacity;
        }

        if((level == 0 && inhabitants.size() > 0) ||
                (level == 1 && capacity >= lvl1to2) ||
                (level == 2 && capacity >= lvl2to3)
        ){
            model.levelUpTile(r, c);
        }
        else if((level == 2 && capacity < lvl2to1) ||
                level == 3 && capacity < lvl3to2){
            model.levelDownTile(r, c);
        }

        if(inhabitants.size() > capacity){
            List<Actor> remove = new ArrayList<>();
            for(Actor actor:inhabitants){
                if(actor.getInhabNum() > capacity){
                    remove.add(actor);
                }
            }
            inhabitants.removeAll(remove);
            model.getStats().addInhabs(-remove.size());
        }
    }

    public void addInhabitant(Actor inhabitant){
        inhabitants.add(inhabitant);
        updateLevel();
        model.getStats().addInhabs(1);
    }

    public boolean hasRoom(String option){
        return inhabitants.size() < Math.floor(capacity);
    }

    public void jobFound(){
        model.getStats().addMaxInhabs(-capacity);
        capacity *= jobFound;
        updateLevel();
        model.getStats().addMaxInhabs(capacity);
    }

    public void jobNotFound(){
        model.getStats().addMaxInhabs(-capacity);
        capacity *= jobNotFound;
        updateLevel();
        model.getStats().addMaxInhabs(capacity);
    }

    public void shopFound(){
        model.getStats().addMaxInhabs(-capacity);
        capacity *= shopFound;
        updateLevel();
        model.getStats().addMaxInhabs(capacity);
    }

    public void shopNotFound(){
        model.getStats().addMaxInhabs(-capacity);
        capacity *= shopNotFound;
        updateLevel();
        model.getStats().addMaxInhabs(capacity);
    }

    public List<Actor> getInhabitants() {
        return inhabitants;
    }

    @Override
    public void deleteBuilding(Pane pane) {
        super.deleteBuilding(pane);
        model.getStats().addInhabs(inhabitants.size()*-1);
        model.getStats().addMaxInhabs(capacity*-1);
    }

    @Override
    public String statsText() {
        return "Bewoners: " + inhabitants.size() + " / " + round(capacity, 1);
    }

    @Override
    public String titleText() {
        return "Residentieel @ " + r + ":" + c;
    }
}
