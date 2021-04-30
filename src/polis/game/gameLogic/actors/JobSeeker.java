package polis.game.gameLogic.actors;

import javafx.scene.paint.Paint;
import polis.game.gameLogic.tiles.Residence;

public class JobSeeker extends Inhabitant{
    public JobSeeker(int r, int c, int age, float cellSize, Residence house, int inhabNum) {
        super(r, c, age, "Jobseeker", cellSize, house, r, c, inhabNum);
        setFill(Paint.valueOf("peru"));
    }
}
