package Data;

import java.io.Serializable;

public class Road implements Serializable {

    Player owner;
    Node start;
    Node end;
    int x;
    int y;
    String pathToImage;


    public Road(Player owner, Node start, Node end) {
        this.owner = owner;
        this.start = start;
        this.end = end;
        setPathAndCoordinates();
    }

    private void setPathAndCoordinates() {
        if (Math.abs(start.getNodeX() - end.getNodeX()) < 3) {
            if (start.getNodeY() > end.getNodeY()) {
                pathToImage = "Pics\\Buildings\\" + getColor() + "VerticalRoad.png";
                x = end.getNodeX() + 11;
                y = end.getNodeY() + 29;
            } else {
                pathToImage = "Pics\\Buildings\\" + getColor() + "VerticalRoad.png";
                x = start.getNodeX() + 11;
                y = start.getNodeY() + 29;
            }
        } else if (start.getNodeX() < end.getNodeX()) {
            if (start.getNodeY() < end.getNodeY()) {
                pathToImage = "Pics\\Buildings\\" + getColor() + "LeftLeaningRoad.png";
                x = start.getNodeX() + 20;
                y = start.getNodeY() + 7;
            } else {
                pathToImage = "Pics\\Buildings\\" + getColor() + "RightLeaningRoad.png";
                x = start.getNodeX() + 26;
                y = start.getNodeY() - 30;
            }
        } else {
            if (start.getNodeY() > end.getNodeY()) {
                pathToImage = "Pics\\Buildings\\" + getColor() + "LeftLeaningRoad.png";
                x = end.getNodeX() + 20;
                y = end.getNodeY() + 7;
            } else {
                pathToImage = "Pics\\Buildings\\" + getColor() + "RightLeaningRoad.png";
                x = end.getNodeX() + 26;
                y = end.getNodeY() - 30;
            }
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

    public String getPathToImage() {
        return pathToImage;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public Player getOwner() {
        return owner;
    }
}


