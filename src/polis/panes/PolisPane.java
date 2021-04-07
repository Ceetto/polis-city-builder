package polis.panes;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import polis.panes.buttons.PlayButton;
import polis.panes.buttons.SoundButton;
import prog2.util.Viewport;

import java.io.IOException;

public class PolisPane extends Pane {
    Buttons buttons = new Buttons();
    SoundButton mute;


    public PolisPane(SoundButton mute) throws IOException {
        setStyle("-fx-background-color: SKYBLUE");


        this.mute = mute;
        Game game = new Game(buttons, mute.getSound());

        Viewport viewport = new Viewport(game, 0.5);
        ToggleButton play = new PlayButton(game);

        viewport.setSensitiveMargin(50);
        viewport.setFocusTraversable(true);
        buttons.setFocusTraversable(false);

        setPrefSize(4096, 2048);
        setMinSize(1080, 607);

        viewport.layoutXProperty().bind(this.widthProperty().subtract(viewport.widthProperty()).divide(2));
        viewport.layoutYProperty().bind(this.heightProperty().subtract(viewport.heightProperty()).divide(2));

        getChildren().addAll(viewport, buttons, play, mute);

        widthProperty().addListener((obs, oldval, newval) -> {
            viewport.setPrefWidth((double) newval);
            mute.relocate((double)newval/2 - 45, 5);
        });

        heightProperty().addListener((obs, oldval, newval) -> {
            viewport.setPrefHeight((double) newval);
            play.relocate( 10, (double) newval - 50);
        });

    }
}
