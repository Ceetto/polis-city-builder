package polis.game.gameLogic.actorsLogic;

import polis.game.gameLogic.GameView;
import polis.game.gameLogic.actors.Actor;
import polis.game.gameLogic.tiles.Commerce;
import polis.game.gameLogic.tiles.Industry;

public class GoodsModel extends ActActors{
    public GoodsModel(ActorsModel model, GameView gameView) {
        super(model, gameView);
    }

    @Override
    public void act(Actor actor){
        move(actor);
        Commerce destination = (Commerce) findBuilding(actor, "commerce", "goods");
        Industry origin = (Industry) actor.getLocation();
        if(destination != null){
                        destination.addGoods();
            origin.goodsDelivered();
            model.removeActor(actor);
        }
        actor.age();
        if(actor.getAge() <= 0){
            origin.goodsNotDelivered();
            model.removeActor(actor);
        }
    }
}
