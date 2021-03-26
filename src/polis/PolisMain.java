package polis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import polis.panes.PolisPane;

public class PolisMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(new PolisPane(), 1080, 607);

        stage.setScene(scene);

        stage.setTitle("Polis - 2021 © Universiteit Gent");
        stage.show();
    }
}
