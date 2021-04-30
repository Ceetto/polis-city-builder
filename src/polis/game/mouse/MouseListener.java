package polis.game.mouse;

import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;

/**
 * luistert naar de mouse events en geeft dit door aan het mouseModel
 */
public class MouseListener extends Pane {

    public MouseListener(MouseModel mouseEvents){

        setOnMouseMoved(mouseEvents::cursorMoved);

        setOnMouseClicked(e -> {
            try {
                mouseEvents.clicked();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });


        setOnDragDetected(e -> startFullDrag());

        setOnMouseDragEntered(mouseEvents::dragStart);
        setOnMouseDragged(mouseEvents::dragging);
        setOnMouseDragExited(e -> {
            try {
                mouseEvents.dragEnd();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

    }
}
