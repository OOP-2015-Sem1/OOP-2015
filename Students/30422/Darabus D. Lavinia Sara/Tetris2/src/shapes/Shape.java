package shapes;

import java.util.Random;

public class Shape {

	enum Tetrominoes{ NoShape, IShape, JShape, LShape, OShape, SShape, Tshape, ZShape};
	
	private Tetrominoes piece;
	private int coordinates[][];
	private int coordinatesTable[][][];
	
	
	public void setShape(Tetrominoes shape) {
		coordinatesTable = new int[][][]{
			{{0, 0}, {0, 0}, {0, 0}, {0, 0}},//NoShape
			{{0, -1}, {0, 0}, {0, 1}, {0, 2}},//IShape
			{{-1, -1}, {0, -1}, {0, 0}, {0, 1}},//JShape
			{{0, 0}, {1, 0}, {0, 1}, {1, 1}},//LShape
			{{1, -1}, {0, -1}, {0, 0}, {0, 1}},//OShape
			{{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},//SShape
			{{-1, 0}, {0, 0}, {1, 0}, {0, 1}},//TShape
			{{0, -1}, {0, 0}, {1, 0}, {1, 1}}//ZShape
		};
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 2; j++){
				coordinates[i][j] = coordinatesTable[shape.ordinal()][i][j];
			}
		}
		
		piece = shape;
	}
	
	public Tetrominoes getShape(){
		return this.piece;
	}
	
	public Shape(){
		coordinates = new int[4][2];
		setShape(Tetrominoes.NoShape);
	}
	
	public void setX(int index, int x){
		coordinates[index][0] = x;
	}
	
	public int getX(int index){
		return coordinates[index][0];
		}
	
	public void setY(int index, int y){
		coordinates[0][index] = y;
	}
	
	public int getY(int index){
		return coordinates[0][index];
	}
	
	public void setRandomShape(){
		
		Random random = new Random();
		int randomValue = Math.abs(random.nextInt() % 7 + 1);
		Tetrominoes[] shapeValues = Tetrominoes.values();// array of tetromino enum elements
		setShape(shapeValues[randomValue]);
		
	}
	
	public Shape rotateRight(){
		if(piece == Tetrominoes.OShape){
			return this;
			}
		Shape rightRotatedShape = new Shape();
		rightRotatedShape.piece = piece;
		
		for(int i = 0; i < 4; i++){
			rightRotatedShape.setX(i, -getY(i));
			rightRotatedShape.setY(i, getX(i));
		}
		
		return rightRotatedShape;
	}
	
	public Shape rotateLeft(){
		if(piece == Tetrominoes.OShape){
			return this;
			}
		Shape leftRotatedShape = new Shape();
		leftRotatedShape.piece = piece;
		
		for(int i = 0; i < 4; i++){
			leftRotatedShape.setX(i, getY(i));
			leftRotatedShape.setY(i, -getX(i));
		}
		
		return leftRotatedShape;
	}

	
	
}
