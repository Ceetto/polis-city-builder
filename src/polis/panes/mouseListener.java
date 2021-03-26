package polis.panes;

import javafx.scene.layout.Pane;

public class mouseListener extends Pane {
    Game game;

    public mouseListener(Game game){
        this.game = game;

        setOnMouseMoved(game::cursorMoved);
    }
}
