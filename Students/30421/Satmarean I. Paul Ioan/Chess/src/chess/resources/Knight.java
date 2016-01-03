package chess.resources;

/**
 * Created by Paul on 12.11.2015.
 */
public class Knight extends Piece {

    public Knight(int color,int posx,int posy) {
        super(color,"Knight",posx,posy);
    }

    @Override
    public int[][] getPossiblexy(Table table) {
        int[][] pmx = new int[8][2];

        pmx[0][0]=getPosx()-2;
        pmx[1][0]=getPosx()-1;
        pmx[2][0]=getPosx()+1;
        pmx[3][0]=getPosx()+2;
        pmx[4][0]=getPosx()+2;
        pmx[5][0]=getPosx()+1;
        pmx[6][0]=getPosx()-1;
        pmx[7][0]=getPosx()-2;

        for(int i=0;i<8;i++){
            if(pmx[i][0]<0||pmx[i][0]>7)
                pmx[i][0]=-1;
        }

        pmx[0][1]=getPosy()-1;
        pmx[1][1]=getPosy()-2;
        pmx[2][1]=getPosy()-2;
        pmx[3][1]=getPosy()-1;
        pmx[4][1]=getPosy()+1;
        pmx[5][1]=getPosy()+2;
        pmx[6][1]=getPosy()+2;
        pmx[7][1]=getPosy()+1;

        for(int i=0;i<8;i++){
            if(pmx[i][1]<0||pmx[i][1]>7)
                pmx[i][1]=-1;
        }

        for(int i=0;i<8;i++) {
            if(pmx[i][0]!=-1&&pmx[i][1]!=-1)
                if(table.getGametable()[pmx[i][1]][pmx[i][0]]!=null) {
                    if (table.getGametable()[pmx[i][1]][pmx[i][0]].getColor() == getColor()) {
                        pmx[i][1] = -1;
                        pmx[i][0] = -1;
                    }
                }
        }
        return pmx;
    }

    public Piece getPiece(){
        return this;
    }
}
