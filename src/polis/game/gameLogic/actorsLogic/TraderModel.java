package polis.game.gameLogic.actorsLogic;

import polis.game.gameLogic.GameView;
import polis.game.gameLogic.actors.Actor;

public class TraderModel extends ActActors{
    public TraderModel(ActorsModel model, GameView gameView) {
        super(model, gameView);
    }

    @Override
    public void act(Actor actor){
        workThenShop(actor);
    }
}
