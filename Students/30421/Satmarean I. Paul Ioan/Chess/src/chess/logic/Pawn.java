package chess.logic;

/**
 * Created by Paul on 12.11.2015.
 */
public class Pawn extends Piece{

    public Pawn(int color,int posx,int posy) {
        super(color,"Pawn",posx,posy);

    }

    @Override
    public int[][] getPossiblexy(Table table) {
        int[][] pmx = new int[4][2];

        //pt albe
        if(getColor()==1) {
            if (table.getType().equals("White")) {      //daca masa e cu albe in fata
                pmx[0][0] = getPosx() - 1;
                pmx[0][1] = getPosy() - 1;
                pmx[1][0] = getPosx();
                pmx[1][1] = getPosy() - 1;
                pmx[2][0] = getPosx() + 1;
                pmx[2][1] = getPosy() - 1;

                if(getPosy()==6) {                  //daca se afla pe prima pozitie
                    pmx[3][0] = getPosx();
                    pmx[3][1] = getPosy() - 2;
                }else{
                    pmx[3][0] = -1;
                    pmx[3][1] = -1;
                }
            } else {                                //daca masa e cu negre in fata
                pmx[0][0] = getPosx() - 1;
                pmx[0][1] = getPosy() + 1;
                pmx[1][0] = getPosx();
                pmx[1][1] = getPosy() + 1;
                pmx[2][0] = getPosx() + 1;
                pmx[2][1] = getPosy() + 1;

                if(getPosy()==1) {                  //daca se afla pe prima pozitie
                    pmx[3][0] = getPosx();
                    pmx[3][1] = getPosy() + 2;
                }else{
                    pmx[3][0] = -1;
                    pmx[3][1] = -1;
                }
            }
        }else { //pt negre
            if (table.getType().equals("White")) {      //daca masa e cu albe in fata
                pmx[0][0] = getPosx() - 1;
                pmx[0][1] = getPosy() + 1;
                pmx[1][0] = getPosx();
                pmx[1][1] = getPosy() + 1;
                pmx[2][0] = getPosx() + 1;
                pmx[2][1] = getPosy() + 1;

                if(getPosy()==1) {                      //daca se afla pe prima pozitie
                    pmx[3][0] = getPosx();
                    pmx[3][1] = getPosy() + 2;
                }else{
                    pmx[3][0] = -1;
                    pmx[3][1] = -1;
                }
            } else {                                //daca masa e cu negre in fata
                pmx[0][0] = getPosx() - 1;
                pmx[0][1] = getPosy() - 1;
                pmx[1][0] = getPosx();
                pmx[1][1] = getPosy() - 1;
                pmx[2][0] = getPosx() + 1;
                pmx[2][1] = getPosy() - 1;

                if(getPosy()==6) {                  //daca se afla pe prima pozitie
                    pmx[3][0] = getPosx();
                    pmx[3][1] = getPosy() - 2;
                }else{
                    pmx[3][0] = -1;
                    pmx[3][1] = -1;
                }
            }
        }



        //out of bounds check
        for(int i=0;i<3;i++){
            if(pmx[i][0]>7||pmx[i][0]<0||pmx[i][1]>7||pmx[i][1]<0){
                pmx[i][0]=-1;
                pmx[i][1]=-1;
            }
        }
        //left/right piece check
        //left
        if(pmx[0][1]!=-1&&pmx[0][0]!=-1) {
            if (table.getGametable()[pmx[0][1]][pmx[0][0]] != null) {
                if (table.getGametable()[pmx[0][1]][pmx[0][0]].getColor() == getColor()) {
                    pmx[0][1] = -1;
                    pmx[0][0] = -1;
                }
            } else {
                pmx[0][1] = -1;
                pmx[0][0] = -1;
            }
        }
        //right
        if(pmx[2][1]!=-1&&pmx[2][0]!=-1) {
            if (table.getGametable()[pmx[2][1]][pmx[2][0]] != null) {
                if (table.getGametable()[pmx[2][1]][pmx[2][0]].getColor() == getColor()) {
                    pmx[2][1] = -1;
                    pmx[2][0] = -1;
                }
            } else {
                pmx[2][1] = -1;
                pmx[2][0] = -1;
            }
        }


            //front check
        if(pmx[1][1]!=-1&&pmx[1][0]!=-1) {
            if (table.getGametable()[pmx[1][1]][pmx[1][0]] != null) {
                pmx[1][1] = -1;
                pmx[1][0] = -1;
            }
        }
        return pmx;
    }

    @Override
    public Piece getPiece(){
        return this;
    }
}
