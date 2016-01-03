package Data;

import java.io.Serializable;

public class ResourceCards implements Serializable {
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
            case BRICK: {
                numberOfBrick += increment;
                break;
            }
            case LUMBER: {
                numberOfLumber += increment;
                break;
            }
            case WOOL: {
                numberOfWool += increment;
                break;
            }
            case GRAIN: {
                numberOfGrain += increment;
                break;
            }
            case ORE: {
                numberOfOre += increment;
                break;
            }
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
            case NULL:
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
            case NULL:
                break;
        }
        return 19;
    }

    public boolean verifyResourceAvailability(int numberOfBrick, int numberOfLumber, int numberOfWool, int numberOfGrain, int numberOfOre) {
        if (this.numberOfBrick < numberOfBrick)
            return false;
        if (this.numberOfLumber < numberOfLumber)
            return false;
        if (this.numberOfWool < numberOfWool)
            return false;
        if (this.numberOfGrain < numberOfGrain)
            return false;
        if (this.numberOfOre < numberOfOre)
            return false;
        return true;
    }

    public void spendResources(int numberOfBrick, int numberOfLumber, int numberOfWool, int numberOfGrain, int numberOfOre) {
        this.numberOfBrick -= numberOfBrick;
        this.numberOfLumber -= numberOfLumber;
        this.numberOfWool -= numberOfWool;
        this.numberOfGrain -= numberOfGrain;
        this.numberOfOre -= numberOfOre;
    }

    public int countAllResources(){
        return numberOfBrick + numberOfLumber + numberOfWool + numberOfGrain + numberOfOre;
    }

    public void setResourcesToNine(){
        numberOfBrick = 9;
        numberOfOre = 9;
        numberOfGrain = 9;
        numberOfWool = 9;
        numberOfLumber = 9;
    }
}
