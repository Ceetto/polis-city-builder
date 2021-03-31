package polis.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import polis.panes.buttons.BuildButton;

import java.io.FileNotFoundException;

public class Buttons extends GridPane {

    Button resident = new BuildButton("residence.png", "lbuild", "res", this);
    Button industry = new BuildButton("industry.png", "lbuild", "ind", this);
    Button commerce = new BuildButton("commerce.png", "lbuild", "com", this);
    Button road = new BuildButton("road.png", "sbuild", "road", this);
    Button buildozer = new BuildButton("bulldozer.png", "del", "del", this);
    Button select = new BuildButton("selection.png", "select", "sel", this);

    String cstatus = "select";
    String bstatus;

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

    public void setStatus(String cstatus, String bstatus){
        this.cstatus = cstatus;
        this.bstatus = bstatus;
    }

    public String getCstatus(){
        return cstatus;
    }

    public String getBstatus(){
        return bstatus;
    }
}
