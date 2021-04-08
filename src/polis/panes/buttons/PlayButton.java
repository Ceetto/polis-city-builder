package polis.panes.buttons;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.panes.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class PlayButton extends ToggleButton {

    private final Queue<String> pics;
    private Game game;

    public PlayButton(Game game) throws FileNotFoundException {
        this.game = game;

        setMaxSize(50,60);
        setFocusTraversable(false);
        setGraphic(new ImageView(new Image("/polis/buttons/play.png")));

        this.pics = new LinkedList<>();
        pics.add("pause.png");
        pics.add("play.png");

        setOnAction(e -> {
            try {
                clicked();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
    }

    public void clicked() throws FileNotFoundException {
        String next = pics.poll();
        setGraphic(new ImageView(new Image("/polis/buttons/" + next)));
        pics.add(next);
    }

}
