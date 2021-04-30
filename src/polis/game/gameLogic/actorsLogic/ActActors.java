package polis.game.gameLogic.actorsLogic;

import polis.game.gameLogic.GameView;
import polis.game.gameLogic.actors.Actor;
import polis.game.gameLogic.actors.Inhabitant;
import polis.game.gameLogic.actors.Shopper;
import polis.game.gameLogic.actors.Sleeper;
import polis.game.gameLogic.tiles.BuildingTile;

import java.io.InputStream;
import java.util.*;

public abstract class ActActors {

    protected final ActorsModel model;

    protected final GameView gameView;
    private final Random r = new Random();
    protected final Map<ActorsModel.Dirs, int[]> directions = Map.of(
            ActorsModel.Dirs.NORTH, new int[]{-1, 0},
            ActorsModel.Dirs.SOUTH, new int[]{1,0},
            ActorsModel.Dirs.EAST, new int[]{0,1},
            ActorsModel.Dirs.WEST, new int[]{0,-1}
    );

    protected final Properties lvlProps  = new Properties();
    protected final Properties engineProps = new Properties();
    private final int shopperAge;
    private final int sleeperAge;

    public ActActors(ActorsModel model, GameView gameView){
        this.model = model;
        this.gameView = gameView;

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

        this.shopperAge = Integer.parseInt(engineProps.getProperty("shopper.age"));
        this.sleeperAge = Integer.parseInt(engineProps.getProperty("sleeper.age"));
    }

    public void act(Actor actor) {
        actor.age();
        if(actor.getAge() <= 0){
            model.removeActor(actor);
        }
    }

    public void move(Actor actor){
        if(canMoveToTile(actor, actor.getDir())){
            gameView.getChildren().remove(actor);
            actor.setPos(actor.getR() + directions.get(actor.getDir())[0], actor.getC() + directions.get(actor.getDir())[1]);
            gameView.addActor(actor, gameView);
        }
        ArrayList<ActorsModel.Dirs> nextDirs = new ArrayList<>(Arrays.asList(actor.getDir().left(), actor.getDir().right(), actor.getDir()));
        ArrayList<ActorsModel.Dirs> possibleDirs = new ArrayList<>();
        for(ActorsModel.Dirs dir:nextDirs){
            if(canMoveToTile(actor, dir)){
                possibleDirs.add(dir);
            }
        }
        actor.setRandomDir(possibleDirs);
        if(!canMoveToTile(actor, actor.getDir())){
            actor.setDir(actor.getDir().back());
        }

    }

    public boolean canMoveToTile(Actor actor, ActorsModel.Dirs dir){
        return withinBounds(actor.getR() + directions.get(dir)[0], actor.getC() + directions.get(dir)[1])
                && model.getRoadsPlaced()[actor.getR() + directions.get(dir)[0]][actor.getC() + directions.get(dir)[1]] != null;
    }

    public boolean withinBounds(int r, int c){
        return r >= 0 && r < gameView.getDIM() && c >= 0 && c < gameView.getDIM();
    }

    public BuildingTile findBuilding(Actor actor, String building, String option){
        ActorsModel.Dirs left = actor.getDir().left();
        ActorsModel.Dirs right = actor.getDir().right();

        int leftR = actor.getR() + directions.get(left)[0];
        int leftC = actor.getC() + directions.get(left)[1];

        int rightR = actor.getR() + directions.get(right)[0];
        int rightC = actor.getC() + directions.get(right)[1];

        BuildingTile destination = null;
        BuildingTile buildLeft = null;
        BuildingTile buildRight = null;

        if(withinBounds(leftR, leftC)
                && model.getBuildingsPlaced()[leftR][leftC] != null
                && model.getBuildingsPlaced()[leftR][leftC].getType().equals(building)) {
            buildLeft = model.getBuildingsPlaced()[leftR][leftC];
        }if(withinBounds(rightR, rightC)
                && model.getBuildingsPlaced()[rightR][rightC] != null
                && model.getBuildingsPlaced()[rightR][rightC].getType().equals(building)){
            buildRight = model.getBuildingsPlaced()[rightR][rightC];
        }

        if(r.nextInt(2) == 0 && buildLeft != null && buildLeft.hasRoom(option)){
            destination = buildLeft;
        } else if(buildRight != null && buildRight.hasRoom(option)){
            destination = buildRight;
        } else if(buildLeft != null && buildLeft.hasRoom(option)){
            destination =  buildLeft;
        }
        return destination;
    }

    public void sleepIfDone(Actor actor){
        move(actor);
        if(actor.getAge() <= 0){
            model.removeActor(actor);
            Sleeper newActor =  new Sleeper(actor.getHouseR(), actor.getHouseC(), sleeperAge, gameView.getCELL_SIZE(), actor.getHouse(), actor.getInhabNum());
            if(model.getBuildingsPlaced()[actor.getHouse().getR()][actor.getHouse().getC()] == actor.getHouse())
                model.addInhabitant(newActor);
        }
    }

    public void workThenShop(Actor actor){
        actor.age();
        if(actor.getAge() <= 0){
            model.removeActor(actor);
            Actor newActor = new Shopper(actor.getHouseR(), actor.getHouseC(), shopperAge, gameView.getCELL_SIZE(), actor.getHouse(), actor.getInhabNum());
            actor.getLocation().removeWorker(actor);
            model.addInhabitant(newActor);
        }
    }

    public int getSpawnRate(){
        return 0;
    }
}
