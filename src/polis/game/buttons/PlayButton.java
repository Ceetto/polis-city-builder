package polis.game.buttons;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.game.gameLogic.Game;

import java.util.LinkedList;
import java.util.Queue;

public class PlayButton extends ToggleButton {

    private final Queue<String> pics;
    private final Game game;
    private boolean playing = false;

    public PlayButton(Game game){
        this.game = game;

        setMaxSize(50,60);
        setFocusTraversable(false);
        setGraphic(new ImageView(new Image("/polis/buttons/play.png")));

        this.pics = new LinkedList<>();
        pics.add("pause.png");
        pics.add("play.png");

        setOnAction(e -> clicked());
    }

    public void clicked() {
        String next = pics.poll();
        setGraphic(new ImageView(new Image("/polis/buttons/" + next)));
        pics.add(next);
        playing = !playing;
        if(playing){
            game.getSimulation().playSimulation();
        } else {
            game.getSimulation().stopSimulation();
        }
    }

}
