package polis.game.gameLogic.actors;

import javafx.scene.paint.Paint;

public class Imigrant extends Actor{
    public Imigrant(int r, int c, int age, float cellSize) {
        super(r, c, age, "Immigrant", cellSize);
        setFill(Paint.valueOf("gray"));
    }
}
