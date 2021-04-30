package polis.game.gameLogic.actorsLogic;

import polis.game.gameLogic.GameView;
import polis.game.gameLogic.actors.Actor;
import polis.game.gameLogic.actors.Goods;
import polis.game.gameLogic.tiles.Industry;

public class WorkerModel extends ActActors{

    private final int stepsPerGoods = Integer.parseInt(engineProps.getProperty("steps.per.goods"));
    private final int goodsAge = Integer.parseInt(engineProps.getProperty("goods.age"));

    public WorkerModel(ActorsModel model, GameView gameView) {
        super(model, gameView);
    }

    @Override
    public void act(Actor actor){

        if(actor.getAge()%stepsPerGoods == 0){
            model.addInhabitant(new Goods(actor.getR(), actor.getC(), goodsAge, gameView.getCELL_SIZE(), (Industry) actor.getLocation()));
        }

        workThenShop(actor);
    }
}
