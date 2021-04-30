package polis.game.gameLogic.actorsLogic;

import polis.game.gameLogic.GameView;
import polis.game.gameLogic.actors.Actor;
import polis.game.gameLogic.actors.JobSeeker;

public class SleepersModel extends ActActors{

    private final int jobSeekerAge = Integer.parseInt(engineProps.getProperty("jobseeker.age"));

    public SleepersModel(ActorsModel model, GameView gameView) {
        super(model, gameView);
    }

    @Override
    public void act(Actor actor){
        if(actor.getAge() <= 0){
            model.removeActor(actor);
            JobSeeker newActor = new JobSeeker(actor.getR(), actor.getC(), jobSeekerAge, gameView.getCELL_SIZE(), actor.getHouse(), actor.getInhabNum());
            if(actor.getInhabNum() <= actor.getHouse().getInhabitants().size() && model.getBuildingsPlaced()[actor.getHouse().getR()][actor.getHouse().getC()] == actor.getHouse())
                model.addInhabitant(newActor);
        }
        actor.age();
    }
}
