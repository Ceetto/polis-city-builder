package polis.game.gameLogic.actorsLogic;

import polis.game.gameLogic.GameView;
import polis.game.gameLogic.actors.Actor;
import polis.game.gameLogic.actors.Sleeper;
import polis.game.gameLogic.tiles.Commerce;

public class ClientModel extends ActActors{

    private final int sleeperAge = Integer.parseInt(engineProps.getProperty("sleeper.age"));

    public ClientModel(ActorsModel model, GameView gameView) {
        super(model, gameView);
    }

    @Override
    public void act(Actor actor) {
        if(actor.getAge() <= 0){
            Commerce shop = (Commerce) actor.getLocation();
            shop.removeClient(actor);
            model.removeActor(actor);
            Sleeper newActor =  new Sleeper(actor.getHouseR(), actor.getHouseC(), sleeperAge, gameView.getCELL_SIZE(), actor.getHouse(), actor.getInhabNum());
            model.addInhabitant(newActor);
        }
        actor.age();
    }
}
