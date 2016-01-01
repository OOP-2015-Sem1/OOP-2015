package Data;

public class Road {

    Player owner;
    int startingX;
    int startingY;
    int finalX;
    int finalY;
    String pathToImage;

    public Road(Player owner, int startingX, int startingY, int finalX, int finalY) {
        this.owner = owner;
        this.startingX = startingX;
        this.startingY = startingY;
        this.finalX = finalX;
        this.finalY = finalY;
        setPathToImage();
    }

    private void setPathToImage() {
        if (startingX == finalX) {
            pathToImage = "Pics\\Buildings\\" + getColor() + "VerticalRoad.png";
        } else if (startingX < finalX) {
            if (startingY < finalY)
                pathToImage = "Pics\\Buildings\\" + getColor() + "LeftLeaningRoad.png";
            else
                pathToImage = "Pics\\Buildings\\" + getColor() + "RightLeaningRoad.png";
        } else {
            if (startingY > finalY)
                pathToImage = "Pics\\Buildings\\" + getColor() + "LeftLeaningRoad.png";
            else
                pathToImage = "Pics\\Buildings\\" + getColor() + "RightLeaningRoad.png";
        }
    }

    private String getColor() {
        switch (owner.color) {
            case RED:
                return "Red";
            case BLUE:
                return "Blue";
            case ORANGE:
                return "Orange";
            case GREY:
                return "Grey";
        }
        return "";
    }
}


