package polis.game.gameLogic.tiles;

import javafx.scene.layout.Pane;
import polis.game.gameLogic.TilesModel;
import polis.game.gameLogic.actors.Actor;

import java.util.ArrayList;
import java.util.List;

public class Residence extends BuildingTile{

    private double capacity = Double.parseDouble(engineProps.getProperty("residential.capacity.initial"));
    private final double minCapacity = Double.parseDouble(engineProps.getProperty("residential.capacity.minimal"));
    private final double maxCapacity = Double.parseDouble(engineProps.getProperty("residential.capacity.maximal"));
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
        addMaxStats(1);
        model.getStats().addInhabs(inhabitants.size());
    }

    public void updateLevel(){
        if(capacity < minCapacity){
            capacity = minCapacity;
        } else if (capacity >= maxCapacity){
            capacity = maxCapacity;
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
        model.getStats().addInhabs(1);
        updateLevel();
    }

    public boolean hasRoom(String option){
        return inhabitants.size() < Math.floor(capacity);
    }

    public void jobFound(){
        addMaxStats(-1);
        capacity *= jobFound;
        updateLevel();
        addMaxStats(1);
    }

    public void jobNotFound(){
        addMaxStats(-1);
        capacity *= jobNotFound;
        updateLevel();
        addMaxStats(1);
    }

    public void shopFound(){
        addMaxStats(-1);
        capacity *= shopFound;
        updateLevel();
        addMaxStats(1);
    }

    public void shopNotFound(){
        addMaxStats(-1);
        capacity *= shopNotFound;
        updateLevel();
        addMaxStats(1);
    }

    public void addMaxStats(int fac){
        model.getStats().addMaxInhabs(Math.floor(capacity)*fac);
    }




    public List<Actor> getInhabitants() {
        return inhabitants;
    }

    @Override
    public void deleteBuilding(Pane pane) {
        super.deleteBuilding(pane);
        model.getStats().addInhabs(inhabitants.size()*-1);
        addMaxStats(-1);
    }

    @Override
    public String statsText() {
        return "Inhabitants: " + inhabitants.size() + " / " + round(capacity);
    }

    @Override
    public String titleText() {
        return "Residential @ " + r + ":" + c;
    }
}
