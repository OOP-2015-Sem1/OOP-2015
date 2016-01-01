package Data;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {
    private Random generate = new Random();
    private int redDieValue;
    private int yellowDieValue;
    private int value;
    private String[] redDicePaths = new String[7];
    private String[] yellowDicePaths = new String[7];

    public Dice(){
        redDieValue = 6;
        yellowDieValue = 6;
        getPaths();
    }

    private void getPaths(){
        redDicePaths[1] = "Pics\\DiceFaces\\redDice1.png";
        redDicePaths[2] = "Pics\\DiceFaces\\redDice2.png";
        redDicePaths[3] = "Pics\\DiceFaces\\redDice3.png";
        redDicePaths[4] = "Pics\\DiceFaces\\redDice4.png";
        redDicePaths[5] = "Pics\\DiceFaces\\redDice5.png";
        redDicePaths[6] = "Pics\\DiceFaces\\redDice6.png";
        yellowDicePaths[1] = "Pics\\DiceFaces\\yellowDice1.png";
        yellowDicePaths[2] = "Pics\\DiceFaces\\yellowDice2.png";
        yellowDicePaths[3] = "Pics\\DiceFaces\\yellowDice3.png";
        yellowDicePaths[4] = "Pics\\DiceFaces\\yellowDice4.png";
        yellowDicePaths[5] = "Pics\\DiceFaces\\yellowDice5.png";
        yellowDicePaths[6] = "Pics\\DiceFaces\\yellowDice6.png";
    }

    public void rollDice(){
        redDieValue = generate.nextInt(5) + 1;
        yellowDieValue = generate.nextInt(5) + 1;
        value = redDieValue + yellowDieValue;
    }

    public int getRedDie(){
        return redDieValue;
    }

    public int getYellowDie(){
        return yellowDieValue;
    }

    public String getRedDieImage(int number){
        return redDicePaths[number];
    }

    public String getYellowDieImage(int number){
        return yellowDicePaths[number];
    }

    public int getValue() {
        return value;
    }
}
