package polis.panes;

import javafx.scene.input.MouseEvent;
import java.io.FileNotFoundException;
import java.util.*;

public class Game extends Drawer {

    Buttons buttons;
    Tiles tiles = new Tiles();
    Cursor cursor = new Cursor(tiles);

    public Game(Buttons buttons) throws FileNotFoundException {
        setPrefSize(CELL_SIZE*2*DIM,CELL_SIZE*DIM);

        getChildren().add(new Field());
        getChildren().add(tiles);
        getChildren().add(cursor);
        getChildren().add(new mouseListener(this));

        this.buttons = buttons;

        for(int i = 0; i < DIM/2; i++){
            tiles.addTile("road", i, DIM/2-1, true);
        }

    }

    public void cursorMoved(MouseEvent e){
        cursor.mouseMoved(e, buttons.getCstatus());
    }

    public void clicked() throws FileNotFoundException {
        if (!dragging) {
            if (buttons.getCstatus().equals("sbuild") || buttons.getCstatus().equals("lbuild")) {
                tiles.addTile(buttons.getBstatus(), cursor.getRow(), cursor.getColumn(), false);
            } else if (buttons.getCstatus().equals("del")) {
                tiles.removeTile(cursor.getRow(), cursor.getColumn());
            } else if(buttons.getCstatus().equals("select")){
                tiles.levelUpTile(cursor.getRow(), cursor.getColumn());
            }
        }
    }


    int sdr;
    int sdc;
    List<Coord> coords = new ArrayList<>();
    boolean dragging = false;

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
        sdr = (int) (2 * e.getY() - e.getX() + getWidth() / 2) / (2 * CELL_SIZE);
        sdc = (int) (e.getX() + 2 * e.getY() - getWidth() / 2) / (2 * CELL_SIZE);
        dragging = true;
    }

    public void dragging(MouseEvent e){
        int er = (int) (2*e.getY() - e.getX() + getWidth()/2) / (2 * CELL_SIZE);
        int ec = (int) (e.getX() + 2*e.getY() - getWidth()/2) / (2 * CELL_SIZE);

        if (sdr >= 0 && sdr < DIM && sdc >= 0 && sdc < DIM && er >= 0 && er < DIM && ec >= 0 && ec < DIM && (
                buttons.getCstatus().equals("sbuild") || buttons.getCstatus().equals("lbuild")) && dragging) {

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
                for (int dr = Integer.signum(rdist)*size; Math.abs(dr) <= Math.abs(rdist); dr += Integer.signum(rdist)*size) {
                    coords.add(new Coord(r, c));
                    r = sdr + dr;
                }
            }

            if (Math.signum(cdist) != 0) {
                for (int dc = Integer.signum(cdist)*size; Math.abs(dc) <= Math.abs(cdist)+size; dc += Integer.signum(cdist)*size) {
                    coords.add(new Coord(r, c));
                    c = sdc + dc;
                }
            } else {
                coords.add(new Coord(r, c));
            }

            cursor.drag(coords, size);
        }
    }

    public void dragEnd() throws FileNotFoundException {
        if (buttons.getCstatus().equals("sbuild") || buttons.getCstatus().equals("lbuild")) {
            for (Coord tile : coords) {
                tiles.addTile(buttons.getBstatus(), tile.getR(), tile.getC(), false);
            }
        }
        dragging = false;
    }
}
