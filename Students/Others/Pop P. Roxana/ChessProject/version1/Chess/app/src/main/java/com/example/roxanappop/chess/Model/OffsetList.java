package com.example.roxanappop.chess.Model;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/29/2015.
 */
public class OffsetList {

    private ArrayList<Offset> offsets;

    public OffsetList(){
        offsets= new ArrayList<Offset>();
    }

    public OffsetList(int[][] offsetMatrix){
        offsets=new ArrayList<Offset>();
        for(int i=0;i<offsetMatrix.length;i++){

            Offset offset = new Offset(offsetMatrix[i][0],offsetMatrix[i][1]);
            offsets.add(offset);

        }
    }

    public void add(Offset offset){
        offsets.add(offset);
    }

    public int size(){
        return offsets.size();
    }

    public Offset getOffset(int i){
        return offsets.get(i);
    }
}
