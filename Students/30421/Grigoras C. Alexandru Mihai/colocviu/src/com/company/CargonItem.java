package com.company;

import java.util.Random;

/**
 * Created by Zuklar on 13-Jan-16.
 */
public class CargonItem{
    private final String name;
    final int profit;
    private Carriable  []item;
    public int numberofitems;

    public  CargonItem (String name, int profit)
    {
        this.name = name;
        this.profit = profit;
        Random random = new Random();
        int numberofitems = -1;
        while (numberofitems < 1)
        {
            numberofitems = random.nextInt(10);
        }
        item = new Carriable[numberofitems];
        this.numberofitems = numberofitems;
    }

}
