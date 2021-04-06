package polis.panes;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Cursor extends Drawer {

    private int row;
    private int column;
    private double cx;
    private double cy;
    private final Tiles tiles;

    private final List<Node> cursorTiles = new ArrayList<>();


    public Cursor(Tiles tiles){
        setAlignment(Pos.TOP_CENTER);

        setPrefSize(CELL_SIZE*2*DIM,CELL_SIZE*DIM);

        this.tiles = tiles;
    }


    public void mouseMoved(MouseEvent e, String status){
        cx = (e.getX() );
        cy = (e.getY() );
        int size;
        if (status.equals("lbuild")){
            size = 2;
        } else {
            size = 1;
        }

        row = (int) (2*cy - cx + getWidth()/2) / (2 * CELL_SIZE);
        column = (int) (cx + 2*cy - getWidth()/2) / (2 * CELL_SIZE);
        if (row >= 0 && row+size-1 < DIM && column >= 0 && column+size-1 < DIM) {

            tiles.getChildren().removeAll(cursorTiles);
            if (status.equals("select")) {
                addPoly(row, column, size, tiles, Color.rgb(0, 0, 0, 0), Color.valueOf("white"), 5.0, this);
            } else if (status.equals("del")) {
                addPoly(row, column, size, tiles, Color.rgb(0, 0, 0, 0), Color.rgb(255, 0, 0, 0.75), 5.0, this);
            } else if (tiles.spotFree(row, column, size)) {
                if (status.equals("sbuild") || status.equals("lbuild")) {
                    addPoly(row, column, size, tiles, Color.rgb(0, 127, 255, 0.5), Color.valueOf("white"), 0.0, this);
                }
            } else {
                addPoly(row, column, size, tiles, Color.rgb(255, 0, 0, 0.5), Color.valueOf("white"), 0.0, this);
            }
        }
    }

    public void drag(List<Game.Coord> coords, int size){
        tiles.getChildren().removeAll(cursorTiles);
        for(Game.Coord coord:coords){
            if (tiles.spotFree(coord.getR(), coord.getC(), size)) {
                addPoly(coord.getR(), coord.getC(), size, tiles, Color.rgb(0, 127, 255, 0.5), Color.valueOf("white"), 0.0, this);
            } else {
                addPoly(coord.getR(), coord.getC(), size, tiles, Color.rgb(255, 0, 0, 0.5), Color.valueOf("white"), 0.0, this);
            }
        }
    }



    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public List<Node> getCursorTiles() {
        return cursorTiles;
    }
}
