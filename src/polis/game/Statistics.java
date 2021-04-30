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
    private int maxInhabs = 0;
    private int jobs = 0;
    private int maxJobs = 0;
    private int goods = 0;
    private int maxGoods = 0;
    private int clients = 0;
    private int maxClients = 0;

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
        if(selected == null){
            title.setText("STATISTIEKEN");
            stats.setText("Inhabitants: " + inhabs + " / " + maxInhabs + "\n" +
                    "Jobs: " + jobs + " / " + maxJobs + "\n" +
                    "Goods: " + goods + " / " + maxGoods + "\n" +
                    "Customers: " + clients + " / " + maxClients
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

    public double round(double value) {
        int precision = 2;
        int scale = (int) Math.pow(10, precision);
        return Math.floor(value * scale) / scale;
    }
}
