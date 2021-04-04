package polis.panes;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import polis.panes.buttons.BuildButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Buttons extends GridPane {

    List<BuildButton> buildButtons = new ArrayList<>(List.of(
            new BuildButton("residence.png", "lbuild", "res", this),
            new BuildButton("industry.png", "lbuild", "ind", this),
            new BuildButton("commerce.png", "lbuild", "com", this),
            new BuildButton("road.png", "sbuild", "road", this),
            new BuildButton("bulldozer.png", "del", "del", this),
            new BuildButton("selection.png", "select", "sel", this)
    ));

    String cstatus = "select";
    String bstatus;

    public Buttons() throws FileNotFoundException {

        setAlignment(Pos.TOP_LEFT);
        setPrefSize(100,100);

        setVgap(10.0);
        setHgap(10.0);

        add(buildButtons.get(0), 1,1);
        add(buildButtons.get(1), 2 ,1);
        add(buildButtons.get(2), 3, 1);
        add(buildButtons.get(3),1,2);
        add(buildButtons.get(4), 2, 2);
        add(buildButtons.get(5), 1,3);

    }

    public void setStatus(String cstatus, String bstatus){
        this.cstatus = cstatus;
        this.bstatus = bstatus;
    }

    public void setUnselected(BuildButton selected){
        for(BuildButton button: buildButtons){
            button.setSelected(false);
        }
        selected.setSelected(true);
    }

    public String getCstatus(){
        return cstatus;
    }

    public String getBstatus(){
        return bstatus;
    }
}
