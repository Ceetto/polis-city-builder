package polis.game.gameLogic.actors;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import polis.game.gameLogic.actorsLogic.ActorsModel;
import polis.game.gameLogic.tiles.BuildingTile;
import polis.game.gameLogic.tiles.Commerce;
import polis.game.gameLogic.tiles.Residence;

import java.util.ArrayList;
import java.util.Random;


public class Actor extends Circle {

    private int r;
    private int c;
    private int age;
    private String type;

    private ActorsModel.Dirs dir;

    public Actor(int r, int c, int age, String type, float cellSize){
        super(0, cellSize/2, cellSize/6);
        setFill(Paint.valueOf("transparent"));
        setViewOrder(-r - c - 1.5);
        this.r = r;
        this.c = c;
        this.dir = ActorsModel.Dirs.SOUTH;
        this.age = age;
        this.type = type;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public ActorsModel.Dirs getDir(){
        return dir;
    }

    public void setPos(int r, int c){
        this.r = r;
        this.c = c;
        setViewOrder(-r - c - 1.5);
    }

    public void setRandomDir(ArrayList<ActorsModel.Dirs> possibleDirs){
        Random r = new Random();
        if(possibleDirs.size() > 0)
            dir = possibleDirs.get(r.nextInt(possibleDirs.size()));
    }

    public void setDir(ActorsModel.Dirs dir){
        this.dir = dir;
    }

    public void age(){
        age--;
    }

    public int getAge(){
        return age;
    }

    public String getType() {
        return type;
    }

    public Residence getHouse(){
        return null;
    }

    public int getHouseR() {
        return 0;
    }

    public int getHouseC() {
        return 0;
    }

    public BuildingTile getLocation(){
        return null;
    }

    public int getInhabNum(){
        return 0;
    }

}
