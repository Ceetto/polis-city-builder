package polis.game.mouse;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import polis.game.gameLogic.Drawer;
import polis.game.gameLogic.GameView;
import polis.game.gameLogic.TilesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * bevat methoden die de cursor tekenen op het tekenveld (GameView).
 */
public class Cursor extends Drawer {

    private int row;
    private int column;
    private final TilesModel tilesModel;
    private final GameView gameView;
    private final List<Node> cursorTiles = new ArrayList<>();

    public Cursor(TilesModel tilesModel, GameView tiles, int DIM){
        super(DIM);
        setAlignment(Pos.TOP_CENTER);
        setPrefSize(CELL_SIZE*2*DIM,CELL_SIZE*DIM);
        this.tilesModel = tilesModel;
        this.gameView = tiles;
    }

    /**
     * hertekent de cursor elke keer dat de muis beweegt
     */
    public void mouseMoved(MouseEvent e, String status){
        double cx = (e.getX());
        double cy = (e.getY());
        int size;
        if (status.equals("lbuild")){
            size = 2;
        } else {
            size = 1;
        }

        row = (int) (2* cy - cx + getWidth()/2) / (2 * CELL_SIZE);
        column = (int) (cx + 2* cy - getWidth()/2) / (2 * CELL_SIZE);
        if (row >= 0 && row+size-1 < DIM && column >= 0 && column+size-1 < DIM) {

            gameView.getChildren().removeAll(cursorTiles);
            if (status.equals("select")) {
                addPoly(row, column, size, gameView, Color.rgb(0, 0, 0, 0), Color.valueOf("white"), 5.0, this);
            } else if (status.equals("del")) {
                addPoly(row, column, size, gameView, Color.rgb(0, 0, 0, 0), Color.rgb(255, 0, 0, 0.75), 5.0, this);
            } else if (tilesModel.spotFree(row, column, size)) {
                if (status.equals("sbuild") || status.equals("lbuild")) {
                    addPoly(row, column, size, gameView, Color.rgb(0, 127, 255, 0.5), Color.valueOf("white"), 0.0, this);
                }
            } else {
                addPoly(row, column, size, gameView, Color.rgb(255, 0, 0, 0.5), Color.valueOf("white"), 0.0, this);
            }
        }
    }

    /**
     * krijgt een lijst van coördinaten die een drag voorstellen. Deze worden dan afgebeeld.
     */
    public void drag(List<MouseModel.Coord> coords, int size){
        gameView.getChildren().removeAll(cursorTiles);
        for(MouseModel.Coord coord:coords){
            if (tilesModel.spotFree(coord.getR(), coord.getC(), size)) {
                addPoly(coord.getR(), coord.getC(), size, gameView, Color.rgb(0, 127, 255, 0.5), Color.valueOf("white"), 0.0, this);
            } else {
                addPoly(coord.getR(), coord.getC(), size, gameView, Color.rgb(255, 0, 0, 0.5), Color.valueOf("white"), 0.0, this);
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
