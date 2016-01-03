package chess.logic;

/**
 * Created by Paul on 14.11.2015.
 */
public class Rook extends Piece {

    public Rook(int color, int posx, int posy) {
        super(color, "Rook", posx, posy);
    }

    @Override
    public int[][] getPossiblexy(Table table) {
        int[][] pmx = new int[14][2];

        //up
        int i= getPosy()-1;
        int j= getPosx();
        int index=-1;
        boolean topleft=false;

        while(i>=0){
            if(table.getGametable()[i][j]!=null)
                break;
            index++;
            pmx[index][0]=j;
            pmx[index][1]=i;
            if(i==0&&j==0)
                topleft=true;
            i--;
        }
        if(i>=0){
            if(table.getGametable()[i][j].getColor()!=getColor()){
                index++;
                pmx[index][0]=j;
                pmx[index][1]=i;
                if(i==0&&j==0)
                    topleft=true;
            }
        }

        //down
        i= getPosy()+1;
        j= getPosx();
        while(i<8){
            if(table.getGametable()[i][j]!=null)
                break;
            index++;
            pmx[index][0]=j;
            pmx[index][1]=i;

            i++;
        }
        if(i<8){
            if(table.getGametable()[i][j].getColor()!=getColor()){
                index++;
                pmx[index][0]=j;
                pmx[index][1]=i;
            }
        }

        //right
        i= getPosy();
        j= getPosx()+1;
        while(j<8){
            if(table.getGametable()[i][j]!=null)
                break;
            index++;
            pmx[index][0]=j;
            pmx[index][1]=i;

            j++;

        }
        if(j<8){
            if(table.getGametable()[i][j].getColor()!=getColor()){
                index++;
                pmx[index][0]=j;
                pmx[index][1]=i;
            }
        }

        //left
        i= getPosy();
        j= getPosx()-1;
        while(j>=0){
            if(table.getGametable()[i][j]!=null)
                break;
            index++;
            pmx[index][0]=j;
            pmx[index][1]=i;
            if(i==0&&j==0)
                topleft=true;

            j--;
        }
        if(j>=0){
            if(table.getGametable()[i][j].getColor()!=getColor()){
                index++;
                pmx[index][0]=j;
                pmx[index][1]=i;
                if(i==0&&j==0)
                    topleft=true;
            }
        }

        //we check for 0,0 and replace them with -1 if necessary
        for(int x=0;x<14;x++){
            if(pmx[x][0]==0&&pmx[x][1]==0){
                if(topleft==true){
                    topleft=false;
                }else{
                    pmx[x][0]=-1;
                    pmx[x][1]=-1;
                }
            }
        }

        return pmx;
    }

    @Override
    public Piece getPiece() {
        return this;
    }
}
