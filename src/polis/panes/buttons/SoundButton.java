package polis.panes.buttons;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.Sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class SoundButton extends ToggleButton {

    Sound sound;

    private final Queue<String> pics;

    public SoundButton(Sound sound) throws FileNotFoundException {
        this.pics = new LinkedList<>();
        this.sound = sound;

        setMaxSize(50,60);
        setFocusTraversable(false);
        setGraphic(new ImageView(new Image("/polis/buttons/on.png")));

        pics.add("mute.png");
        pics.add("on.png");


        setOnAction(e -> {
            try {
                clicked();
                sound.mute();
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

    public Sound getSound() {
        return sound;
    }
}
