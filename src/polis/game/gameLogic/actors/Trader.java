package polis.game.gameLogic.actors;

import polis.game.gameLogic.tiles.Commerce;
import polis.game.gameLogic.tiles.Residence;

public class Trader extends Inhabitant{
    private final Commerce workplace;

    public Trader(int r, int c, int age, float cellSize, Residence house, int houseR, int houseC, Commerce workplace, int inHabnum) {
        super(r, c, age, "Trader", cellSize, house, houseR, houseC, inHabnum);
        this.workplace = workplace;
    }

    public Commerce getLocation() {
        return workplace;
    }
}
