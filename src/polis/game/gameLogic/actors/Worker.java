package polis.game.gameLogic.actors;

import polis.game.gameLogic.tiles.Industry;
import polis.game.gameLogic.tiles.Residence;

public class Worker extends Inhabitant{
    private final Industry workplace;

    public Worker(int r, int c, int age, float cellSize, Residence house, int houseR, int houseC, Industry workplace, int inhabNum) {
        super(r, c, age, "Worker", cellSize, house, houseR, houseC, inhabNum);
        this.workplace = workplace;
    }

    public Industry getLocation(){
        return workplace;
    }

}
