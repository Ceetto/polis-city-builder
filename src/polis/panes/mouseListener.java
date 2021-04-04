package polis.panes;

import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;

public class mouseListener extends Pane {
    Game game;

    public mouseListener(Game game){
        this.game = game;

        setOnMouseMoved(game::cursorMoved);

        setOnMouseClicked(e -> {
            try {
                game.clicked();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });


        setOnDragDetected(e -> {
            startFullDrag();
        });

        setOnMouseDragEntered(game::dragStart);
        setOnMouseDragged(game::dragging);
        setOnMouseDragExited(e -> {
            try {
                game.dragEnd();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

    }
}
