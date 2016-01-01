package Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Player implements Serializable{

    List<Road> roads = new LinkedList<>();
    public String playerName;
    public ResourceCards resources;
    PlayerColor color;
    int armySize;
    int victoryPoints;

    String settlement;
    String city;
    String verticalRoad;
    String leftLeaningRoad;
    String rightLeaningRoad;

    int longestRoad;

    public Player(String name, PlayerColor color) {
        this.playerName = name;
        this.color = color;
        resources = new ResourceCards();
        setImagePaths();
        victoryPoints = 0;
        armySize = 0;
    }

    private void setImagePaths() {
        switch(color){
            case RED:{
                    settlement = "Pics\\Buildings\\RedSettlement.png";
                    city = "Pics\\Buildings\\RedCity.png";
                    verticalRoad = "Pics\\Buildings\\RedVerticalRoad.png";
                    leftLeaningRoad = "Pics\\Buildings\\RedLeftLeaningRoad.png";
                    rightLeaningRoad =  "Pics\\Buildings\\RedRightLeaningRoad.png";
                    break;
            }
            case BLUE:{
                    settlement = "Pics\\Buildings\\BlueSettlement.png";
                    city = "Pics\\Buildings\\BlueCity.png";
                    verticalRoad = "Pics\\Buildings\\BlueVerticalRoad.png";
                    leftLeaningRoad = "Pics\\Buildings\\BlueLeftLeaningRoad.png";
                    rightLeaningRoad =  "Pics\\Buildings\\BlueRightLeaningRoad.png";
                    break;
            }
            case ORANGE:{
                    settlement = "Pics\\Buildings\\OrangeSettlement.png";
                    city = "Pics\\Buildings\\OrangeCity.png";
                    verticalRoad = "Pics\\Buildings\\OrangeVerticalRoad.png";
                    leftLeaningRoad = "Pics\\Buildings\\OrangeLeftLeaningRoad.png";
                    rightLeaningRoad =  "Pics\\Buildings\\OrangeRightLeaningRoad.png";
                    break;
            }
            case GREY:{
                    settlement = "Pics\\Buildings\\GreySettlement.png";
                    city = "Pics\\Buildings\\GreyCity.png";
                    verticalRoad = "Pics\\Buildings\\GreyVerticalRoad.png";
                    leftLeaningRoad = "Pics\\Buildings\\GreyLeftLeaningRoad.png";
                    rightLeaningRoad =  "Pics\\Buildings\\GreyRightLeaningRoad.png";
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

    public String getRightLeaningRoad() {
        return rightLeaningRoad;
    }

    public String getLeftLeaningRoad() {
        return leftLeaningRoad;
    }

    public String getVerticalRoad() {
        return verticalRoad;
    }

    public String getCity() {
        return city;
    }

    public String getSettlement() {
        return settlement;
    }

    public ResourceCards getResources() {
        return resources;
    }
}
