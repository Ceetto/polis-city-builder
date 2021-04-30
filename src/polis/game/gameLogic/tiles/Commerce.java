package polis.game.gameLogic.tiles;

import javafx.scene.layout.Pane;
import polis.game.gameLogic.TilesModel;
import polis.game.gameLogic.actors.Actor;

import java.util.ArrayList;
import java.util.Map;

public class Commerce extends BuildingTile{

    private double workersCapacity;
    private final double clientsPerWorker = Double.parseDouble(engineProps.getProperty("customers.per.trader"));
    private double clientCapacity = Double.parseDouble(engineProps.getProperty("commercial.capacity.initial"));
    private final double minClientCapacity = Double.parseDouble(engineProps.getProperty("commercial.capacity.minimal"));
    private final double maxClientCapacity = Double.parseDouble(engineProps.getProperty("commercial.capacity.maximal"));
    private final double goodsPerCustomer = Double.parseDouble(engineProps.getProperty("goods.per.customer"));
    private double goodsCapacity;
    private int goods = 0;

    private final double goodTrade = Double.parseDouble(engineProps.getProperty("factor.good.trade"));
    private final double badTrade = Double.parseDouble(engineProps.getProperty("factor.bad.trade"));
    private final double lvl1to2 = Double.parseDouble(lvlProps.getProperty("commercial.level1to2"));
    private final double lvl2to1 = Double.parseDouble(lvlProps.getProperty("commercial.level2to1"));
    private final double lvl2to3 = Double.parseDouble(lvlProps.getProperty("commercial.level2to3"));
    private final double lvl3to2 = Double.parseDouble(lvlProps.getProperty("commercial.level3to2"));

    private final ArrayList<Actor> workers = new ArrayList<>();
    private final ArrayList<Actor> clients = new ArrayList<>();

    public Commerce(String picture, int r, int c, TilesModel model) {
        super(picture, r, c, model);
        updateCapacity();
    }

    public void init(){
        addMaxStats(1);
        addStats(1);
    }

    private void updateCapacity(){
        workersCapacity = clientCapacity / clientsPerWorker;
        goodsCapacity = clientCapacity * goodsPerCustomer;
        if(goods > goodsCapacity)
            goods = (int) goodsCapacity;
    }

    public void updateLevel(){
        if(clientCapacity < minClientCapacity){
            clientCapacity = minClientCapacity;
        } else if (clientCapacity > maxClientCapacity) {
            clientCapacity = maxClientCapacity;
        }

        if((level == 0 && workers.size() > 0) ||
                (level == 1 && clientCapacity >= lvl1to2) ||
                (level == 2 && clientCapacity >= lvl2to3)
        ){
            model.levelUpTile(r, c);
        } else if((level == 2 && clientCapacity <= lvl2to1) ||
                level == 3 && clientCapacity <= lvl3to2){
            model.levelDownTile(r, c);
        }
    }

    @Override
    public boolean hasRoom(String option){
        Map<String, Boolean> options = Map.of(
                "worker", workers.size() < Math.floor(workersCapacity),
                "client", clients.size() + 1 <= workers.size() * clientsPerWorker && goods >= clients.size() + 1,
                "goods", goods + 1 <= Math.floor(goodsCapacity)
        );

        if(option.equals("client") && !options.get("client") && (workers.size() < Math.floor(workersCapacity) || goods < clients.size() +1)){
            badTrade();
        }

        return options.get(option);
    }

    public void addWorker(Actor actor){
        workers.add(actor);
        updateLevel();
        model.getStats().addJobs(1);
    }

    public void removeWorker(Actor actor){
        workers.remove(actor);
        model.getStats().addJobs(-1);
    }


    public void addClient(Actor actor){
        clients.add(actor);
        if(clients.size() >= Math.floor(clientCapacity)){
            goodTrade();
        }
        model.getStats().addClients(1);
    }

    public void removeClient(Actor actor){
        clients.remove(actor);
        goods --;
        model.getStats().addClients(-1);
        model.getStats().addGoods(-1);
    }


    public void addGoods(){
        goods ++;
        model.getStats().addGoods(1);
    }

    public void goodTrade(){
        addMaxStats(-1);
        addStats(-1);
        clientCapacity *= goodTrade;
        updateLevel();
        updateCapacity();
        addMaxStats(1);
        addStats(1);
    }

    public void badTrade(){
        addMaxStats(-1);
        addStats(-1);
        clientCapacity *= badTrade;
        updateLevel();
        updateCapacity();
        addMaxStats(1);
        addStats(1);
    }

    public void addMaxStats(int fac){
        model.getStats().addMaxGoods(Math.floor(goodsCapacity)*fac);
        model.getStats().addMaxClients(Math.floor(clientCapacity)*fac);
        model.getStats().addMaxJobs(Math.floor(workersCapacity)*fac);
    }

    public void addStats(int fac){
        model.getStats().addGoods(goods*fac);
        model.getStats().addClients(clients.size()*fac);
        model.getStats().addJobs(workers.size()*fac);
    }

    @Override
    public void deleteBuilding(Pane pane) {
        super.deleteBuilding(pane);
        addMaxStats(-1);
        addStats(-1);
    }
    

    @Override
    public String statsText() {
        return "Jobs: " + workers.size() + " / " + round(workersCapacity) + "\n" +
                "Goods: " + goods + " / " + round(goodsCapacity) + "\n" +
                "Customers: " + clients.size() + " / " + round(clientCapacity);
    }

    @Override
    public String titleText() {
        return "Commercial @ " + r + ":" + c;
    }
}
