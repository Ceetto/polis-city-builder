package polis.game.gameLogic.actors;

import polis.game.gameLogic.tiles.Commerce;
import polis.game.gameLogic.tiles.Residence;

public class Client extends Inhabitant{
    private Commerce shop;
    public Client(int r, int c, int age, float cellSize, Residence house, int houseR, int houseC, Commerce shop, int inhabNum) {
        super(r, c, age, "Client", cellSize, house, houseR, houseC, inhabNum);
        this.shop = shop;
    }

    public Commerce getLocation(){
        return shop;
    }
}
