package chess.resources;

/**
 * Created by Paul on 14.11.2015.
 */
public class Queen extends Piece{
    public Queen(int color, int posx, int posy) {
        super(color,"Queen", posx, posy);
    }

    @Override
    public int[][] getPossiblexy(Table table) {
        int[][] pmx = new int[28][2];

        //upper left
        int i= getPosy()-1;
        int j= getPosx()-1;
        int index=-1;
        boolean topleft=false;

        while(i>=0&&j>=0){
            if(table.getGametable()[i][j]!=null)
                break;
            index++;
            pmx[index][0]=j;
            pmx[index][1]=i;
            if(i==0&&j==0)
                topleft=true;
            j--;
            i--;
        }
        if(i>=0&&j>=0){
            if(table.getGametable()[i][j].getColor()!=getColor()){
                index++;
                pmx[index][0]=j;
                pmx[index][1]=i;
                if(i==0&&j==0)
                    topleft=true;
            }
        }

        //lower left
        i= getPosy()+1;
        j= getPosx()-1;
        while(i<8&&j>=0){
            if(table.getGametable()[i][j]!=null)
                break;
            index++;
            pmx[index][0]=j;
            pmx[index][1]=i;

            j--;
            i++;
        }
        if(i<8&&j>=0){
            if(table.getGametable()[i][j].getColor()!=getColor()){
                index++;
                pmx[index][0]=j;
                pmx[index][1]=i;
            }
        }

        //upper right
        i= getPosy()-1;
        j= getPosx()+1;
        while(i>=0&&j<8){
            if(table.getGametable()[i][j]!=null)
                break;
            index++;
            pmx[index][0]=j;
            pmx[index][1]=i;

            j++;
            i--;
        }
        if(i>=0&&j<8){
            if(table.getGametable()[i][j].getColor()!=getColor()){
                index++;
                pmx[index][0]=j;
                pmx[index][1]=i;
            }
        }

        //lower right
        i= getPosy()+1;
        j= getPosx()+1;
        while(i<8&&j<8){
            if(table.getGametable()[i][j]!=null)
                break;
            index++;
            pmx[index][0]=j;
            pmx[index][1]=i;

            j++;
            i++;
        }
        if(i<8&&j<8){
            if(table.getGametable()[i][j].getColor()!=getColor()){
                index++;
                pmx[index][0]=j;
                pmx[index][1]=i;
            }
        }

        //up
        i= getPosy()-1;
        j= getPosx();

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

        //verificam sa nu ramana 0,0 in sir in afara daca este aceasa pozitie vialbila dar si asa numai una
        for(int x=0;x<28;x++){
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
