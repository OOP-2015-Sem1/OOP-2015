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

    public boolean acquireCard(ResourceCards resourceCards) {
        if ((resourceCards.numberOfGrain < 1) || (resourceCards.numberOfWool < 1) || (resourceCards.numberOfOre < 1))
            return false;
        int cardNumber = generate.nextInt(24);
        if(cardNumber < 14){
            numberOfSoldier++;
        }
        else if(cardNumber < 19){
            numberOfVictoryPoint++;
        }
        else if(cardNumber < 21){
            numberOfMonopoly++;
        }
        else if(cardNumber < 23){
            numberOfRoadBuilding++;
        }
        else if(cardNumber < 25){
            numberOfYearOfPlenty++;
        }
        return true;
    }

}
