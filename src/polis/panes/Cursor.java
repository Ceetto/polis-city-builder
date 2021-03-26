package polis.panes;

import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Cursor extends Drawer {

    public Cursor(){
        setAlignment(Pos.TOP_CENTER);
        setPrefSize(CELL_SIZE*2*DIM,CELL_SIZE*DIM);
    }

    public void mouseMoved(MouseEvent e, String status){
        double cx = (e.getX() );
        double cy = (e.getY() );
        int size;
        if (status.equals("lbuild")){
            size = 2;
        } else {
            size = 1;
        }

        int row = (int) (2*cy - cx + getWidth()/2) / (2 * CELL_SIZE);
        int column = (int) (cx + 2*cy - getWidth()/2) / (2 * CELL_SIZE);
        if (row >= 0 && row+size-1 < DIM && column >= 0 && column+size-1 < DIM) {
            this.getChildren().clear();
            if (status.equals("sbuild") || status.equals("lbuild")) {
                addPoly(row, column, size, this, Color.rgb(50, 50, 255, 0.4), Color.valueOf("white"), 0.0);
            } else if (status.equals("select")){
                addPoly(row, column, size, this, Color.rgb(0, 0, 0, 0), Color.valueOf("white"), 5.0);
            } else if (status.equals("del")){
                addPoly(row, column, size, this, Color.rgb(0, 0, 0, 0), Color.valueOf("red"), 5.0);
            }
        }


    }

}
