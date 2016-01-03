package com.example.roxana.tictactoe;

/**
 * Created by Roxana on 10/28/2015.
 */
public class Game {

    int count=0;//nr of made moves
    int moves[];//the positions are the moves
    //values: 1-move made by X, 2-move made by O, 0-move not made
    int winingMoves[];

    private static Game instance = null;
    private Game() {
        moves=new int[9];
        initializeMoves();
        winingMoves=new int[3];
    }

    public static Game getInstance(){
        if(instance==null){
            instance=new Game();
        }
        return instance;
    }

    public static boolean isGameOn(){
        return (instance!=null);
    }

    /**** Method getting the 3 winning moves ****/
    public int[] getWinningMoves(){
        return winingMoves;
    }
    /**** Method getting the bestMove field ****/
    public int[] getMoves(){
        return moves;
    }
    /**** Method restarting the game ****/
    public void restartGame(){
        initializeMoves();
        initializeWinningMoves();
        count=0;
    }

    /**** Method deciding whose turn it is ****/
    public int getTurn(){
        //returns 1 for X and 2 for O
        return (count%2)+1;
    }


    /**** Method testing if the game is over ****/
    public boolean isGameOver(){
        return (evaluateTable()!=-1);
    }
    /**** Method that evaluates the XO table ***/
    public int evaluateTable() {
        //returns: -1: game not over
        //  1: X wins
        //  2: O wins
        //  0: tie
        int returnValue = 0;//suppose game is over and no one won

        //testing the rows
        if (moves[0]!=0&&areEqual(moves[0], moves[1], moves[2])) {
            setWinningMoves(0,1,2);
            returnValue = moves[0];}
        else if (moves[3]!=0&&areEqual(moves[3], moves[4], moves[5])){
            setWinningMoves(3,4,5);
            returnValue = moves[3];
        }
        else if (moves[6]!=0&&areEqual(moves[6], moves[7], moves[8])) {
            setWinningMoves(6,7,8);
            returnValue = moves[6];
        }
        //testing the columns
        else if (moves[0]!=0&&areEqual(moves[0], moves[3], moves[6])) {
            setWinningMoves(0,3,6);
            returnValue = moves[0];
        }
        else if (moves[1]!=0&&areEqual(moves[1], moves[4], moves[7])) {
            setWinningMoves(1,4,7);
            returnValue = moves[1];
        }
        else if (moves[2]!=0&&areEqual(moves[2], moves[5], moves[8])) {
            setWinningMoves(2,5,8);
            returnValue = moves[2];
        }
        //testing the diagonals
        else if (moves[0]!=0&&areEqual(moves[0], moves[4], moves[8])) {
            setWinningMoves(0,4,8);
            returnValue = moves[0];
        }
        else if (moves[2]!=0&&areEqual(moves[2], moves[4], moves[6])) {
            setWinningMoves(2,4,6);
            returnValue = moves[2];
        }
        else if ((returnValue == 0) && (count != 9)) {
            //the game ain't over yet
            returnValue = -1;
        }
        return returnValue;
    }

    /**** Method that makes a move ****/
    public void makeMove(int i,int player){
        //player "player" makes move i
        moves[i] = player;
        //the nr of moves made increases
        count++;
    }

    /**** Method that deletes a move ****/
    public void deleteMove(int move){
        moves[move]=0;
        count--;
        initializeWinningMoves();
    }

    /**** Method that returns the result of the game ****/
    public int utility(){
        //returns 1 if O wins, -1 if O loses and 0 for tie
        int eT = evaluateTable();

        if(eT==2){
            return  10;
        }
        else if(eT==1){
            return -10;
        }
        return 0;
    }

    /**** Method for initializing the moves ****/
    private void initializeMoves(){
        for(int i=0;i<9;i++){
            moves[i]=0;
        }
    }
    /**** Method for initializing the winning moves ****/
    private void initializeWinningMoves(){
        setWinningMoves(-1,-1,-1);
    }
    //just some helpful methods
    private boolean areEqual(int a, int b, int c){
        return (a==b&&a==c);
    }
    private void setWinningMoves(int a, int b, int c){
        winingMoves[0]=a;
        winingMoves[1]=b;
        winingMoves[2]=c;
    }
}
