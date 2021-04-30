package polis.game.gameLogic;

import polis.Sound;
import polis.game.Buttons;
import polis.game.Field;
import polis.game.Statistics;
import polis.game.gameLogic.actorsLogic.ActorsModel;
import polis.game.mouse.Cursor;
import polis.game.mouse.MouseListener;
import polis.game.mouse.MouseModel;

public class Game extends Drawer {

    private final Sound sound;
    private final Buttons buttons;
    private final GameView gameView;
    private final Cursor cursor;
    private final Simulation simulation;
    private final TilesModel tilesModel;
    private final ActorsModel actorsModel;
    private final Statistics stats;

    public Game(Buttons buttons, Sound sound, int DIM, Statistics stats){
        super(DIM);

        setFocusTraversable(true);

        setPrefSize(CELL_SIZE*2*DIM,CELL_SIZE*DIM);

        this.stats = stats;
        this.buttons = buttons;
        this.sound = sound;
        this.gameView = new GameView(DIM);
        this.tilesModel = new TilesModel(sound, gameView, stats);
        this.actorsModel = new ActorsModel(gameView, tilesModel);
        this.cursor = new Cursor(tilesModel, gameView, DIM);
        this.simulation = new Simulation(actorsModel, stats);


        getChildren().add(new Field(DIM));
        getChildren().add(gameView);
        getChildren().add(cursor);
        getChildren().add(new MouseListener(new MouseModel(this)));

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
