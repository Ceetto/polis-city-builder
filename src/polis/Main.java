package polis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import polis.startScreen.StartPane;


public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMinHeight(700);
        stage.setMinWidth(650);
        Scene startScreen = new Scene(new StartPane(stage), 1400, 787.5);
        startScreen.getStylesheets().addAll(getClass().getResource("startScreen/StartPane.css").toExternalForm());
        stage.setScene(startScreen);

        stage.setTitle("Polis - 2021 © Universiteit Gent");
        stage.show();
    }
}
