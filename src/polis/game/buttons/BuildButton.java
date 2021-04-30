package polis.game.buttons;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.game.Buttons;

public class BuildButton extends ToggleButton {

    public BuildButton(String picture, String ctype, String btype, Buttons buttons){
        this.setMinSize(70,40);
        this.setFocusTraversable(false);
        Image image = new Image("/polis/buttons/" + picture);
        ImageView imageView = new ImageView(image);
        this.setGraphic(imageView);

        this.setOnAction(e -> {
            buttons.setStatus(ctype, btype);
            buttons.setSelected(this);
        });
    }


}
