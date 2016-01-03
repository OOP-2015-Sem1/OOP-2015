package Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Player implements Serializable{

    List<Road> roads = new LinkedList<>();
    String playerName;
    ResourceCards resources;
    DevelopmentCards developmentCards;
    PlayerColor color;
    int armySize;
    int victoryPoints;

    String settlementPath;
    String cityPath;
    String verticalRoadPath;
    String leftLeaningRoadPath;
    String rightLeaningRoadPath;

    int longestRoad;

    public Player(String name, PlayerColor color) {
        this.playerName = name;
        this.color = color;
        resources = new ResourceCards();
        developmentCards = new DevelopmentCards();
        setImagePaths();
        victoryPoints = 0;
        armySize = 0;
    }

    private void setImagePaths() {
        switch(color){
            case RED:{
                    settlementPath = "Pics\\Buildings\\RedSettlement.png";
                    cityPath = "Pics\\Buildings\\RedCity.png";
                    verticalRoadPath = "Pics\\Buildings\\RedVerticalRoad.png";
                    leftLeaningRoadPath = "Pics\\Buildings\\RedLeftLeaningRoad.png";
                    rightLeaningRoadPath =  "Pics\\Buildings\\RedRightLeaningRoad.png";
                    break;
            }
            case BLUE:{
                    settlementPath = "Pics\\Buildings\\BlueSettlement.png";
                    cityPath = "Pics\\Buildings\\BlueCity.png";
                    verticalRoadPath = "Pics\\Buildings\\BlueVerticalRoad.png";
                    leftLeaningRoadPath = "Pics\\Buildings\\BlueLeftLeaningRoad.png";
                    rightLeaningRoadPath =  "Pics\\Buildings\\BlueRightLeaningRoad.png";
                    break;
            }
            case ORANGE:{
                    settlementPath = "Pics\\Buildings\\OrangeSettlement.png";
                    cityPath = "Pics\\Buildings\\OrangeCity.png";
                    verticalRoadPath = "Pics\\Buildings\\OrangeVerticalRoad.png";
                    leftLeaningRoadPath = "Pics\\Buildings\\OrangeLeftLeaningRoad.png";
                    rightLeaningRoadPath =  "Pics\\Buildings\\OrangeRightLeaningRoad.png";
                    break;
            }
            case GREY:{
                    settlementPath = "Pics\\Buildings\\GreySettlement.png";
                    cityPath = "Pics\\Buildings\\GreyCity.png";
                    verticalRoadPath = "Pics\\Buildings\\GreyVerticalRoad.png";
                    leftLeaningRoadPath = "Pics\\Buildings\\GreyLeftLeaningRoad.png";
                    rightLeaningRoadPath =  "Pics\\Buildings\\GreyRightLeaningRoad.png";
                    break;
            }
        }
    }

    public void addRoad(Road road){
        roads.add(road);
    }

    public void increaseVictoryPoints(int increment){
        victoryPoints += increment;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void increaseArmySize(int increment){
        armySize += increment;
    }

    public int getArmySize() {
        return armySize;
    }

    public int getLongestRoad() {
        return longestRoad;
    }

    public String getRightLeaningRoadPath() {
        return rightLeaningRoadPath;
    }

    public String getLeftLeaningRoadPath() {
        return leftLeaningRoadPath;
    }

    public String getVerticalRoadPath() {
        return verticalRoadPath;
    }

    public String getCityPath() {
        return cityPath;
    }

    public String getSettlementPath() {
        return settlementPath;
    }

    public ResourceCards getResources() {
        return resources;
    }

    public String getPlayerName() {
        return playerName;
    }

    public DevelopmentCards getDevelopmentCards() {
        return developmentCards;
    }
}
