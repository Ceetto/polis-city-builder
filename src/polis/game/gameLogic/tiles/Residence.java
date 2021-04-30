package polis.game.gameLogic.tiles;

import javafx.scene.layout.Pane;
import polis.game.gameLogic.TilesModel;
import polis.game.gameLogic.actors.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * residentiële zone
 */
public class Residence extends BuildingTile{

    private final double jobFound = Double.parseDouble(engineProps.getProperty("factor.job.found"));
    private final double jobNotFound = Double.parseDouble(engineProps.getProperty("factor.job.not.found"));
    private final double shopFound = Double.parseDouble(engineProps.getProperty("factor.shop.found"));
    private final double shopNotFound = Double.parseDouble(engineProps.getProperty("factor.shop.not.found"));

    List<Actor> inhabitants = new ArrayList<>();

    public Residence(String picture, int r, int c, TilesModel model) {
        super(picture, r, c, model);
        
        setProperties(
                Double.parseDouble(engineProps.getProperty("residential.capacity.minimal")),
                Double.parseDouble(engineProps.getProperty("residential.capacity.initial")),
                Double.parseDouble(engineProps.getProperty("residential.capacity.maximal")),
                Double.parseDouble(lvlProps.getProperty("residential.level1to2")),
                Double.parseDouble(lvlProps.getProperty("residential.level2to1")),
                Double.parseDouble(lvlProps.getProperty("residential.level2to3")),
                Double.parseDouble(lvlProps.getProperty("residential.level3to2"))
        );
    }

    public void init(){
        addMaxStats(1);
        model.getStats().addInhabs(inhabitants.size());
    }

    public void updateLevel(List<Actor> actors){
        super.updateLevel(actors);

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
        updateLevel(inhabitants);
    }

    public boolean hasRoom(String option){
        return inhabitants.size() < Math.floor(capacity);
    }

    public void jobFound(){
        addMaxStats(-1);
        capacity *= jobFound;
        updateLevel(inhabitants);
        addMaxStats(1);
    }

    public void jobNotFound(){
        addMaxStats(-1);
        capacity *= jobNotFound;
        updateLevel(inhabitants);
        addMaxStats(1);
    }

    public void shopFound(){
        addMaxStats(-1);
        capacity *= shopFound;
        updateLevel(inhabitants);
        addMaxStats(1);
    }

    public void shopNotFound(){
        addMaxStats(-1);
        capacity *= shopNotFound;
        updateLevel(inhabitants);
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
