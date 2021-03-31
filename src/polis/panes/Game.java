package polis.panes;

import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Drawer {


    Buttons buttons;
    Tiles tiles = new Tiles();
    Cursor cursor = new Cursor(tiles);

    public Game(Buttons buttons){
        setPrefSize(CELL_SIZE*2*DIM,CELL_SIZE*DIM);

        getChildren().add(new Field());
        getChildren().add(tiles);
        getChildren().add(cursor);
        getChildren().add(new mouseListener(this));

        this.buttons = buttons;

    }

    public void cursorMoved(MouseEvent e){
        cursor.mouseMoved(e, buttons.getCstatus());
    }

    public void clicked() throws FileNotFoundException {
        if(buttons.getCstatus().equals("sbuild") || buttons.getCstatus().equals("lbuild")) {
            tiles.addTile(buttons.getBstatus(), cursor.getRow(), cursor.getColumn());
            tiles.drawTiles();
        } else if(buttons.getCstatus().equals("del")){
            tiles.removeTile(cursor.getRow(), cursor.getColumn());
        }
    }


    int sdr;
    int sdc;
    List<Coord> coords = new ArrayList<>();

    static class Coord{
        private final int r;
        private final int c;

        public Coord(int r, int c){
            this.r = r;
            this.c = c;
        }

        public int getR() {
            return r;
        }

        public int getC() {
            return c;
        }
    }


    public void dragStart(MouseEvent e){
        sdr = (int) (2*e.getY() - e.getX() + getWidth()/2) / (2 * CELL_SIZE);
        sdc = (int) (e.getX() + 2*e.getY() - getWidth()/2) / (2 * CELL_SIZE);
    }

    public void dragging(MouseEvent e){
        int er = (int) (2*e.getY() - e.getX() + getWidth()/2) / (2 * CELL_SIZE);
        int ec = (int) (e.getX() + 2*e.getY() - getWidth()/2) / (2 * CELL_SIZE);

        if (sdr >= 0 && sdr < DIM && sdc >= 0 && sdc < DIM && er >= 0 && er < DIM && ec >= 0 && ec < DIM) {

            int size;
            if (buttons.getCstatus().equals("lbuild")) {
                size = 2;
            } else {
                size = 1;
            }


            int r = sdr;
            int c = sdc;

            int rdist = er - sdr;
            int cdist = ec - sdc;

            coords.clear();

            if (Math.signum(rdist) != 0) {
                for (int dr = 0; Math.abs(dr) <= Math.abs(rdist); dr += Math.signum(rdist)*size) {
                    coords.add(new Coord(r, c));
                    r = sdr + dr;
                }
            }

            if (Math.signum(cdist) != 0) {
                for (int dc = 0; Math.abs(dc) <= Math.abs(cdist); dc += Math.signum(cdist)*size) {
                    coords.add(new Coord(r, c));
                    c = sdc + dc;
                }
            }

            cursor.drag(coords, size);
        }
    }

    public void dragEnd(MouseEvent e) throws FileNotFoundException {
        System.out.println(coords);
        for (Coord coord:coords){
            System.out.println(coord.getR() + ", " + coord.getC());
        }

        if (buttons.getCstatus().equals("sbuild") || buttons.getCstatus().equals("lbuild")) {
            for (Coord tile : coords) {
                tiles.addTile(buttons.getBstatus(), tile.getR(), tile.getC());
            }
        }
        tiles.drawTiles();
    }
}
