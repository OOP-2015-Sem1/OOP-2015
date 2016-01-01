package Data;

import java.io.Serializable;

public class Node implements Serializable{
    Player owner = null;
    BuildingType building = null;
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
}
