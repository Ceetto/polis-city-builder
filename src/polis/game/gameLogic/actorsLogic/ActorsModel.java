package polis.game.gameLogic.actorsLogic;

import polis.game.gameLogic.GameView;
import polis.game.gameLogic.TilesModel;
import polis.game.gameLogic.actors.Actor;
import polis.game.gameLogic.actors.Imigrant;
import polis.game.gameLogic.tiles.BuildingTile;
import polis.game.gameLogic.tiles.RoadTile;

import java.io.InputStream;
import java.util.*;

/**
 * model die data over alle actors bevat.
 */
public class ActorsModel {

    private final List<Actor> actors = new ArrayList<>();
    private List<Actor> gone = new ArrayList<>();
    private List<Actor> newActors = new ArrayList<>();

    private final GameView gameView;
    private final TilesModel tilesModel;

    private final int imigrantAge;

    //richtingen die een actor kan uitgaan
    public enum Dirs {
        NORTH,
        EAST,
        SOUTH,
        WEST;
        private static final Dirs[] vals = values();
        public Dirs right(){
            return vals[(this.ordinal()+1) % vals.length];
        }
        public Dirs left(){ return vals[(this.ordinal()+3) % vals.length]; }
        public Dirs back(){ return vals[(this.ordinal()+2) % vals.length]; }
    }

    //map die het type actor linkt aan een nieuw model die de logica rond deze actor bevat
    private final Map<String, ActActors> acting = new HashMap<>();

    public ActorsModel(GameView gameView, TilesModel tilesModel){
        this.gameView = gameView;
        this.tilesModel = tilesModel;

        acting.put("Immigrant", new ImigrantsModel(this, gameView));
        acting.put("Sleeper", new SleepersModel(this, gameView));
        acting.put("Jobseeker", new JobSeekerModel(this, gameView));
        acting.put("Trader", new TraderModel(this, gameView));
        acting.put("Shopper", new ShopperModel(this, gameView));
        acting.put("Worker", new WorkerModel(this, gameView));
        acting.put("Goods", new GoodsModel(this, gameView));
        acting.put("Client", new ClientModel(this, gameView));

        Properties engineProps = new Properties();
        try(InputStream in2 = BuildingTile.class.getResourceAsStream("/polis/properties/engine.properties")){
            engineProps.load(in2);
        } catch (Exception e){
            e.printStackTrace();
        }

        imigrantAge = Integer.parseInt(engineProps.getProperty("immigrant.age"));
    }

    public void addImigrant(){
        Actor newActor = new Imigrant(0, gameView.getDIM()/2-1, imigrantAge, gameView.getCELL_SIZE());
        gameView.addActor(newActor, gameView);
        actors.add(newActor);
    }

    public void addInhabitant(Actor newActor){
        gameView.addActor(newActor, gameView);
        newActors.add(newActor);
    }

    public void removeActor(Actor actor){
        gone.add(actor);
        gameView.getChildren().remove(actor);
    }

    /**
     * laat alle actors acten.
     */
    public void actActors(){
        gone = new ArrayList<>();
        newActors = new ArrayList<>();
        for (Actor actor : actors) {
            acting.get(actor.getType()).act(actor);

        }
        actors.removeAll(gone);
        actors.addAll(newActors);
    }

    public RoadTile[][] getRoadsPlaced(){
        return tilesModel.getRoadsPlaced();
    }

    public BuildingTile[][] getBuildingsPlaced(){
        return tilesModel.getBuildingsPlaced();
    }

    public int getSpawnRate(){
        return acting.get("Immigrant").getSpawnRate();
    }
}
