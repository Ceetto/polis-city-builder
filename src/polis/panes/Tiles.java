package polis.panes;

import javafx.geometry.Pos;
import polis.panes.tiles.BuildingTile;
import polis.panes.tiles.RoadTile;

import java.io.FileNotFoundException;
import java.util.*;

public class Tiles extends Drawer {

    Map<String, String> buildingTypes = Map.of(
            "res", "residence-0.png",
            "ind", "industry-0.png",
            "com", "commerce-0.png"
    );

    int[][] usedTiles = new int[DIM][DIM];
    int[][] roadsPlaced = new int[DIM][DIM];
    List<RoadTile> roadTiles = new ArrayList<>();
    List<BuildingTile> buildingTiles = new ArrayList<>();

    public Tiles(){
        Collections.sort(roadTiles);
        setAlignment(Pos.TOP_CENTER);
    }

    public void addTile(String status, int r, int k) throws FileNotFoundException {
        if (r >= 0 && r < DIM && k >= 0 && k < DIM) {
            if (status.equals("road")) {
                if (spotFree(r, k, 1)) {
                    setSpot(r, k, 1, 1);
                    roadsPlaced[r][k] = 1;
                    RoadTile newTile = new RoadTile(r, k, 0);
                    roadTiles.add(newTile);
                    addRoadTile(newTile, this);
                    newTile.updateLevel(roadsPlaced, DIM);

                    for(RoadTile tile:roadTiles){
                        if(Math.abs(newTile.getR() - tile.getR()) <= 1 && Math.abs(newTile.getC() - tile.getC()) <= 1){
                            tile.updateLevel(roadsPlaced, DIM);
                        }
                    }
                }
            } else {
                if (spotFree(r, k, 2)) {
                    BuildingTile newTile = new BuildingTile(buildingTypes.get(status), r, k);
                    buildingTiles.add(newTile);
                    addBuildingTile(newTile, this);
                    setSpot(r, k, 2, 1);
                }
            }
        }
    }

    public void removeTile(int r, int c){
        int index = 0;
        boolean found = false;
        while(!found && index < roadTiles.size()){
            if(roadTiles.get(index).getR() == r && roadTiles.get(index).getC() == c){
                found = true;
                roadTiles.get(index).deleteRoad(this);
                roadTiles.remove(index);
                roadsPlaced[r][c] = 0;
                setSpot(r,c,1,0);
            }
            index++;
        }
        index = 0;
        while(!found && index < buildingTiles.size()){
            if((buildingTiles.get(index).getR() == r && buildingTiles.get(index).getC() == c)
                    || (buildingTiles.get(index).getR() == r-1 && buildingTiles.get(index).getC() == c)
                    || (buildingTiles.get(index).getR() == r && buildingTiles.get(index).getC() == c-1)
                    || (buildingTiles.get(index).getR() == r-1 && buildingTiles.get(index).getC() == c-1)
            ){
                found = true;
                setSpot(buildingTiles.get(index).getR(),buildingTiles.get(index).getC(),2,0);
                buildingTiles.get(index).deleteBuilding(this);
                buildingTiles.remove(index);
            }
            index++;
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

}
