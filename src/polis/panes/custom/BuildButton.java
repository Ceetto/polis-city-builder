package polis.panes.custom;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.panes.Buttons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BuildButton extends Button {

    String type;

    public BuildButton(String picture, String type, Buttons buttons) throws FileNotFoundException {
        this.type = type;
        this.setMinSize(70,70);
        this.setFocusTraversable(false);
        FileInputStream input = new FileInputStream("resources/polis/buttons/" + picture);
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        this.setGraphic(imageView);

        this.setOnAction(e -> {
            buttons.setStatus(type);
        });
    }
}
