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
import polis.panes.PolisPane;

import java.io.*;
import java.util.Properties;

public class StartPane extends StackPane {

    Stage window;

    Sound sound = new Sound();

    public StartPane(Stage main) throws FileNotFoundException {
        sound.mainTheme();

        this.window = main;

        setStyle("-fx-background-color: SKYBLUE");

        ImageView title = new ImageView();
        title.setImage(new Image(new FileInputStream("resources/polis/other/titlescreen.png")));

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

        Button start = new Button();
        start.getStyleClass().add("button");
        start.setText("Create World");
        start.setPrefSize(169,40);
        start.setOnAction(e -> {
            try {

                try (OutputStream output = new FileOutputStream("resources/polis/properties/settings.properties")) {
                    Properties prop = new Properties();
                    prop.setProperty("field.size", sizeInput.getText());
                    prop.store(output, null);

                } catch (IOException io) {
                    io.printStackTrace();
                }

                sound.click();
                window.setScene(new Scene(new PolisPane(), 1400, 787.5 ));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        heightProperty().addListener((obs, oldval, newval) -> {
            sizeInput.setTranslateY((double)newval/2 + 115);
            size.setTranslateY((double)newval/2 + 100);
            start.setTranslateY((double)newval - 100);
        });

        widthProperty().addListener((obs, oldval, newval) -> {

        });


        getChildren().addAll(title, sizeInput, size, start);
    }
}
