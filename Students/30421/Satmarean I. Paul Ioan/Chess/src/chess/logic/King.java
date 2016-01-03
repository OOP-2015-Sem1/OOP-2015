package chess.logic;

/**
 * Created by Paul on 14.11.2015.
 */
public class King extends Piece{
    public King(int color,int posx, int posy) {
        super(color, "King", posx, posy);
    }

    @Override
    public int[][] getPossiblexy(Table table) {
        int[][] pmx = new int[8][2];

        //up
        pmx[0][0]=getPosx();
        pmx[0][1]=getPosy()-1;

        //upper right
        pmx[1][0]=getPosx()+1;
        pmx[1][1]=getPosy()-1;

        //upper left
        pmx[2][0]=getPosx()-1;
        pmx[2][1]=getPosy()-1;

        //left
        pmx[3][0]=getPosx()-1;
        pmx[3][1]=getPosy();

        //right
        pmx[4][0]=getPosx()+1;
        pmx[4][1]=getPosy();

        //lower left
        pmx[5][0]=getPosx()-1;
        pmx[5][1]=getPosy()+1;

        //down
        pmx[6][0]=getPosx();
        pmx[6][1]=getPosy()+1;

        //lower right
        pmx[7][0]=getPosx()+1;
        pmx[7][1]=getPosy()+1;

        //TODO
        //trebuie eliminate pozitiile atacate(its gonna be shit)


        //remove the moves outside the arena
        for(int i=0;i<8;i++){
            if(pmx[i][1]<0||pmx[i][1]>7||pmx[i][0]<0||pmx[i][0]>7){
                pmx[i][1]=-1;
                pmx[i][0]=-1;
            }else{
                if(table.getGametable()[pmx[i][1]][pmx[i][0]]!=null){
                    if(table.getGametable()[pmx[i][1]][pmx[i][0]].getColor()==getColor()){
                        pmx[i][1]=-1;
                        pmx[i][0]=-1;
                    }
                }
            }
        }


        //verifies if any possible move is attacked
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (table.getGametable()[i][j] != null) {
                    if (table.getGametable()[i][j].getColor() != getColor()&& !table.getGametable()[i][j].getType().equals("King") ) {

                        int[][] pos = table.getGametable()[i][j].getPossiblexy(table);
                        for (int x = 0; x < pos.length; x++) {
                            for (int y = 0; y < pmx.length; y++) {
                                if (pos[x][1] == pmx[y][1] && pos[x][0] == pmx[y][0]) {
                                    pmx[y][1]= -1;
                                    pmx[y][0]= -1;
                                }
                            }
                        }
                    }
                }
            }
        }




        return pmx;
    }

    @Override
    public Piece getPiece() {
        return this;
    }

    //checks if the king can move anywhere from its position
    public boolean canMove(Table table){
        int[][] pos = getPossiblexy(table);
        boolean can = false;
        for(int i=0; i< pos.length; i++){
            if(pos[i][1]!=-1 && pos[i][0]!= -1){
                can = true;
                break;
            }
        }
        return can;
    }

    //return true if king is attacked
    public boolean isAttacked(Table table) {
        boolean is = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (table.getGametable()[i][j] != null) {
                    if (table.getGametable()[i][j].getColor() != getColor()) {
                        int[][] pos = table.getGametable()[i][j].getPossiblexy(table);
                        for (int x = 0; x < pos.length; x++) {
                            if (pos[x][1] == getPosy() && pos[x][0] == getPosx()) {
                                is = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return is;
    }

}
