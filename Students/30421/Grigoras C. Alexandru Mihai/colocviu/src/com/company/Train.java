package com.company;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Zuklar on 13-Jan-16.
 */
public class Train {
    private Wagon []wagons;
    String name;
    private String wagontype;
    int numberOfWagons;

    public Train(String name, String wagontype){
        this.name = name;
        this.wagontype = wagontype;
        int i;
        Random random = new Random();
        int randomnumber = -1;

        while (randomnumber < 1)
        {
            randomnumber = random.nextInt(10);
        }
        numberOfWagons = randomnumber;
        wagons = new Wagon[randomnumber];
        for (i = 0; i < randomnumber; i++){
            wagons[i] = new Wagon(i, wagontype);
        }
    }

    public int computeProfit()
    {
        int sum = 0;
        for (Wagon i: wagons) {
            sum += i.getprofit();
        }
        return  sum;
    }

}
