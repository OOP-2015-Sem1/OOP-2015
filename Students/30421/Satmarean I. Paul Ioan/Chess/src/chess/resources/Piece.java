package chess.resources;

/**
 * Created by Paul on 12.11.2015.
 */
public abstract class Piece {
    //current piece position
    private int posx;
    private int posy;

    //possible moves
    private int[][] possiblexy;

    //constructor
    public Piece(int color,String type,int posx,int posy){
        this.color=color;
        this.type=type;
        this.posx=posx;
        this.posy=posy;
    }

    //color
    private int color;
    public int getColor() {
        return color;
    }

    //piece type
    private String type;
    public String getType() {
        return type;
    }

    //pozitia piesei
    public int getPosx() {
        return posx;
    }
    public void setPosx(int posx) {
        this.posx = posx;
    }
    public int getPosy() {
        return posy;
    }
    public void setPosy(int posy) {
        this.posy = posy;
    }

    //pozitii posibile de mutare
    public abstract int[][] getPossiblexy(Table table);

    //returneaza piesa
    public abstract Piece getPiece();

}
