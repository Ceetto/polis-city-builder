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
        setAlignment(Pos.TOP_CENTER);
    }

    public void addTile(String status, int r, int k) throws FileNotFoundException {
        if(status.equals("road")){
            if(spotFree(r,k,1)) {
                setSpot(r,k,1,1);
                roadsPlaced[r][k] = 1;
                roadTiles.add(new RoadTile(r, k));
            }
        } else{
            if(spotFree(r,k,2)) {
                buildingTiles.add(new BuildingTile(buildingTypes.get(status), r, k));
                setSpot(r,k,2,1);
            }
        }
    }

    public void drawTiles() throws FileNotFoundException {
        getChildren().clear();
        for (RoadTile road:roadTiles){
            int level = 0;
            int r = road.getR();
            int c = road.getC();

            if(r-1 >= 0 && roadsPlaced[r-1][c] == 1)
                level++;
            if(c+1 < DIM && roadsPlaced[r][c+1] == 1)
                level+=2;
            if(r+1 < DIM && roadsPlaced[r+1][c] == 1)
                level+=4;
            if(c-1 >= 0 && roadsPlaced[r][c-1] == 1)
                level+=8;

            addRoadTile(road.getR(), road.getC(), this, level);
        }

        for (BuildingTile building:buildingTiles){
            addBuildingTile(building.getR(), building.getC(), this, building.getPicture());
        }
    }

    public void removeTile(int r, int c) throws FileNotFoundException {
        int index = 0;
        boolean found = false;
        while(!found && index < roadTiles.size()){
            if(roadTiles.get(index).getR() == r && roadTiles.get(index).getC() == c){
                found = true;
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
                buildingTiles.remove(index);
            }
            index++;
        }
        drawTiles();
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
