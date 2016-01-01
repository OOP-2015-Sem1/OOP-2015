package controller;
import gui.Game;

public class Move extends Main{

	static int[][] rotateMatrix= new int[3][3];

	public static void init() {    //REWORKED
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				if (i == 9) {
					matrice[i][j] = 200;
				} else {
					matrice[i][j] = 0;
				}
				if (j == 0 || j == 12 || j == 1 || j == 2 || j == 13 || j == 14) {
					matrice[i][j] = 200;
				}

			}
		}
		Game.repaint();
	}

	public static void afis() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				System.out.print(matrice[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	public static boolean stopCond() {   //REWORKED
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++){
				if(matrice[row+i][col+j]<20 && matrice[row+i][col+j]>10){
					if(matrice[row+i][col+j]+matrice[row+i+1][col+j]>30){
						return true;
					}
				}
			}
		return false;
	}

	public static void moveLine(int l1, int c1) {    //REWORKED
		if (matrice[l1][c1] > 10 && matrice[l1][c1]<20) {
			matrice[l1 + 1][c1] = matrice[l1][c1];
			matrice[l1][c1] = 0;
		}
		if (matrice[l1][c1 + 1] > 10 && matrice[l1][c1 + 1]<20) {
			matrice[l1 + 1][c1 + 1] = matrice[l1][c1 + 1];
			matrice[l1][c1 + 1] = 0;
		}
		if (matrice[l1][c1 + 2] > 10 && matrice[l1][c1 + 2] < 20) {
			matrice[l1 + 1][c1 + 2] = matrice[l1][c1 + 2];
			matrice[l1][c1 + 2] = 0;
		}
	}

	public static void moveColRight(int l1, int c1) {     //REWORKED
		if (matrice[l1][c1] >10 && matrice[l1][c1]<100) {
			matrice[l1][c1 + 1] = matrice[l1][c1];
			matrice[l1][c1] = 0;
		}
		if (matrice[l1 + 1][c1] > 10 && matrice[l1 + 1][c1] < 100) {
			matrice[l1 + 1][c1 + 1] = matrice[l1 + 1][c1];
			matrice[l1 + 1][c1] = 0;
		}
		if (matrice[l1 + 2][c1] > 10 && matrice[l1 + 2][c1] <100) {
			matrice[l1 + 2][c1 + 1] = matrice[l1 + 2][c1];
			matrice[l1 + 2][c1] = 0;
		}
		
	}

	public static void moveColLeft(int l1, int c1) {    //REWORKED
		if (matrice[l1][c1] > 10 && matrice[l1][c1]<100) {
			matrice[l1][c1 - 1] = matrice[l1][c1];
			matrice[l1][c1] = 0;
		}
		if (matrice[l1 + 1][c1] > 10 && matrice[l1 + 1][c1] <100) {
			matrice[l1 + 1][c1 - 1] = matrice[l1 + 1][c1];
			matrice[l1 + 1][c1] = 0;
		}
		if (matrice[l1 + 2][c1] > 10 && matrice[l1 + 2][c1] < 100) {
			matrice[l1 + 2][c1 - 1] = matrice[l1 + 2][c1];
			matrice[l1 + 2][c1] = 0;
		}
		
	}

	public static void clearLine(int l1, int c1) {  //REWORKED
		if (matrice[l1][c1] < 100) {
			matrice[l1][c1] = 0;
		}
		if (matrice[l1][c1] < 100) {
			matrice[l1][c1 + 1] = 0;
		}
		if (matrice[l1][c1 + 2] < 100) {
			matrice[l1][c1 + 2] = 0;
		}
	}

	public static void clearCol(int l1, int c1) {    //REWORKED
		if (matrice[l1][c1] < 100) {
			matrice[l1][c1] = 0;
		}
		if (matrice[l1 + 1][c1] < 100) {
			matrice[l1 + 1][c1] = 0;
		}
		if (matrice[l1 + 2][c1] < 100) {
			matrice[l1 + 2][c1] = 0;
		}
	}

	public static boolean moveDown() {   //REWORKED
		if (stopCond()) {
			stabilize();
			checkLine();
			stoped=true;
			Game.repaint();
			return false;
		} else {
			moveLine(row + 2, col);
			moveLine(row + 1, col);
			moveLine(row, col);
			clearLine(row, col);
			adjust();
			row++;
			Game.repaint();
			return true;
		}
	}

	public static boolean moveRightCond(int line, int col) { // REWORKED
		for (int i = line; i < line + 3; i++) {
			for (int j = col; j < col + 3; j++) {
				if(matrice[i][j]<20 && matrice[i][j]>10){
					if (matrice[i][j] + matrice[i][j + 1] > 120) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean moveLeftCond(int line, int col) {  //  REWORKED
		for (int i = line; i < line + 3; i++) {
			for (int j = col; j < col + 3; j++) {
				if(matrice[i][j]>10 && matrice[i][j]<100){
					if (matrice[i][j] + matrice[i][j - 1] >120) {
					return false;
					}
				}
				
			}
		}
		return true;
	}

	public static void moveRight() {        //REWORKED
		if (moveRightCond(row, col)) {
			moveColRight(row, col + 2);
			moveColRight(row, col + 1);
			moveColRight(row, col);
			clearCol(row, col);
			adjust();
			col++;
			Game.repaint();
		}
	}

	public static void moveLeft() {         //REWORKED
		if (moveLeftCond(row, col)) {
			moveColLeft(row, col);
			moveColLeft(row, col + 1);
			moveColLeft(row, col + 2);
			clearCol(row, col + 2);
			adjust();
			col--;
			Game.repaint();
		}
	}

	public static void stabilize() {  //REWORKED
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				if (matrice[i][j] > 10 && matrice[i][j]<20) {
					matrice[i][j] = matrice[i][j]+100;
				}
			}
		}
		Game.repaint();
	}

	public static void elimLine(int line) {   //ALREADY GOOD
		for (int i = line; i > 0; i--) {
			for (int j = 3; j < 12; j++) {
				matrice[i][j] = matrice[i - 1][j];
			}
		}
		for (int i = 3; i < 12; i++) {
			matrice[0][i] = 0;
		}
		Main.score=Main.score+5;
		Game.repaint();
	}

	public static boolean checkLine() {  //REWORKED
		boolean full;
		for (int i = 0; i < 9; i++) {
			full=true;
			for (int j = 3; j < 12; j++) {
				if (matrice[i][j] == 0)
					full=false;
			}
			if(full)
			elimLine(i);
		}
		return true;
	}

	public static void adjust() {  //REWORKED
		for (int i = 0; i < 10; i++) {
			matrice[i][2] = 200;
			matrice[i][12] = 200;
		}
	}
	public static boolean shiftLineShapeCondition(){
		if(matrice[row+1][col]==14){
			if(matrice[row][col+1]==0 && matrice[row][col+2]==0){
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(matrice[row+1][col]==0 && matrice[row+2][col]==0)
			{
				return true;
			}
			else
				return false;
		}
	}
	public static void shiftLineShape(){
		if(matrice[row+1][col]>10 && matrice[row+1][col] < 100){
			matrice[row][col+1]=matrice[row][col];
			matrice[row][col+2]=matrice[row][col];
			matrice[row+1][col]=0;
			matrice[row+2][col]=0;
		}
		else
		{
			matrice[row+1][col]=matrice[row][col];
			matrice[row+2][col]=matrice[row][col];
			matrice[row][col+1]=0;
			matrice[row][col+2]=0;
		}
	}
	public static boolean shiftZShapeCondition(){
		if(matrice[row+1][col]==13){
			if(matrice[row][col+1]==0 && matrice[row+1][col+2]==0){
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(matrice[row+1][col]==0 && matrice[row+2][col+1]==0)
			{
				return true;
			}
			else
				return false;
		}
	}
	public static void shiftZShape(){
		if(matrice[row+1][col]>10 && matrice[row+1][col] < 100){
			matrice[row][col+1]=matrice[row][col];
			matrice[row+1][col+2]=matrice[row][col];
			matrice[row+1][col]=0;
			matrice[row+2][col+1]=0;
		}
		else
		{
			matrice[row+1][col]=matrice[row][col];
			matrice[row+2][col+1]=matrice[row][col];
			matrice[row][col+1]=0;
			matrice[row+1][col+2]=0;
		}
	}
	public static boolean shiftLShapeCondition(){
		if(matrice[row+1][col]==12){
			if(matrice[row][col+1]==0 && matrice[row][col+2]==0 && matrice[row+1][col+2]==0){
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(matrice[row+1][col]==0 && matrice[row+2][col]==0 && matrice[row+2][col+1]==0)
			{
				return true;
			}
			else
				return false;
		}
	}
	public static void shiftLShape(){
		if(matrice[row+1][col]>10 && matrice[row+1][col] < 100){
			matrice[row][col+1]=matrice[row][col];
			matrice[row][col+2]=matrice[row][col];
			matrice[row+1][col+2]=matrice[row][col];
			matrice[row+1][col]=0;
			matrice[row+2][col]=0;
			matrice[row+2][col+1]=0;
		}
		else
		{
			matrice[row+1][col]=matrice[row][col];
			matrice[row+2][col]=matrice[row][col];
			matrice[row+2][col+1]=matrice[row][col];
			matrice[row][col+1]=0;
			matrice[row][col+2]=0;
			matrice[row+1][col+2]=0;
		}
	}
	
	public static void shift(){
		int shapeForm=matrice[row][col];
		switch(shapeForm)
		{
		case 11: break;
		
		case 12:
			if(shiftLShapeCondition()){
				shiftLShape();
			}
			break;
		case 13:
			if(shiftZShapeCondition()){
				shiftZShape();
			}
			break;
		case 14:
			if(shiftLineShapeCondition()){
				shiftLineShape();
			}
			break;
		}
		Game.repaint();
	}
}
