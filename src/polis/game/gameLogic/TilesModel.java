package polis.game.gameLogic;

import polis.Sound;
import polis.game.Statistics;
import polis.game.gameLogic.tiles.*;
import java.util.Arrays;
import java.util.Map;

/**
 * Model die alle data bevat rond waar welke tiles zijn.
 */
public class TilesModel{
    final Map<String, String> buildingTypes = Map.of(
            "res", "residence-0.png",
            "ind", "industry-0.png",
            "com", "commerce-0.png"
    );

    private final GameView gameView;
    private final int[][] usedTiles; //tiles in gebruik
    private final RoadTile[][] roadsPlaced; //alle roads
    private final BuildingTile[][] buildingsPlaced; //alle buildings
    private final Sound sound;
    private final Statistics stats;

    public TilesModel(Sound sound, GameView gameView, Statistics stats){
        this.stats = stats;
        this.sound = sound;
        this.gameView = gameView;
        this.usedTiles  = new int[gameView.getDIM()][gameView.getDIM()];
        this.buildingsPlaced  = new BuildingTile[gameView.getDIM()][gameView.getDIM()];
        for(BuildingTile[] row:buildingsPlaced) {
            Arrays.fill(row, null);
        }
        this.roadsPlaced = new RoadTile[gameView.getDIM()][gameView.getDIM()];
        for(RoadTile[] row: roadsPlaced) {
            Arrays.fill(row, null);
        }
    }

    /**
     * voegt een road of building tile toe.
     */
    public void addTile(String status, int r, int c, boolean unbreakable){
        if (r >= 0 && r < gameView.getDIM() && c >= 0 && c < gameView.getDIM()) {
            if (status.equals("road")) {
                if (spotFree(r, c, 1)) {
                    RoadTile newTile = new RoadTile(r, c, 0, unbreakable);
                    setSpot(r, c, 1, 1);
                    roadsPlaced[r][c] = newTile;
                    gameView.addTile(newTile);
                    updateRoadsLevel(r,c);
                }
            } else {
                if (spotFree(r, c, 2)) {
                    BuildingTile newTile = makeBuilding(r, c, status);
                    gameView.addTile(newTile);
                    setSpot(newTile.getR(), newTile.getC(), 2, 1);
                    setBuildingSpot(newTile.getR(), newTile.getC(), newTile);
                }
            }
        }
    }

    /**
     * geeft een nieuwe building op plaats r, c terug van een bepaald type.
     */
    public BuildingTile makeBuilding(int r, int c, String status){
        Map<String, BuildingTile> buildings = Map.of(
                "res", new Residence(buildingTypes.get(status), r, c, this),
                "com", new Commerce(buildingTypes.get(status), r, c, this),
                "ind", new Industry(buildingTypes.get(status), r, c, this)
        );
        buildings.get(status).init();
        return buildings.get(status);
    }

    /**
     * verwijdert een tile.
     */
    public void removeTile(int r, int c){
        if(roadsPlaced[r][c] != null && !roadsPlaced[r][c].isUnbreakable()){
            roadsPlaced[r][c].deleteRoad(gameView);
            roadsPlaced[r][c] = null;
            setSpot(r,c,1,0);
            updateRoadsLevel(r, c);
            sound.delete();
        }
        if(buildingsPlaced[r][c] != null){
            buildingsPlaced[r][c].deleteBuilding(gameView);
            BuildingTile tile = buildingsPlaced[r][c];
            setBuildingSpot(tile.getR(), tile.getC(), null);
            setSpot(tile.getR(), tile.getC(), 2, 0);
            sound.delete();
        }
    }

    /**
     * Methodes om het level van een building te verhogen/verlagen.
     */
    public void levelUpTile(int r, int c){
        BuildingTile evolved = buildingsPlaced[r][c];
        if(evolved != null) {
            evolved.levelUp();
            levelUpDown(r, c, evolved);
        }
    }
    public void levelDownTile(int r, int c){
        BuildingTile devolved = buildingsPlaced[r][c];
        if(devolved != null) {
            devolved.levelDown();
            levelUpDown(r, c, devolved);
        }
    }
    private void levelUpDown(int r, int c, BuildingTile newTile){
        buildingsPlaced[r][c].deleteBuilding(gameView);
        BuildingTile tile = buildingsPlaced[r][c];
        setBuildingSpot(tile.getR(), tile.getC(), null);
        setBuildingSpot(tile.getR(), tile.getC(), newTile);
        newTile.init();
        sound.upgrade();
        gameView.addTile(newTile);
    }

    /**
     * Update het uiterlijk van straten rond een bepaalde straat
     */
    public void updateRoadsLevel(int r, int c){
        for(int i = r-1; i <= r+1 && i < gameView.getDIM(); i++){
            for(int j = c-1; j <= c+1 && j < gameView.getDIM(); j++){
                if(i < 0)
                    i=0;
                if(j<0)
                    j=0;
                if(roadsPlaced[i][j] != null)
                    roadsPlaced[i][j].updateLevel(roadsPlaced, gameView.getDIM());
            }
        }
    }

    public boolean spotFree(int r, int c, int size){
        boolean free = true;
        for (int i = 0; i < size; i++){
            for (int j = 0; j< size; j++){
                if (usedTiles[r + i][c + j] == 1) {
                    free = false;
                    break;
                }
            }
        }
        return free;
    }

    public void setSpot(int r, int c, int size, int val){
        for (int i = 0; i < size; i++){
            for (int j = 0; j< size; j++){
                usedTiles[r+i][c+j] = val;
            }
        }
    }

    public void setBuildingSpot(int r, int c, BuildingTile tile){
        for (int i = 0; i < 2; i++){
            for (int j = 0; j< 2; j++){
                buildingsPlaced[r+i][c+j] = tile;
            }
        }
    }

    public RoadTile[][] getRoadsPlaced(){
        return roadsPlaced;
    }

    public BuildingTile[][] getBuildingsPlaced(){
        return buildingsPlaced;
    }

    public Statistics getStats(){
        return stats;
    }
}
