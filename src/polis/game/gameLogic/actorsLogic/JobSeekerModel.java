package polis.game.gameLogic.actorsLogic;

import polis.game.gameLogic.GameView;
import polis.game.gameLogic.actors.Actor;
import polis.game.gameLogic.actors.Trader;
import polis.game.gameLogic.actors.Worker;
import polis.game.gameLogic.tiles.Commerce;
import polis.game.gameLogic.tiles.Industry;

import java.util.Random;

public class JobSeekerModel extends ActActors{

    private Random r = new Random();
    private final int workerAge = Integer.parseInt(engineProps.getProperty("worker.age"));
    private final int traderAge = Integer.parseInt(engineProps.getProperty("trader.age"));

    public JobSeekerModel(ActorsModel model, GameView gameView) {
        super(model, gameView);
    }

    @Override
    public void act(Actor actor){
        sleepIfDone(actor);
        if(actor.getAge() <= 0){
            actor.getHouse().jobNotFound();
        }

        Commerce tradingPlace = (Commerce) findBuilding(actor, "commerce", "worker");
        Industry workplace = (Industry) findBuilding(actor, "industry", "worker");

        if(r.nextInt(2) == 0 && tradingPlace != null && tradingPlace.hasRoom("worker")){
            model.removeActor(actor);
            Trader newActor = new Trader(actor.getR(), actor.getC(), traderAge, gameView.getCELL_SIZE(), actor.getHouse(), actor.getHouseR(), actor.getHouseC(), tradingPlace, actor.getInhabNum());
            tradingPlace.addWorker(newActor);
            model.addInhabitant(newActor);
        } else if (workplace != null && workplace.hasRoom("worker")){
            model.removeActor(actor);
            Worker newActor = new Worker(actor.getR(), actor.getC(), workerAge, gameView.getCELL_SIZE(), actor.getHouse(), actor.getHouseR(), actor.getHouseC(), workplace, actor.getInhabNum());
            workplace.addWorker(newActor);
            model.addInhabitant(newActor);
        } else if (tradingPlace != null && tradingPlace.hasRoom("worker")){
            model.removeActor(actor);
            Trader newActor = new Trader(actor.getR(), actor.getC(), traderAge, gameView.getCELL_SIZE(), actor.getHouse(), actor.getHouseR(), actor.getHouseC(), tradingPlace, actor.getInhabNum());
            tradingPlace.addWorker(newActor);
            model.addInhabitant(newActor);
        }

        if(tradingPlace != null || workplace != null){
            actor.getHouse().jobFound();
        }

        actor.age();
    }
}
