package polis.game;

import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import polis.game.buttons.BuildButton;

import java.util.ArrayList;
import java.util.List;

public class Buttons extends GridPane {

    private final List<BuildButton> buildButtons = new ArrayList<>(List.of(
            new BuildButton("residence.png", "lbuild", "res", this),
            new BuildButton("industry.png", "lbuild", "ind", this),
            new BuildButton("commerce.png", "lbuild", "com", this),
            new BuildButton("road.png", "sbuild", "road", this),
            new BuildButton("bulldozer.png", "del", "del", this),
            new BuildButton("selection.png", "select", "sel", this)
    ));

    private String cstatus = "select";
    private String bstatus;

    public Buttons() {

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

        buildButtons.get(5).setSelected(true);
    }

    public void setStatus(String cstatus, String bstatus){
        this.cstatus = cstatus;
        this.bstatus = bstatus;
    }

    public void setSelected(BuildButton selected){
        for(BuildButton button: buildButtons){
            button.setSelected(false);
        }
        selected.setSelected(true);
    }

    public void handleKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getText()) {
            case "r":
                setStatus("lbuild", "res");
                setSelected(buildButtons.get(0));
                break;
            case "i":
                setStatus("lbuild", "ind");
                setSelected(buildButtons.get(1));
                break;
            case "c":
                setStatus("lbuild", "com");
                setSelected(buildButtons.get(2));
                break;
            case "s":
                setStatus("sbuild", "road");
                setSelected(buildButtons.get(3));
                break;
            case "b":
                setStatus("del", "del");
                setSelected(buildButtons.get(4));
                break;
        }

        if(keyEvent.getCode() == KeyCode.ESCAPE){
            setStatus("select", "sel");
            setSelected(buildButtons.get(5));
        }
    }

    public String getCstatus(){
        return cstatus;
    }

    public String getBstatus(){
        return bstatus;
    }
}
