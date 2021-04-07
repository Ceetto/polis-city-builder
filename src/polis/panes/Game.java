package polis.panes;

import javafx.scene.input.MouseEvent;
import polis.Sound;

import java.io.FileNotFoundException;
import java.util.*;

public class Game extends Drawer {

    Buttons buttons;
    Tiles tiles = new Tiles();
    Cursor cursor = new Cursor(tiles);
    //Sound sound = new Sound();

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
            switch (buttons.getCstatus()) {
                case "sbuild":
                    tiles.addTile(buttons.getBstatus(), cursor.getRow(), cursor.getColumn(), false);
                    //sound.build();
                    break;
                case "lbuild":
                    int cr = 0;
                    int cc = 0;
                    if(cursor.getRow() == DIM-1)
                        cr = 1;
                    if(cursor.getColumn() == DIM-1)
                        cc = 1;
                    tiles.addTile(buttons.getBstatus(), cursor.getRow()-cr, cursor.getColumn()-cc, false);
                    //sound.build();
                    break;
                case "del":
                    tiles.removeTile(cursor.getRow(), cursor.getColumn());
                    break;
                case "select":
                    tiles.levelUpTile(cursor.getRow(), cursor.getColumn());
                    break;
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
        if(!dragging) {
            sdr = (int) (2 * e.getY() - e.getX() + getWidth() / 2) / (2 * CELL_SIZE);
            sdc = (int) (e.getX() + 2 * e.getY() - getWidth() / 2) / (2 * CELL_SIZE);
            dragging = true;
        }
    }

    public void dragging(MouseEvent e){
        int er = (int) (2*e.getY() - e.getX() + getWidth()/2) / (2 * CELL_SIZE);
        int ec = (int) (e.getX() + 2*e.getY() - getWidth()/2) / (2 * CELL_SIZE);

        int size;
        if (buttons.getCstatus().equals("lbuild")) {
            size = 2;
        } else {
            size = 1;
        }

        if (sdr >= 0 && sdr < DIM && sdc >= 0 && sdc < DIM && er >= 0 && er < DIM-size+1 && ec >= 0 && ec < DIM-size+1 && (
                buttons.getCstatus().equals("sbuild") || buttons.getCstatus().equals("lbuild")) && dragging) {

            int r = sdr;
            int c = sdc;

            int rc = 0;
            int cc = 0;
            if(er == DIM-1)
                rc = 1;
            if(ec == DIM-1)
                cc = 1;

            int rdist = er - sdr;
            int cdist = ec - sdc;

            coords.clear();

            if (Math.signum(rdist) != 0) {
                for (int dr = Integer.signum(rdist)*size; Math.abs(dr) <= Math.abs(rdist); dr += Integer.signum(rdist)*size) {
                    coords.add(new Coord(r-rc, c-cc));
                    r = sdr + dr;
                }
            }

            if (Math.signum(cdist) != 0) {
                for (int dc = Integer.signum(cdist)*size; Math.abs(dc) <= Math.abs(cdist)+size; dc += Integer.signum(cdist)*size) {
                    coords.add(new Coord(r-rc, c-cc));
                    c = sdc + dc;
                }
            } else {
                coords.add(new Coord(r-rc, c-cc));
            }

            cursor.drag(coords, size);
        }
    }

    public void dragEnd() throws FileNotFoundException {
        if (buttons.getCstatus().equals("sbuild") || buttons.getCstatus().equals("lbuild")) {
            for (Coord tile : coords) {
                tiles.addTile(buttons.getBstatus(), tile.getR(), tile.getC(), false);
            }
            for(int i = 0; i < 3; i++){
                //sound.build();
            }
        }
        dragging = false;
    }
}
