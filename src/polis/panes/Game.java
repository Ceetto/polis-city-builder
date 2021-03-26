package polis.panes;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;

public class Game extends Drawer {

    Cursor cursor = new Cursor();
    Buttons buttons;

    public Game(Buttons buttons){
        setPrefSize(CELL_SIZE*2*DIM,CELL_SIZE*DIM);

        getChildren().add(new Field());
        getChildren().add(cursor);
        getChildren().add(new mouseListener(this));

        this.buttons = buttons;

    }

    public void cursorMoved(MouseEvent e){
        cursor.mouseMoved(e, buttons.getStatus());
    }
}
