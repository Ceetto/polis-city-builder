package polis.panes;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import polis.panes.custom.PlayButton;
import prog2.util.Viewport;

import java.io.IOException;

public class PolisPane extends Pane {
    Buttons buttons = new Buttons();
    Game game = new Game(buttons);
    Viewport viewport = new Viewport(game, 0.5);
    Button play = new PlayButton();

    public PolisPane() throws IOException {
        setStyle("-fx-background-color: beige");

        viewport.setSensitiveMargin(50);
        viewport.setFocusTraversable(true);
        buttons.setFocusTraversable(false);

        setPrefSize(4096, 2048);
        setMinSize(1080, 607);

        viewport.layoutXProperty().bind(this.widthProperty().subtract(viewport.widthProperty()).divide(2));
        viewport.layoutYProperty().bind(this.heightProperty().subtract(viewport.heightProperty()).divide(2));

        getChildren().addAll(viewport, buttons, play);

        widthProperty().addListener((obs, oldval, newval) -> {
            viewport.setPrefWidth((double) newval);
        });

        heightProperty().addListener((obs, oldval, newval) -> {
            viewport.setPrefHeight((double) newval);
            play.relocate( 10, (double) newval - 50);
        });

    }
}
