package polis.game;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import polis.game.gameLogic.tiles.BuildingTile;

public class Statistics extends Pane {

    Label title = new Label();
    Label stats = new Label();
    BuildingTile selected = null;

    private int inhabs = 0;
    private double maxInhabs = 0.0;
    private int jobs = 0;
    private double maxJobs = 0.0;
    private int goods = 0;
    private double maxGoods = 0.0;
    private int clients = 0;
    private double maxClients = 0.0;

    public Statistics(){
        setStyle("-fx-background-color: white;" +
                " -fx-border-color: black;" +
                "-fx-border-width: 1px");
        setPrefSize(300, 169);
        setWidth(300);
        setHeight(169);

        title.setText("STATISTIEKEN");
        title.setFont(new Font(20));
        stats.setFont(new Font(15));
        title.relocate(10, 5);
        stats.relocate(getWidth()/2 - 75, getHeight()/2 - 30);

        getChildren().addAll(title, stats);
        updateStats();
    }

    public void updateStats(){
        //System.out.println(selected);
        if(selected == null){
            title.setText("STATISTIEKEN");
            stats.setText("Bewoners: " + inhabs + " / " + round(maxInhabs, 1) + "\n" +
                    "Jobs: " + jobs + " / " + round(maxJobs,1) + "\n" +
                    "Goederen: " + goods + " / " + round(maxGoods, 1) + "\n" +
                    "Klanten: " + clients + " / " + round(maxClients, 1)
            );
        } else {
            title.setText(selected.titleText());
            stats.setText(selected.statsText());
        }
    }

    public void addJobs(int num){
        this.jobs += num;
        updateStats();
    }

    public void addInhabs(int num){
        this.inhabs += num;
        updateStats();
    }

    public void addGoods(int num){
        this.goods += num;
        updateStats();
    }

    public void addClients(int num){
        this.clients += num;
        updateStats();
    }

    public void addMaxJobs(double num){
        this.maxJobs += num;
        updateStats();
    }

    public void addMaxInhabs(double num){
        this.maxInhabs += num;
        updateStats();
    }

    public void addMaxGoods(double num){
        this.maxGoods += num;
        updateStats();
    }

    public void addMaxClients(double num){
        this.maxClients += num;
        updateStats();
    }

    public void setSelected(BuildingTile tile){
        selected = tile;
        updateStats();
    }

    private double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
