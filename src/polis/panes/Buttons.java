package polis.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import polis.panes.custom.BuildButton;

import java.io.FileNotFoundException;

public class Buttons extends GridPane {

    Button resident = new BuildButton("residence.png", "lbuild", this);
    Button industry = new BuildButton("industry.png", "lbuild", this);
    Button commerce = new BuildButton("commerce.png", "lbuild", this);
    Button road = new BuildButton("road.png", "sbuild", this);
    Button buildozer = new BuildButton("bulldozer.png", "del", this);
    Button select = new BuildButton("selection.png", "select", this);

    String status = "select";

    public Buttons() throws FileNotFoundException {
        setAlignment(Pos.TOP_LEFT);
        setPrefSize(100,100);

        setVgap(10.0);
        setHgap(10.0);

        add(resident, 1,1);
        add(industry, 2 ,1);
        add(commerce, 3, 1);
        add(road,1,2);
        add(buildozer, 2, 2);
        add(select, 1,3);

    }

    public void setStatus(String satus){
        this.status = satus;
    }

    public String getStatus(){
        return status;
    }
}
