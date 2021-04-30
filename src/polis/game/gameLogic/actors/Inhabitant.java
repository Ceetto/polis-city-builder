package polis.game.gameLogic.actors;

import polis.game.gameLogic.tiles.Residence;

public class Inhabitant extends Actor{

    private final Residence house;
    private final int houseR;
    private final int houseC;
    private final int inhabNum;

    Inhabitant(int r, int c, int age, String type, float cellSize, Residence house, int houseR, int houseC, int inhabNum) {
        super(r, c, age, type, cellSize);
        this.house = house;
        this.houseR = houseR;
        this.houseC = houseC;
        this.inhabNum = inhabNum;
    }

    public Residence getHouse(){
        return house;
    }

    public int getHouseR() {
        return houseR;
    }

    public int getHouseC() {
        return houseC;
    }

    public int getInhabNum(){
        return inhabNum;
    }
}
