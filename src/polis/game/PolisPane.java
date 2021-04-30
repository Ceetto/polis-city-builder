package polis.game;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import polis.game.buttons.PlayButton;
import polis.game.buttons.SoundButton;
import polis.game.gameLogic.Game;
import prog2.util.Viewport;

import java.io.IOException;

/**
 * hoofd-pane van het spel. Dit is de directe parent van de viewport en alle GUI's. Dit is de grandparent van alles in
 * de viewport
 */
public class PolisPane extends Pane {
    final Buttons buttons = new Buttons();
    final SoundButton mute;
    final Statistics stats;

    public PolisPane(SoundButton mute, int DIM) throws IOException {
        setStyle("-fx-background-color: SKYBLUE");

        this.mute = mute;
        mute.setTranslateX(0);
        stats = new Statistics();
        Game game = new Game(buttons, mute.getSound(), DIM, stats);

        Viewport viewport = new Viewport(game, 0.5);
        PlayButton play = new PlayButton(game);

        viewport.setSensitiveMargin(50);
        viewport.setFocusTraversable(true);

        setOnKeyReleased(ke -> {
            if(ke.getCode() == KeyCode.SPACE){
                play.clicked();
            } else {
                buttons.handleKeyReleased(ke);
            }
        });

        setPrefSize(4096, 2048);
        setMinSize(1080, 607);

        viewport.layoutXProperty().bind(this.widthProperty().subtract(viewport.widthProperty()).divide(2));
        viewport.layoutYProperty().bind(this.heightProperty().subtract(viewport.heightProperty()).divide(2));

        getChildren().addAll(viewport, buttons, play, mute, stats);

        widthProperty().addListener((obs, oldval, newval) -> {
            viewport.setPrefWidth((double) newval);
            mute.relocate((double) newval -100 , 10);
            stats.relocate((double) newval - stats.getWidth() - 10, getHeight() - stats.getHeight() - 10);
        });

        heightProperty().addListener((obs, oldval, newval) -> {
            viewport.setPrefHeight((double) newval);
            play.relocate( 10, (double) newval - 50);
            stats.relocate(getWidth() - stats.getWidth() - 10, (double) newval - stats.getHeight() - 10);
        });

    }
}
