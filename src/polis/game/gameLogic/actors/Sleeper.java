package polis.game.gameLogic.actors;

import polis.game.gameLogic.tiles.Residence;

public class Sleeper extends Inhabitant{
    public Sleeper(int r, int c, int age, float cellSize, Residence house, int inhabNum) {
        super(r, c, age, "Sleeper", cellSize, house, r, c, inhabNum);
    }
}
