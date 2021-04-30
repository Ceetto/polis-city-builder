package polis.game.gameLogic;

import polis.Sound;
import polis.game.Buttons;
import polis.game.Field;
import polis.game.Statistics;
import polis.game.gameLogic.actorsLogic.ActorsModel;
import polis.game.mouse.Cursor;
import polis.game.mouse.MouseListener;
import polis.game.mouse.MouseModel;

/**
 * hoofd-pane van de simulatie. Hierin zitten alle views en modellen van de simulatie. Hierin zitten ook de muis en
 * cursor controllers
 */
public class Game extends Drawer {

    private final Sound sound;
    private final Buttons buttons;
    private final Cursor cursor;
    private final Simulation simulation;
    private final TilesModel tilesModel;
    private final Statistics stats;

    public Game(Buttons buttons, Sound sound, int DIM, Statistics stats){
        super(DIM);
        setFocusTraversable(true);
        setPrefSize(CELL_SIZE*2*DIM,CELL_SIZE*DIM);

        this.stats = stats;
        this.buttons = buttons;
        this.sound = sound;
        GameView gameView = new GameView(DIM);
        this.tilesModel = new TilesModel(sound, gameView, stats);
        ActorsModel actorsModel = new ActorsModel(gameView, tilesModel);
        this.cursor = new Cursor(tilesModel, gameView, DIM);
        this.simulation = new Simulation(actorsModel, stats);

        getChildren().addAll(new Field(DIM), gameView, cursor, new MouseListener(new MouseModel(this)));

        //initiële weg
        for(int i = 0; i < DIM/2; i++){
            tilesModel.addTile("road", i, DIM/2-1, true);
        }
    }

    public Sound getSound() {
        return sound;
    }

    public Buttons getButtons() {
        return buttons;
    }

    public TilesModel getGameView() {
        return tilesModel;
    }

    public Cursor getGameCursor() {
        return cursor;
    }

    public Simulation getSimulation(){
        return simulation;
    }

    public TilesModel getTilesModel() {
        return tilesModel;
    }

    public Statistics getStats(){
        return stats;
    }
}
