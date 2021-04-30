package polis.game.mouse;

import javafx.scene.layout.Pane;

/**
 * luistert naar de mouse events en geeft dit door aan het mouseModel
 */
public class MouseListener extends Pane {

    public MouseListener(MouseModel mouseEvents){

        setOnMouseMoved(mouseEvents::cursorMoved);

        setOnMouseClicked(e -> mouseEvents.clicked());


        setOnDragDetected(e -> startFullDrag());

        setOnMouseDragEntered(mouseEvents::dragStart);
        setOnMouseDragged(mouseEvents::dragging);
        setOnMouseDragExited(e -> mouseEvents.dragEnd());

    }
}
