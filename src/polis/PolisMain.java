package polis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import polis.panes.PolisPane;

import java.io.File;


public class PolisMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("startScreen/startScreen.fxml"));
        Scene startScreen = new Scene(root);

        Scene scene = new Scene(new PolisPane(), 1080, 607);

        stage.setScene(scene);



        stage.setTitle("Polis - 2021 © Universiteit Gent");
        stage.show();


        Media media = new Media(new File("resources/polis/bgm/bgm-1.mp3").toURI().toString());
        AudioClip player = new AudioClip(media.getSource());
        player.play();
        player.setCycleCount(AudioClip.INDEFINITE);
    }
}
