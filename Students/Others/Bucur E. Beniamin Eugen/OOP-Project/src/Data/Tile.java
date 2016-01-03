package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tile implements Serializable {

    private List<Node> adjacentNodes = new ArrayList<>(6);
    private int tileHeight;
    private int tileWidth;
    private int tileX;
    private int tileY;
    private int tokenHeight;
    private int tokenWidth;
    private int tokenX;
    private int tokenY;
    TileType tileType;
    ResourceType resourceType;
    int tokenNumber;
    private boolean hasRobber = false;
    String tilePath;
    String tokenPath;

    public Tile(TileType tileType) {
        this.tileType = tileType;
        setProperties();
        setTilePath();
    }

    public void placeNumber(int tileNumber) {
        this.tokenNumber = tileNumber;
        setTokenPath();
    }

    public void generateResource() {

    }

    public String getTilePath() {
        return tilePath;
    }

    public String getTokenPath() {
        return tokenPath;
    }

    public int getToken() {
        return tokenNumber;
    }


    private void setProperties() {
        switch (tileType) {
            case PLAIN:
                resourceType = ResourceType.GRAIN;
                break;
            case FOREST:
                resourceType = ResourceType.LUMBER;
                break;
            case PASTURE:
                resourceType = ResourceType.WOOL;
                break;
            case HILL:
                resourceType = ResourceType.BRICK;
                break;
            case MOUNTAIN:
                resourceType = ResourceType.ORE;
                break;
            case DESERT: {
                resourceType = ResourceType.NULL;
                hasRobber = true;
                break;
            }
        }
    }

    private void setTilePath() {
        switch (tileType) {
            case PLAIN:
                tilePath = "Pics\\Tiles\\Plain.png";
                break;
            case FOREST:
                tilePath = "Pics\\Tiles\\Forest.png";
                break;
            case PASTURE:
                tilePath = "Pics\\Tiles\\Pasture.png";
                break;
            case HILL:
                tilePath = "Pics\\Tiles\\Hill.png";
                break;
            case MOUNTAIN:
                tilePath = "Pics\\Tiles\\Mountain.png";
                break;
            case DESERT:
                tilePath = "Pics\\Tiles\\Desert.png";
                break;
        }
    }

    private void setTokenPath() {
        switch (tokenNumber) {
            case 2:
                tokenPath = "Pics\\Tokens\\2.png";
                break;
            case 3:
                tokenPath = "Pics\\Tokens\\3.png";
                break;
            case 4:
                tokenPath = "Pics\\Tokens\\4.png";
                break;
            case 5:
                tokenPath = "Pics\\Tokens\\5.png";
                break;
            case 6:
                tokenPath = "Pics\\Tokens\\6.png";
                break;
            case 8:
                tokenPath = "Pics\\Tokens\\8.png";
                break;
            case 9:
                tokenPath = "Pics\\Tokens\\9.png";
                break;
            case 10:
                tokenPath = "Pics\\Tokens\\10.png";
                break;
            case 11:
                tokenPath = "Pics\\Tokens\\11.png";
                break;
            case 12:
                tokenPath = "Pics\\Tokens\\12.png";
                break;
        }
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public int getTokenHeight() {
        return tokenHeight;
    }

    public void setTokenHeight(int tokenHeight) {
        this.tokenHeight = tokenHeight;
    }

    public int getTokenWidth() {
        return tokenWidth;
    }

    public void setTokenWidth(int tokenWidth) {
        this.tokenWidth = tokenWidth;
    }

    public int getTokenX() {
        return tokenX;
    }

    public void setTokenX(int tokenX) {
        this.tokenX = tokenX;
    }

    public int getTokenY() {
        return tokenY;
    }

    public void setTokenY(int tokenY) {
        this.tokenY = tokenY;
    }

    public boolean isHasRobber() {
        return hasRobber;
    }

    public void setHasRobber(boolean hasRobber) {
        this.hasRobber = hasRobber;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void addAdjacentNode(Node node) {
        adjacentNodes.add(node);
    }

    public List<Node> getAdjacentNodes() {
        return adjacentNodes;
    }
}
