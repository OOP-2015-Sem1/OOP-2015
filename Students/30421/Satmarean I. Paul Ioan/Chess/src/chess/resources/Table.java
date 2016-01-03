package chess.resources;

/**
 * Created by Paul on 12.11.2015.
 */
public class Table {

    //piece matrix
    private Piece[][] gametable;

    //table type(whites in front or blacks in front)
    private String type;

    //table color
    private int color;

    //returns the color which can be controlled
    public int getColor() {
        return color;
    }

    //returns table type to help pieces compute their possible moves
    public String getType() {
        return type;
    }

    //returns table
    public Piece[][] getGametable() {
        return gametable;
    }

    //sets table(not used but kept for testing purposes)
    public void setGametable(Piece[][] gametable) {
        this.gametable = gametable;
    }

    //moves pieces on the boart(not checked!!)
    public void move(int y, int x, int py,int px){
        gametable[y][x].setPosx(px);
        gametable[y][x].setPosy(py);
        gametable[py][px]= gametable[y][x];
        gametable[y][x]=null;
    }

    //constructor
    public Table(String s){
        gametable= new Piece[8][8];
        type=s;

        if(s.equals("White")){
            color = 1;
        }else{
            color = 0;
        }

        //add pawns
        if(s.equals("White")){
            for(int i=0;i<8;i++){
                //negre
                gametable[1][i]= new Pawn(0,i,1);
                //albe
                gametable[6][i]= new Pawn(1,i,6);
            }
            //rooks
            //00 07 70 77
            //negre
            gametable[0][0] = new Rook(0,0,0);
            gametable[0][7] = new Rook(0,7,0);
            //albe
            gametable[7][0] = new Rook(1,0,7);
            gametable[7][7] = new Rook(1,7,7);

            //Knights
            //01 06 71 76
            //negre
            gametable[0][1] = new Knight(0,1,0);
            gametable[0][6] = new Knight(0,6,0);
            //albe
            gametable[7][1] = new Knight(1,1,7);
            gametable[7][6] = new Knight(1,6,7);

            //Bishops
            //02 05 72 75
            //negre
            gametable[0][2] = new Bishop(0,2,0);
            gametable[0][5] = new Bishop(0,5,0);
            //albe
            gametable[7][2] = new Bishop(1,2,7);
            gametable[7][5] = new Bishop(1,5,7);

            //Kings
            //w 74 b 04
            //negre
            gametable[0][4] = new King(0,4,0);
            //albe
            gametable[7][4] = new King(1,4,7);


            //QUEENS
            //b03 w73
            //negre
            gametable[0][3] = new Queen(0,3,0);
            //albe
            gametable[7][3] = new Queen(1,3,7);

        }else{
            for(int i=0;i<8;i++){
                //negre
                gametable[1][i]= new Pawn(1,i,1);
                //albe
                gametable[6][i]= new Pawn(0,i,6);
            }
            //rooks
            //00 07 70 77
            //negre
            gametable[0][0] = new Rook(1,0,0);
            gametable[0][7] = new Rook(1,7,0);
            //albe
            gametable[7][0] = new Rook(0,0,7);
            gametable[7][7] = new Rook(0,7,7);

            //Knights
            //01 06 71 76
            //negre
            gametable[0][1] = new Knight(1,1,0);
            gametable[0][6] = new Knight(1,6,0);
            //albe
            gametable[7][1] = new Knight(0,1,7);
            gametable[7][6] = new Knight(0,6,7);

            //Bishops
            //02 05 72 75
            //negre
            gametable[0][2] = new Bishop(1,2,0);
            gametable[0][5] = new Bishop(1,5,0);
            //albe
            gametable[7][2] = new Bishop(0,5,7);
            gametable[7][5] = new Bishop(0,2,7);

            //Kings
            //w 74 b 04
            //negre
            gametable[0][4] = new King(1,4,0);
            //albe
            gametable[7][4] = new King(0,4,7);


            //QUEENS
            //b03 w73
            //negre
            gametable[0][3] = new Queen(1,3,0);
            //albe
            gametable[7][3] = new Queen(0,3,7);
        }
    }
}
