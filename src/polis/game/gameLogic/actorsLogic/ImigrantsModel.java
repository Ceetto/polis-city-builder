package polis.game.gameLogic.actorsLogic;

import polis.game.gameLogic.GameView;
import polis.game.gameLogic.actors.Actor;
import polis.game.gameLogic.actors.Sleeper;
import polis.game.gameLogic.tiles.Residence;

import java.util.Random;

public class ImigrantsModel extends ActActors{

    Random r = new Random();
    private final int sleeperAge = Integer.parseInt(engineProps.getProperty("sleeper.age"));

    private int initialSpawnRate = Integer.parseInt(engineProps.getProperty("region.initial.rate"));
    private double incRate = Double.parseDouble(engineProps.getProperty("region.factor.recovery"));
    private double decRate = Double.parseDouble(engineProps.getProperty("region.factor.slow.down"));
    private int slowestRate = Integer.parseInt(engineProps.getProperty("region.slowest.rate"));
    private int spawnRate = initialSpawnRate;

    public ImigrantsModel(ActorsModel model, GameView gameView) {
        super(model, gameView);
    }

    @Override
    public void act(Actor actor) {

        Residence house = (Residence) findBuilding(actor, "residence", "living");

        if(house != null && house.hasRoom("living")){
            model.removeActor(actor);
            Sleeper newActor = new Sleeper(actor.getR(), actor.getC(), sleeperAge, gameView.getCELL_SIZE(), house, house.getInhabitants().size()+1);
            house.addInhabitant(newActor);
            model.addInhabitant(newActor);
            spawnRate = (int) Math.floor(spawnRate * incRate);
        } else {
            move(actor);
        }

        if(actor.getAge() <= 0){
            model.removeActor(actor);
            spawnRate = (int) Math.floor(spawnRate * decRate);
        }

        if(spawnRate > slowestRate)
            spawnRate = slowestRate;
        if(spawnRate < initialSpawnRate)
            spawnRate = initialSpawnRate;

        actor.age();
    }

    @Override
    public int getSpawnRate(){
        return spawnRate;
    }
}
