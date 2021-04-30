package polis.game.mouse;

import javafx.scene.input.MouseEvent;
import polis.game.gameLogic.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Dit model dient om alle mouse events te handlen en dan de betrokken views te verwittigen.
 * De reden dat ik hier niet expliciet met listeners werk is zodat steeds enkel de views waar iets aan veranderd moet
 * worden verwittigt.
 */
public class MouseModel {

    private final Game game;

    public MouseModel(Game game){
        this.game = game;
    }

    public void cursorMoved(MouseEvent e){
        game.getGameCursor().mouseMoved(e, game.getButtons().getCstatus());
    }

    public void clicked() {
        if (!dragging && game.getGameCursor().getRow() >= 0 && game.getGameCursor().getRow() < game.getDIM()
        && game.getGameCursor().getColumn() >= 0 && game.getGameCursor().getColumn() < game.getDIM()) {
            switch (game.getButtons().getCstatus()) {
                case "sbuild":
                    game.getGameView().addTile(game.getButtons().getBstatus(), game.getGameCursor().getRow(), game.getGameCursor().getColumn(), false);
                    game.getSound().build();
                    break;
                case "lbuild":
                    int cr = 0;
                    int cc = 0;

                    //randgevallen zodat grote tiles nog altijd juist verschijnen aan de rand van het veld
                    if(game.getGameCursor().getRow() == game.getDIM() -1)
                        cr = 1;
                    if(game.getGameCursor().getColumn() == game.getDIM() -1)
                        cc = 1;

                    game.getGameView().addTile(game.getButtons().getBstatus(), game.getGameCursor().getRow()-cr, game.getGameCursor().getColumn()-cc, false);
                    game.getSound().build();
                    break;
                case "del":
                    game.getGameView().removeTile(game.getGameCursor().getRow(), game.getGameCursor().getColumn());
                    break;
                case "select":
                    game.getStats().setSelected(game.getTilesModel().getBuildingsPlaced()[game.getGameCursor().getRow()][game.getGameCursor().getColumn()]);
                    break;
            }
        }
    }

    private int sdr; //start drag row
    private int sdc; //start drag column
    private final List<Coord> coords = new ArrayList<>();
    private boolean dragging = false;

    static class Coord{
        private final int r;
        private final int c;

        Coord(int r, int c){
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
            sdr = (int) (2 * e.getY() - e.getX() + game.getWidth() / 2) / (2 * game.getCELL_SIZE());
            sdc = (int) (e.getX() + 2 * e.getY() - game.getWidth() / 2) / (2 * game.getCELL_SIZE());
            dragging = true;
        }
    }

    public void dragging(MouseEvent e){
        int er = (int) (2*e.getY() - e.getX() + game.getWidth()/2) / (2 * game.getCELL_SIZE()); //end row
        int ec = (int) (e.getX() + 2*e.getY() - game.getWidth()/2) / (2 * game.getCELL_SIZE()); // end column

        int size;
        if (game.getButtons().getCstatus().equals("lbuild")) {
            size = 2;
        } else {
            size = 1;
        }

        //change column and row for edgecase
        int cr = 0;
        int cc = 0;
        if(game.getGameCursor().getRow() == game.getDIM() -1)
            cr = size-1;
        if(game.getGameCursor().getColumn() == game.getDIM() -1)
            cc = size-1;

        if (sdr >= 0 && sdr < game.getDIM() && sdc >= 0 && sdc < game.getDIM() && er >= 0 && er < game.getDIM()-size+1 && ec >= 0 && ec < game.getDIM()-size+1 && (
                game.getButtons().getCstatus().equals("sbuild") || game.getButtons().getCstatus().equals("lbuild")) && dragging) {

            int r = sdr; //current row
            int c = sdc; //current column

            int rdist = er - sdr; //row distance
            int cdist = ec - sdc; //column distance

            coords.clear();

            if (Math.signum(rdist) != 0) {
                for (int dr = Integer.signum(rdist)*size; Math.abs(dr) <= Math.abs(rdist); dr += Integer.signum(rdist)*size) {
                    coords.add(new Coord(r-cr, c-cc));
                    r = sdr + dr;
                }
            }

            if (Math.signum(cdist) != 0) {
                for (int dc = Integer.signum(cdist)*size; Math.abs(dc) <= Math.abs(cdist)+size; dc += Integer.signum(cdist)*size) {
                    coords.add(new Coord(r-cr, c-cc));
                    c = sdc + dc;
                }
            } else {
                coords.add(new Coord(r-cr, c-cc));
            }
            game.getGameCursor().drag(coords, size);
        }
    }

    public void dragEnd() {
        if (game.getButtons().getCstatus().equals("sbuild") || game.getButtons().getCstatus().equals("lbuild")) {
            for (Coord tile : coords) {
                game.getGameView().addTile(game.getButtons().getBstatus(), tile.getR(), tile.getC(), false);
            }
            for(int i = 0; i < 3; i++){
                game.getSound().build();
            }
        }
        dragging = false;
    }
}
