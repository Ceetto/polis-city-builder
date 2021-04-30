package polis.game;

import javafx.geometry.Pos;
import polis.game.gameLogic.Drawer;
import polis.game.gameLogic.tiles.Tile;

/**
 * De grond van het spel. Dit is puur visueel.
 */
public class Field extends Drawer {
    public Field(int DIM) {
        super(DIM);
        setAlignment(Pos.TOP_CENTER);

        //dit zou efficienter gaan door 1 grote polygon te maken, maar aangeien ik met custom ground tiles werk leek dit
        //me een betere oplossing. Zolang het veld niet te groot is zorgt dit niet voor lag.
        for(int r = 0; r<DIM; r++){
            for(int c = 0; c<DIM; c++){
                Tile groundTile = new Tile(r, c);
                groundTile.setGroundImage();
                addTile(groundTile);
            }
        }
    }
}
