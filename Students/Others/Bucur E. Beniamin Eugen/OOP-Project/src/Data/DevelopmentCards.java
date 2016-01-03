package Data;

import java.io.Serializable;
import java.util.Random;

public class DevelopmentCards implements Serializable{
    int numberOfSoldier;
    int numberOfVictoryPoint;
    int numberOfMonopoly;
    int numberOfRoadBuilding;
    int numberOfYearOfPlenty;
    Random generate = new Random();

    public DevelopmentCards() {
        numberOfSoldier = 0;
        numberOfVictoryPoint = 0;
        numberOfMonopoly = 0;
        numberOfRoadBuilding = 0;
        numberOfYearOfPlenty = 0;
    }

    public String buyCard() {
        int cardNumber = generate.nextInt(24);
        if(cardNumber < 14){
            numberOfSoldier++;
            return "Soldier";
        }
        else if(cardNumber < 19){
            numberOfVictoryPoint++;
            return "Victory point";
        }
        else if(cardNumber < 21){
            numberOfMonopoly++;
            return "Monopoly";
        }
        else if(cardNumber < 23){
            numberOfRoadBuilding++;
            return "Road building";
        }
        else if(cardNumber < 25){
            numberOfYearOfPlenty++;
            return "Year of plenty";
        }
        return null;
    }

    public void spendCard(DevelopmentType card){
        switch (card){
            case SOLDIER: numberOfSoldier --;
                break;
            case VICTORY_POINT: numberOfVictoryPoint --;
                break;
            case MONOPOLY: numberOfMonopoly --;
                break;
            case ROAD_BUILDING: numberOfRoadBuilding --;
                break;
            case YEAR_OF_PLENTY: numberOfYearOfPlenty --;
                break;
        }
    }

    public int getNrOfCards(DevelopmentType card) {
        switch (card){
            case SOLDIER:
                return numberOfSoldier;
            case VICTORY_POINT:
                return numberOfVictoryPoint;
            case MONOPOLY:
                return numberOfMonopoly;
            case ROAD_BUILDING:
                return numberOfRoadBuilding;
            case YEAR_OF_PLENTY:
                return numberOfYearOfPlenty;
        }
        return 0;
    }
}
