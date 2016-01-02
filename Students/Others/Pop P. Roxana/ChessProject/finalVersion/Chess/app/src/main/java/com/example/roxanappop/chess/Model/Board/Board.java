package com.example.roxanappop.chess.Model.Board;

import com.example.roxanappop.chess.Model.Colour;
import com.example.roxanappop.chess.Model.Offset;
import com.example.roxanappop.chess.Model.Pieces.Bishop;
import com.example.roxanappop.chess.Model.Pieces.King;
import com.example.roxanappop.chess.Model.Pieces.Knight;
import com.example.roxanappop.chess.Model.Pieces.Pawn;
import com.example.roxanappop.chess.Model.Pieces.Piece;
import com.example.roxanappop.chess.Model.Pieces.Queen;
import com.example.roxanappop.chess.Model.Pieces.Rook;
import com.example.roxanappop.chess.Model.Position;
import com.example.roxanappop.chess.R;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/13/2015.
 */
public class Board {

    private Cell[][] cells;

    public Board(){

        cells = new Cell[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                cells[i][j] = new Cell();
            }
        }
    }

    public Cell getCellAtPosition(Position position){
        return cells[position.getX()][position.getY()];
    }

    public Piece getPieceAtPosition(Position position){
        return getCellAtPosition(position).getPiece();
    }

    public boolean exceedsBoard(Position position){
        if(position.getX()<0||position.getX()>7){
            return true;
        }
        if(position.getY()<0||position.getY()>7){
            return true;
        }
        return false;
    }

    public boolean isPositionOccupied(Position position){

        Cell cell = getCellAtPosition(position);
        Piece piece = cell.getPiece();
        if(piece==null){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Piece> getPiecesByColour(int colour){
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                Cell cell = cells[i][j];
                Piece piece = cell.getPiece();
                if(piece!=null){
                    if(piece.getColour()==colour){
                        pieces.add(piece);
                    }
                }
            }
        }
        return pieces;
    }
    public void createInitialBoard(){

        cells[0][0].setPiece(new Rook(R.drawable.blackrook, new Position(0, 0), Colour.BLACK));
        cells[0][7].setPiece(new Rook(R.drawable.blackrook, new Position(0, 7), Colour.BLACK));
        cells[0][1].setPiece(new Knight(R.drawable.blackknight, new Position(0, 1), Colour.BLACK));
        cells[0][6].setPiece(new Knight(R.drawable.blackknight, new Position(0, 6), Colour.BLACK));
        cells[0][2].setPiece(new Bishop(R.drawable.blackbishop, new Position(0, 2), Colour.BLACK));
        cells[0][5].setPiece(new Bishop(R.drawable.blackbishop, new Position(0, 5), Colour.BLACK));
        cells[0][3].setPiece(new Queen(R.drawable.blackqueen, new Position(0, 3), Colour.BLACK));
        cells[0][4].setPiece(new King(R.drawable.blackking, new Position(0, 4), Colour.BLACK));
        for(int i=0;i<8;i++){
            cells[1][i].setPiece(new Pawn(R.drawable.blackpawn, new Position(1, i), Colour.BLACK));
        }
        cells[7][0].setPiece(new Rook(R.drawable.whiterook, new Position(7, 0), Colour.WHITE));
        cells[7][7].setPiece(new Rook(R.drawable.whiterook, new Position(7, 7), Colour.WHITE));
        cells[7][1].setPiece(new Knight(R.drawable.whiteknight, new Position(7, 1), Colour.WHITE));
        cells[7][6].setPiece(new Knight(R.drawable.whiteknight, new Position(7, 6), Colour.WHITE));
        cells[7][2].setPiece(new Bishop(R.drawable.whitebishop, new Position(7, 2), Colour.WHITE));
        cells[7][5].setPiece(new Bishop(R.drawable.whitebishop, new Position(7, 5), Colour.WHITE));
        cells[7][3].setPiece(new Queen(R.drawable.whitequeen, new Position(7, 3), Colour.WHITE));
        cells[7][4].setPiece(new King(R.drawable.whiteking, new Position(7, 4), Colour.WHITE));
        for(int i=0;i<8;i++){
            cells[6][i].setPiece(new Pawn(R.drawable.whitepawn, new Position(6, i), Colour.WHITE));
        }

    }

    public boolean isPathFree(Position src, Position dest) {

        int signedWidthDist = dest.getSignedWidthDistance(src);
        int signedHeightDist = dest.getSignedHeightDistance(src);

        int xStep = getStep(signedHeightDist);
        int yStep = getStep(signedWidthDist);
        Offset offset = new Offset(xStep,yStep);

        int cellsToBeTestedNumber = Math.max(Math.abs(signedHeightDist),Math.abs(signedWidthDist))-1;
        for(int i=0; i<cellsToBeTestedNumber;i++){
            if(isPositionOccupied(src.addOffset(offset))){
                return false;
            }
            offset = offset.modifyBy(xStep,yStep);
        }
        return true;
    }

    private int getStep(int distance){
        return (distance==0)?0:distance/Math.abs(distance);
    }
}
