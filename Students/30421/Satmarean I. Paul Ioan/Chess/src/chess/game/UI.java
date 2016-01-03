package chess.game;

import chess.resources.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Paul on 12.11.2015.
 */

public class UI extends JFrame {
    //connection
    public Connection conn;

    //tabla de joc(panouri)
    private JPanel[][] table;

    //player color turn
    private int turn;

    //tabla de joc(piese)
    private Table gametable;

    //piesa selectata curent
    private Piece selectedPiece;

    //imaginile
    private JLabel[][] piese;

    //constructor(tabla goala)
    public UI(String title,Table gametable) throws HeadlessException {
        super(title);

        this.gametable = gametable;

        setLayout(new GridLayout(8,8));

        //whites always go first
        turn = 1;

        //table setup
        setup(gametable);

    }

    //sets the connection
    public void setConn(Connection conn){
        this.conn = conn;
    }

    //switches turn
    public void switchTurn(){
        if(turn==1){
            turn=0;
        }else{
            turn=1;
        }
    }

    //initial table setup(no pieces, just tile colors and event handlers)
    public void setup(Table gametable){
        table= new JPanel[8][8];
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++){
                table[i][j] =  new JPanel();

                // culorile de pe tabla
                if(i%2==0) {
                    if (j % 2 != 0) {
                        table[i][j].setBackground(Color.DARK_GRAY);
                    } else {
                        table[i][j].setBackground(Color.WHITE);
                    }
                }else {
                    if (j % 2 != 0) {
                        table[i][j].setBackground(Color.WHITE);
                    } else {
                        table[i][j].setBackground(Color.DARK_GRAY);
                    }
                }
                //to center the piece image
                table[i][j].setLayout(new BorderLayout());
                final int finalJ = j;
                final int finalI = i;
                this.table[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        click(gametable,finalJ, finalI);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                add(table[i][j]);
            }

    }


    public JPanel[][] getTable() {
        return table;
    }

    public void setTable(JPanel[][] table) {
        this.table = table;
    }

    //original tile colors(no highlights)
    public void resetGraphics(){
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++){
                // culorile de pe tabla
                if(i%2==0) {
                    if (j % 2 != 0) {
                        table[i][j].setBackground(Color.DARK_GRAY);
                    } else {
                        table[i][j].setBackground(Color.WHITE);
                    }
                }else {
                    if (j % 2 != 0) {
                        table[i][j].setBackground(Color.WHITE);
                    } else {
                        table[i][j].setBackground(Color.DARK_GRAY);
                    }
                }

            }
    }

    //highlights the possible moves for the selected piece
    public void highlight(Table table,int y, int x){
        resetGraphics();

        int[][] pos = table.getGametable()[y][x].getPossiblexy(table);

        for(int i=0;i<pos.length;i++){
            System.out.println(pos[i][1] + "," +pos[i][0]);
            if(pos[i][1]!=-1&&pos[i][0]!=-1) {
                //this.table[pos[i][1]][pos[i][0]].setBackground(Color.BLUE);
                if(table.getGametable()[pos[i][1]][pos[i][0]]!=null){
                    this.table[pos[i][1]][pos[i][0]].setBackground(Color.RED);
                }else{
                    this.table[pos[i][1]][pos[i][0]].setBackground(Color.BLUE);
                }
                }
            }
    }

    //prints the table in console
    public void printTable(Table table){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                try{
                    System.out.print(table.getGametable()[i][j].getType() + "(" + table.getGametable()[i][j].getPosx() +
                            "," + table.getGametable()[i][j].getPosy() + ") ");
                }catch(NullPointerException ex){
                    System.out.print("NULL ");
                }
            }
            System.out.println();
        }
    }

    //moves a piece on the board and switches turns
    public void movePiece(int posy,int posx){
        gametable.move(selectedPiece.getPosy(), selectedPiece.getPosx(), posy, posx);
        eraseTable();
        selectedPiece = null;
        resetGraphics();
        updateTable();
        printTable(gametable);
        switchTurn();
    }

    //deselects the current selected piece
    public void deselectPiece(){
        selectedPiece = null;
        resetGraphics();
    }

    //selects a piece
    public void selectPiece(int x, int y){
        try {
            if(gametable.getGametable()[y][x].getColor()==turn) {
                System.out.println(gametable.getGametable()[y][x].getType());
                selectedPiece = gametable.getGametable()[y][x];
                /*if(selectedPiece.getType().equals("King")){
                    King a = (King)selectedPiece;
                    System.out.println(a.canMove(gametable));
                    System.out.println(a.isAttacked(gametable));

                }*/
                highlight(gametable, y, x);
            }
        } catch (NullPointerException ex) {
            //For testing puroposes
            System.out.println("NU ESTE PIESA ACOLO");
        }
    }

    //does exactly what click does except send a command
    //could have entered another argument to the click method but
    //this way is easier to debug
    public void receive(Table tab,int x, int y){
        //if a piece is selected and a possible move for that piece is chosen
        if (selectedPiece!=null) {
            if ((table[y][x].getBackground().equals(Color.BLUE)
                    || table[y][x].getBackground().equals(Color.RED))
                    && selectedPiece != null) {

                movePiece(y, x);
                eraseTable();
                updateTable();
            }else {

                //if we want to deselect a piece
                if (selectedPiece != null &&
                        !(table[y][x].getBackground().equals(Color.BLUE)
                                || table[y][x].getBackground().equals(Color.RED))
                        && tab.getGametable()[y][x]==null) {

                    deselectPiece();
                } else{
                    //if we want to choose another piece
                    selectPiece(x,y);
                }
            }
        }else {
            //if we want to choose another piece
            selectPiece(x,y);
        }
    }

    //click event for table
    public void click(Table tab,int x, int y){

        conn.sendClick(x,y);

        //if a piece is selected and a possible move for that piece is chosen
        if (selectedPiece!=null) {
            if ((table[y][x].getBackground().equals(Color.BLUE)
                    || table[y][x].getBackground().equals(Color.RED))
                    && selectedPiece != null) {

                movePiece(y, x);

            }else {

                //if we want to deselect a piece
                if (selectedPiece != null &&
                        !(table[y][x].getBackground().equals(Color.BLUE)
                                || table[y][x].getBackground().equals(Color.RED))
                                && tab.getGametable()[y][x]==null) {

                    deselectPiece();
                } else{
                    //if we want to choose another piece
                    if(tab.getGametable()[y][x].getColor()==tab.getColor()) {
                        selectPiece(x, y);
                    }
                }
            }
        }else {
            //if we want to choose another piece
            if(tab.getGametable()[y][x].getColor()==tab.getColor()) {
                selectPiece(x, y);
            }
        }
        if(checkMate(tab)){
            JOptionPane.showMessageDialog(null,"Game over, Check mate.");
        }
    }

    //erases the pieces from the board
    public void eraseTable(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) {

                if ((piese[i][j] != null)) {
                    this.table[i][j].remove(piese[i][j]);
                    this.table[i][j].revalidate();
                    this.table[i][j].repaint();
                }
            }
        }
    }

    //updates the graphic table from the matrix of pieces
    public void updateTable(){
        piese = new JLabel[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){

                //curatam tabla de piese vechi
                if(gametable.getGametable()[i][j]==null&&piese[i][j]!=null){
                    this.table[i][j].remove(piese[i][j]);
                    this.table[i][j].revalidate();
                    this.table[i][j].repaint();
                }
                try{
                    addPiece(gametable.getGametable()[i][j],i,j);
                    final int finalI = i;
                    final int finalJ = j;


                }catch(NullPointerException ex) {
                    //System.out.println(i + " " + j);
                }
            }
        }
    }

    //adds a piece to the table(picture)
    public void addPiece(Piece piece,int posx,int posy){
       if(piece.getColor()==1) {
           if (piece.getType().equals("Pawn")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\pawn-white.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("Knight")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\knight-white.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("Bishop")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\bishop-white.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("Rook")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\rook-white.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("King")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\king-white.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("Queen")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\queen-white.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }

       }else{
           if (piece.getType().equals("Pawn")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\pawn-black.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("Knight")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\knight-black.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("Bishop")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\bishop-black.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("Rook")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\rook-black.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("King")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\king-black.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
           if (piece.getType().equals("Queen")) {
               try {
                   BufferedImage myPicture = ImageIO.read(new File("pictures\\queen-black.png"));
                   piese[posx][posy] = new JLabel(new ImageIcon(myPicture));
                   table[posx][posy].add(piese[posx][posy],BorderLayout.CENTER);
               }catch(IOException ex){
                   System.out.print("IMAGE NOT FOUND");
               }
           }
       }
    }

    //returns the game table
    public Table getGametable(){
        return gametable;
    }

    //checks if check mate
    public boolean checkMate(Table table){
        King wk= null;
        King bk= null;

        //find the kings
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) {
                if (table.getGametable()[i][j] != null) {
                    if (table.getGametable()[i][j].getType().equals("King")) {
                        if (table.getGametable()[i][j].getColor() == 0) {
                            bk = (King) table.getGametable()[i][j];
                        } else {
                            wk = (King) table.getGametable()[i][j];
                        }
                    }
                }
            }
        }

        if(wk.isAttacked(table)){
            if(!wk.canMove(table)){
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        if (table.getGametable()[i][j] != null) {
                            if (!table.getGametable()[i][j].getType().equals("King")) {
                                int[][] pos = table.getGametable()[i][j].getPossiblexy(table);
                                for (int x = 0; x < pos.length; x++) {

                                    if (pos[x][1] != -1 && pos[x][0] != -1) {
                                        //x - 0, y -1
                                        table.move(i, j, pos[x][1], pos[x][0]);
                                        if (!bk.isAttacked(table)) {
                                            table.move(pos[x][1], pos[x][0],i, j);
                                            return false;
                                        }
                                        table.move(pos[x][1], pos[x][0],i, j);
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }else{
                return false;
            }
        }

        if(bk.isAttacked(table)){
            if(!bk.canMove(table)){
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        if (table.getGametable()[i][j] != null) {
                            if (!table.getGametable()[i][j].getType().equals("King")) {
                                int[][] pos = table.getGametable()[i][j].getPossiblexy(table);
                                for (int x = 0; x < pos.length; x++) {

                                    if (pos[x][1] != -1 && pos[x][0] != -1) {
                                        //x - 0, y -1
                                        table.move(i, j, pos[x][1], pos[x][0]);
                                        if (!bk.isAttacked(table)) {
                                            table.move(pos[x][1], pos[x][0],i, j);
                                            return false;
                                        }
                                        table.move(pos[x][1], pos[x][0],i, j);
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }else{
                return false;
            }
        }


      return false;
    }


}
