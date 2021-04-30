package polis.startScreen;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import polis.Sound;
import polis.game.PolisPane;
import polis.game.buttons.SoundButton;

/**
 * Title-screen wanneer het programma start. Van hieruit kan de field size ingesteld worden vooraleer het spel start.
 */
public class StartPane extends StackPane {
    final Stage window;
    final Sound sound = new Sound();

    public StartPane(Stage main){
        sound.mainTheme();

        this.window = main;

        setStyle("-fx-background-color: SKYBLUE");

        ImageView title = new ImageView();
        Image image = new Image("/polis/other/titlescreen.png");
        title.setImage(image);

        setAlignment(Pos.TOP_CENTER);
        setPrefSize(1080, 607);
        setMaxSize(1080, 607);

        TextField sizeInput = new TextField();

        sizeInput.setMaxSize(50, 30);
        sizeInput.setPromptText("32");
        sizeInput.setText("32");
        sizeInput.setTranslateX(75);

        Label size = new Label();
        size.setText("Field size: ");
        size.setTranslateX(-50);
        size.getStyleClass().add("outline");

        Label invalid = new Label();
        invalid.setText("INVALID FIELD SIZE");
        invalid.getStyleClass().add("error");
        invalid.setVisible(false);

        SoundButton mute = new SoundButton(sound);

        Button start = new Button();
        start.getStyleClass().add("button");
        start.setText("Create World");
        start.setPrefSize(169,40);
        start.setOnAction(e -> {
            int fieldSize = Integer.parseInt(sizeInput.getText());
            if(fieldSize > 0 && fieldSize <= 69) {
                sound.click();
                window.setScene(new Scene(new PolisPane(mute, fieldSize), window.getWidth(), window.getHeight()));
            } else {
                invalid.setVisible(true);
            }
        });

        heightProperty().addListener((obs, oldval, newval) -> {
            sizeInput.setTranslateY(435);
            size.setTranslateY(420);
            start.setTranslateY(520);
            invalid.setTranslateY(480);
            mute.setTranslateY(10);
        });

        widthProperty().addListener((obs, oldval, newval) -> mute.setTranslateX((double)newval/2-50));

        getChildren().addAll(title, sizeInput, size, start, invalid, mute);
    }
}
