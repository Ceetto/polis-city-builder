package polis.panes.buttons;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.panes.Buttons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BuildButton extends ToggleButton {

    String ctype;
    String btype;

    public BuildButton(String picture, String ctype, String btype, Buttons buttons) throws FileNotFoundException {
        this.ctype = ctype;
        this.btype = btype;
        this.setMinSize(70,40);
        this.setFocusTraversable(false);
        FileInputStream input = new FileInputStream("resources/polis/buttons/" + picture);
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        this.setGraphic(imageView);

        this.setOnAction(e -> {
            buttons.setStatus(ctype, btype);
            buttons.setUnselected(this);
        });
    }


}
