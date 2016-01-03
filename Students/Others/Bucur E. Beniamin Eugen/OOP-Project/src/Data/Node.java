package Data;

import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable{
    List<Tile> neighbouringTiles = new ArrayList<>(3);
    List<Road> neigbouringRoads = new ArrayList<>(3);
    Player owner;
    BuildingType building;
    int nodeHeight;
    int nodeWidth;
    int nodeX;
    int nodeY;

    public Node(int nodeHeight, int nodeWidth, int nodeX, int nodeY){
        this.nodeHeight = nodeHeight;
        this.nodeWidth = nodeWidth;
        this.nodeX = nodeX;
        this.nodeY = nodeY;
    }

    public BuildingType getBuilding() {
        return building;
    }

    public void setBuilding(BuildingType building) {
        this.building = building;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getNodeHeight() {
        return nodeHeight;
    }

    public int getNodeWidth() {
        return nodeWidth;
    }

    public int getNodeX() {
        return nodeX;
    }

    public int getNodeY() {
        return nodeY;
    }

    public List<Tile> getNeighbouringTiles() {
        return neighbouringTiles;
    }

    public void addNeighbouringTiles(Tile neighbouringTiles) {
        this.neighbouringTiles.add(neighbouringTiles);
    }

    public List<Road> getNeighbouringRoads() {
        return neigbouringRoads;
    }

    public void addNeighbouringRoad(Road road){
        neigbouringRoads.add(road);
    }

    public void upgradeToCity(){
        building = BuildingType.CITY;
    }
}
