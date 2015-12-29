package Models;





/**
 * Created by Andreea on 03.12.2015.
 */
public class Game {


    public int[][] boardMatrix = new int[3][8];
    private Player player1;
    private Player player2;

    public Game() {

        player1=new Player();
        player2=new Player();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                boardMatrix[i][j] = 0;
            }
        }
    }




    public int checkIfWinner() {
        if (player1.getNrOfBeansLeft()==2) return 2;
        if (player2.getNrOfBeansLeft()==2) return 1;

       return 0;
    }


    public boolean checkIfMill(int i, int j) {
        if (j==0){
            if (boardMatrix[i][0]==boardMatrix[i][1] && boardMatrix[i][1]==boardMatrix[i][2]) return true;
            if (boardMatrix[i][0]==boardMatrix[i][3] && boardMatrix[i][3]==boardMatrix[i][5]) return true;
        }
        else if (j==1){
            if (boardMatrix[i][0]==boardMatrix[i][1] && boardMatrix[i][1]==boardMatrix[i][2]) return true;
            if (boardMatrix[0][j]==boardMatrix[1][j] && boardMatrix[0][j]==boardMatrix[2][j]) return true;
        }
        else if (j==2){
            if (boardMatrix[i][0]==boardMatrix[i][1] && boardMatrix[i][1]==boardMatrix[i][2]) return true;
            if (boardMatrix[i][2]==boardMatrix[i][4] && boardMatrix[i][4]==boardMatrix[i][7]) return true;

        }
        else if (j==3){
            if (boardMatrix[0][j]==boardMatrix[2][j] && boardMatrix[2][j]==boardMatrix[1][j]) return true;
            if (boardMatrix[i][0]==boardMatrix[i][3] && boardMatrix[i][3]==boardMatrix[i][5]) return true;
        }
        else if (j==4){
            if (boardMatrix[0][j]==boardMatrix[2][j] && boardMatrix[2][j]==boardMatrix[1][j]) return true;
            if (boardMatrix[i][7]==boardMatrix[i][2] && boardMatrix[i][2]==boardMatrix[i][4]) return true;
        }
        else if (j==5){
            if (boardMatrix[i][0]==boardMatrix[i][3] && boardMatrix[i][0]==boardMatrix[i][5]) return true;
            if (boardMatrix[i][5]==boardMatrix[i][7] && boardMatrix[i][7]==boardMatrix[i][6]) return true;
        }
        else if (j==6){
            if (boardMatrix[2][j]==boardMatrix[1][j] && boardMatrix[1][j]==boardMatrix[0][6]) return true;
            if (boardMatrix[i][5]==boardMatrix[i][7] && boardMatrix[i][7]==boardMatrix[i][6]) return true;
        }
        else if (j==7){
            if (boardMatrix[i][7]==boardMatrix[i][4] && boardMatrix[i][4]==boardMatrix[i][2]) return true;
            if (boardMatrix[i][5]==boardMatrix[i][7] && boardMatrix[i][7]==boardMatrix[i][6]) return true;
        }
        return false;
    }

    public boolean checkIfAllBeansOnGame(){

        if (player2.getNrOfBeansInGame()==9 && player1.getNrOfBeansInGame()==9) return true;

        return false;
    }


    public boolean putBean(int i,int j,int player){
        if (boardMatrix[i][j]==0) {
            boardMatrix[i][j]=player;
            if (player==1) {

                player1.addBeanInTheGame();
            }
            else {

                player2.addBeanInTheGame();
            }
            return true;
        }
        return false;
    }

    public boolean moveIsAvailable(int row,int column,int wantedRow,int wantedColumn){
        if (boardMatrix[wantedRow][wantedColumn]==0){
            if (column==0){
                if (row==wantedRow && (wantedColumn==1 || wantedColumn==3)) return true;
            }
            else if ((column==1 || column==3) || (column==4 || column==6)){
                if (wantedColumn==column&& wantedRow!=row) {
                    if (row==0 && (wantedRow==1)) return true;
                    if (row==1 ) return true;
                    if (row==2 && wantedRow==1) return true;
                }
                if ((column==1) &&(wantedRow==row && (wantedColumn==0 || wantedColumn==2))) return true;
                if ((column==3) &&(wantedRow==row && (wantedColumn==0 || wantedColumn==5))) return true;
                if ((column==4) &&(wantedRow==row && (wantedColumn==7 || wantedColumn==2))) return true;
                if ((column==6) &&(wantedRow==row && (wantedColumn==7 || wantedColumn==5))) return true;
            }

            else if (column==2){
                if (wantedRow==row && (wantedColumn==1 || wantedColumn==4)) return true;
            }
            else if (column==5){
                if (wantedRow==row && (wantedColumn==3 || wantedColumn==6)) return true;
            }
            else if (column==7){
                if (wantedRow==row && (wantedColumn==6 || wantedColumn==4)) return true;
            }
        }
        return false;
    }


    public boolean moveBean(int currentRow,int currentColumn,int wantedRow,int wantedColumn, int player){


        if ((boardMatrix[currentRow][currentColumn]==player) &&
                (moveIsAvailable(currentRow,currentColumn,wantedRow,wantedColumn)))
        {
            boardMatrix[wantedRow][wantedColumn]=player;
            boardMatrix[currentRow][currentColumn]=0;
            return true;
        }

        else return  false;
    }


    public boolean stoleBean(int i,int j,int thief){

        if (boardMatrix[i][j]!=thief && boardMatrix[i][j]!=0 && (!checkIfMill(i,j))){

            boardMatrix[i][j]=0;

            if (thief==1) player2.removeBeanFromTheGame();
                     else player1.removeBeanFromTheGame();

            return true;
        }
        else return false;
    }



    
}
