package polis.game.gameLogic.tiles;

import javafx.scene.layout.Pane;
import polis.game.gameLogic.TilesModel;
import polis.game.gameLogic.actors.Actor;

import java.util.ArrayList;

/**
 * industriële zone
 */
public class Industry extends BuildingTile{

    private final ArrayList<Actor> workers = new ArrayList<>();

    private final double goodsDelivered = Double.parseDouble(engineProps.getProperty("factor.goods.delivered"));
    private final double goodsNotDelivered = Double.parseDouble(engineProps.getProperty("factor.goods.not.delivered"));

    private boolean exists = true;
    public Industry(String picture, int r, int c, TilesModel model) {
        super(picture, r, c, model);

        setProperties(
                Double.parseDouble(engineProps.getProperty("industrial.capacity.minimal")),
                Double.parseDouble(engineProps.getProperty("industrial.capacity.initial")),
                Double.parseDouble(engineProps.getProperty("industrial.capacity.maximal")),
                Double.parseDouble(lvlProps.getProperty("industrial.level1to2")),
                Double.parseDouble(lvlProps.getProperty("industrial.level2to1")),
                Double.parseDouble(lvlProps.getProperty("industrial.level2to3")),
                Double.parseDouble(lvlProps.getProperty("industrial.level3to2"))
        );
    }

    public void init(){
        addMaxStats(1);
        model.getStats().addJobs(workers.size());
        exists = true;
    }

    public void addWorker(Actor actor){
        workers.add(actor);
        updateLevel(workers);
        model.getStats().addJobs(1);
    }

    public void removeWorker(Actor actor){
        if(exists) {
            workers.remove(actor);
            model.getStats().addJobs(-1);
        }
    }

    public boolean hasRoom(String option){
        return workers.size() < Math.floor(capacity);
    }

    public void goodsDelivered(){
        addMaxStats(-1);
        capacity *= goodsDelivered;
        updateLevel(workers);
        addMaxStats(1);
    }

    public void goodsNotDelivered(){
        addMaxStats(-1);
        capacity *= goodsNotDelivered;
        updateLevel(workers);
        addMaxStats(1);
    }

    @Override
    public void deleteBuilding(Pane pane) {
        super.deleteBuilding(pane);
        model.getStats().addJobs(workers.size()*-1);
        addMaxStats(-1);
        exists = false;
    }

    private void addMaxStats(int fac){
        model.getStats().addMaxJobs(Math.floor(capacity)*fac);
    }

    @Override
    public String statsText() {
        return "Jobs: " + workers.size() + " / " + round(capacity);
    }

    @Override
    public String titleText() {
        return "Industrial @ " + r + ":" + c;
    }
}
