package Models;

/**
 * Created by Andreea on 29.12.2015.
 */
public class Player {
    private int nrOfBeansLeft;
    private int nrOfBeansInGame;

    public Player(){
       nrOfBeansInGame=0;
       nrOfBeansLeft=9;
    }



    public void setNrOfBeansLeft(int nrOfBeansLeft){
        this.nrOfBeansLeft=nrOfBeansLeft;
    }

    public void setNrOfBeansInGame(int nrOfBeansInGame){
        this.nrOfBeansInGame=nrOfBeansInGame;
    }

    public int getNrOfBeansLeft(){
        return  nrOfBeansLeft;
    }

    public int getNrOfBeansInGame(){
        return  nrOfBeansInGame;
    }

    public void addBeanInTheGame(){
        this.nrOfBeansInGame++;
    }

    public void removeBeanFromTheGame(){
        this.nrOfBeansLeft--;
    }
}
