package Data;

import java.io.Serializable;

public class ResourceCards implements Serializable{
    int numberOfBrick;
    int numberOfLumber;
    int numberOfWool;
    int numberOfGrain;
    int numberOfOre;

    public ResourceCards() {
        numberOfBrick = 0;
        numberOfLumber = 0;
        numberOfWool = 0;
        numberOfGrain = 0;
        numberOfOre = 0;
    }

    public void incrementResourceCounter(ResourceType resourceType, int increment) {
        switch (resourceType) {
            case BRICK:
                numberOfBrick += increment;
                break;
            case LUMBER:
                numberOfLumber += increment;
                break;
            case WOOL:
                numberOfWool += increment;
                break;
            case GRAIN:
                numberOfGrain += increment;
                break;
            case ORE:
                numberOfOre += increment;
                break;
        }
    }

    public void decrementResourceCounter(ResourceType resourceType, int decrement) {
        switch (resourceType) {
            case BRICK:
                numberOfBrick -= decrement;
                break;
            case LUMBER:
                numberOfLumber -= decrement;
                break;
            case WOOL:
                numberOfWool -= decrement;
                break;
            case GRAIN:
                numberOfGrain -= decrement;
                break;
            case ORE:
                numberOfOre -= decrement;
                break;
        }
    }

    public int getCountForResource(ResourceType resourceType) {
        switch (resourceType) {
            case BRICK:
                return numberOfBrick;
            case LUMBER:
                return numberOfLumber;
            case WOOL:
                return numberOfWool;
            case GRAIN:
                return numberOfGrain;
            case ORE:
                return numberOfOre;
        }
        return 0;
    }


}
