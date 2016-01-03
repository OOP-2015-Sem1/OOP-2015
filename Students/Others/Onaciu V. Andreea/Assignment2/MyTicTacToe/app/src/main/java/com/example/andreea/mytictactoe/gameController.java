package com.example.andreea.mytictactoe;

/**
 * Created by Andreea on 27.10.2015.
 */
public class gameController {
    private String[] squares=new String[9];
    private static int player1score=0;
    private static int player2score=0;
    public gameController(){
        resetSquares();
    }
    public void resetSquares(){
        for (int i=0;i<9;i++){
            squares[i]="";
        }

    }
    public String getXorO(){
        int xEs=0;
        int zeros=0;
        for (int i=0;i<9;i++) {
            if (squares[i].equals("X")) xEs++;
            if (squares[i].equals("0")) zeros++;
        }
            if (xEs>zeros) return "0";
             return "X";

        }

    public String setSquares(int position){
        if (squares[position].equals("")) {
            squares[position]=getXorO();
        }

        return squares[position];
    }

    public boolean squaresAreFull(){
        for (int i=0;i<9;i++) {
            if (squares[i].equals("")) return false;
        }
        return true;
        }

    public String checksWinner(){
        if (squares[0].equals(squares[1]) && squares[0].equals(squares[2])
                && (squares[0].equals("X") || squares[0].equals("0"))) return squares[0];
        if (squares[3].equals(squares[4]) && squares[3].equals(squares[5])
                &&(squares[3].equals("X") || squares[3].equals("0"))) return squares[3];
        if (squares[6].equals(squares[7]) && squares[6].equals(squares[8])
                &&(squares[6].equals("X") || squares[6].equals("0"))) return squares[6];
        if (squares[0].equals(squares[3]) && squares[0].equals(squares[6])
                &&(squares[0].equals("X") || squares[0].equals("0"))) return squares[0];
        if (squares[1].equals(squares[4]) && squares[4].equals(squares[7])
                &&(squares[1].equals("X") || squares[1].equals("0"))) return squares[1];
        if (squares[2].equals(squares[5]) && squares[2].equals(squares[8])
                &&(squares[2].equals("X") || squares[2].equals("0"))) return squares[2];
        if (squares[0].equals(squares[4]) && squares[0].equals(squares[8])
                &&(squares[0].equals("X") || squares[0].equals("0"))) return squares[0];
        if (squares[2].equals(squares[4]) && squares[2].equals(squares[6])
                &&(squares[2].equals("X") || squares[2].equals("0"))) return squares[2];
        return "No one";

    }
   public String score(String winner){
       if (winner.equals("X")) player1score++;
       if (winner.equals("0")) player2score++;

       return player1score + " : "+ player2score;

   }
    public void resetScore(){
        player2score=0;
        player1score=0;
    }

    }

