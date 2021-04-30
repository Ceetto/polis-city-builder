package polis.game.gameLogic.tiles;

import javafx.scene.layout.Pane;
import polis.game.gameLogic.GameView;
import polis.game.gameLogic.TilesModel;
import polis.game.gameLogic.actors.Actor;

import java.util.ArrayList;

public class Industry extends BuildingTile{

    private ArrayList<Actor> workers = new ArrayList<>();

    private double capacity = Double.parseDouble(engineProps.getProperty("industrial.capacity.initial"));
    private final double minCapacity = Double.parseDouble(engineProps.getProperty("industrial.capacity.minimal"));
    private final double goodsDelivered = Double.parseDouble(engineProps.getProperty("factor.goods.delivered"));
    private final double goodsNotDelivered = Double.parseDouble(engineProps.getProperty("factor.goods.not.delivered"));
    private final double lvl1to2 = Double.parseDouble(lvlProps.getProperty("industrial.level1to2"));
    private final double lvl2to1 = Double.parseDouble(lvlProps.getProperty("industrial.level2to1"));
    private final double lvl2to3 = Double.parseDouble(lvlProps.getProperty("industrial.level2to3"));
    private final double lvl3to2 = Double.parseDouble(lvlProps.getProperty("industrial.level3to2"));

    public Industry(String picture, int r, int c, TilesModel model) {
        super(picture, r, c, model);
        updateLevel();
    }

    public void init(){
        model.getStats().addMaxJobs(capacity);
        model.getStats().addJobs(workers.size());
    }

    public void updateLevel(){
        if(capacity < minCapacity){
            capacity = minCapacity;
        }

        if((level == 0 && workers.size() > 0) ||
                (level == 1 && capacity >= lvl1to2) ||
                (level == 2 && capacity >= lvl2to3)
        ){
            model.levelUpTile(r, c);
        }
        else if((level == 2 && capacity < lvl2to1) ||
                level == 3 && capacity < lvl3to2){
            model.levelDownTile(r, c);
        }
    }

    public void addWorker(Actor actor){
        workers.add(actor);
        updateLevel();
        model.getStats().addJobs(1);
    }

    public void removeWorker(Actor actor){
        workers.remove(actor);
        model.getStats().addJobs(-1);
    }

    public boolean hasRoom(String option){
        return workers.size() < Math.floor(capacity);
    }

    public void goodsDelivered(){
        model.getStats().addMaxJobs(-capacity);
        capacity *= goodsDelivered;
        updateLevel();
        model.getStats().addMaxJobs(capacity);
    }

    public void goodsNotDelivered(){
        model.getStats().addMaxJobs(-capacity);
        capacity *= goodsNotDelivered;
        updateLevel();
        model.getStats().addMaxJobs(capacity);
    }

    @Override
    public void deleteBuilding(Pane pane) {
        super.deleteBuilding(pane);
        model.getStats().addJobs(workers.size()*-1);
        model.getStats().addMaxJobs(capacity*-1);
    }

    @Override
    public String statsText() {
        return "Jobs: " + workers.size() + " / " + round(capacity,1);
    }

    @Override
    public String titleText() {
        return "Industrieel @ " + r + ":" + c;
    }
}
