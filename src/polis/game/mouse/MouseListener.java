package polis.game.mouse;

import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;

public class MouseListener extends Pane {
    private final MouseModel mouseEvents;

    public MouseListener(MouseModel mouseEvents){
        this.mouseEvents = mouseEvents;

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
