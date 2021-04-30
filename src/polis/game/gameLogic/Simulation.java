package polis.game.gameLogic;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import polis.game.Statistics;
import polis.game.gameLogic.actorsLogic.ActorsModel;
import polis.game.gameLogic.tiles.BuildingTile;

import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class Simulation {

    private Random r = new Random();

    private final ActorsModel actorsModel;
    private final Statistics stats;

    private final Timeline t;

    protected Properties engineProps = new Properties();
    private int spawnRate;
    private int currentRate;

    private int tick = 0;

    public Simulation(ActorsModel actorsModel, Statistics stats){
        this.actorsModel = actorsModel;
        this.stats = stats;
        this.t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(250), this::simulationStep));

        try(InputStream in2 = BuildingTile.class.getResourceAsStream("/polis/properties/engine.properties")){
            engineProps.load(in2);
        } catch (Exception e){
            e.printStackTrace();
        }

        this.spawnRate = Integer.parseInt(engineProps.getProperty("region.initial.rate"));
        this.currentRate = r.nextInt(spawnRate)+1;
    }

    public void simulationStep(ActionEvent event){
        actorsModel.actActors();
        stats.updateStats();
        if(tick%currentRate == 0){
            actorsModel.addImigrant();
            this.spawnRate = actorsModel.getSpawnrate();
            currentRate = r.nextInt(spawnRate+1)+1;
        }
        tick++;
    }

    public void stopSimulation(){
        t.stop();
    }

    public void playSimulation(){
        t.play();
    }
}
