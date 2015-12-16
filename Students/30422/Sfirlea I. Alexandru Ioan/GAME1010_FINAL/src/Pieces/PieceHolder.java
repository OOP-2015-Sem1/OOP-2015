package Pieces;

import java.awt.Color;
import java.util.ArrayList;

import Calculation.ColorPiker;

public class PieceHolder {
	 public static Piece[] hold= new Piece[19];
	 public static ArrayList<int[][]> pieces;
	 	
	    public static final int[][] P1 = { { 11 } };

	    public static final int[][] P2 = { { 22}, 
	                                       { 22} };

	    public static final int[][] P3 = { { 22, 22} };

	    public static final int[][] P4 = { { 33}, 
	                                       { 33}, 
	                                       { 33 }, 
	                                           };
	    
	    public static final int[][] P5 = { { 33, 33, 33 }};
	    
	    public static final int[][] P6 = { { 44, 44, 44, 44 }};
	    
	    public static final int[][] P7 = { { 44 }, 
                						   { 44 }, 
                						   { 44 }, 
                						   { 44 },
                							    };
	    
	    public static final int[][] P8 = { { 55, 55, 55, 55, 55 }, 
	    								       };

public static final int[][] P9 = { { 55 }, 
                				       { 55 }, 
                				       { 55 }, 
                				       { 55 },
                				       { 55 } };

public static final int[][] P10 = { { 63, 0 }, 
									{ 63, 63 }, 
									 };
	
public static final int[][] P11 = { { 0, 63}, 
									{ 63, 63 }, 
									 };

public static final int[][] P12 = { { 63, 63 }, 
									{ 63, 0 }, 
								   };

public static final int[][] P13 = { { 63, 63 }, 
									{ 0, 63 }, 
							         };

public static final int[][] P14 = { { 75,75, 75 }, 
				   					{ 75, 0, 0 }, 
				   				    { 75, 0, 0 }, 
				   				     };
	    
public static final int[][] P15 = { { 75, 75, 75}, 
		   						        { 0, 0, 75}, 
		   						        { 0, 0, 75}, 
		   						         };

public static final int[][] P16 = { { 75, 0, 0}, 
									{ 75, 0, 0}, 
									{ 75, 75, 75 }, 
									 };

public static final int[][] P17 = { { 0, 0, 75 }, 
									{ 0, 0, 75}, 
									{ 75, 75, 75 }, 
									 };

public static final int[][] P18 = { { 84, 84 }, 
		   							{ 84, 84 }, 
		   							 };

public static final int[][] P19 = { { 99, 99, 99}, 
									{ 99, 99, 99 }, 
									{ 99, 99, 99 }, 
								 };


	    static {
	        pieces = new ArrayList<int[][]>();
	        pieces.add(P1);
	        pieces.add(P2);
	        pieces.add(P3);
	        pieces.add(P4);
	        pieces.add(P5);
	        pieces.add(P6);
	        pieces.add(P7);
	        pieces.add(P8);
	        pieces.add(P9);
	        pieces.add(P10);
	        pieces.add(P11);
	        pieces.add(P12);
	        pieces.add(P13);
	        pieces.add(P14);
	        pieces.add(P15);
	        pieces.add(P16);
	        pieces.add(P17);
	        pieces.add(P18);
	        pieces.add(P19);
	        
	    }
	    public static void storePiece (Piece[] hold){
			int dimension=0;
			Color[] colorArray = new Color[10];
			ColorPiker.storeColor(colorArray);
			
			hold[dimension++]=new Piece(colorArray[1],PieceHolder.P1,1,1);
			hold[dimension++]=new Piece(colorArray[2],PieceHolder.P2,2,1);
			hold[dimension++]=new Piece(colorArray[2],PieceHolder.P3,1,2);
			hold[dimension++]=new Piece(colorArray[3],PieceHolder.P4,3,1);
			hold[dimension++]=new Piece(colorArray[3],PieceHolder.P5,1,3);
			hold[dimension++]=new Piece(colorArray[4],PieceHolder.P6,1,4);
			hold[dimension++]=new Piece(colorArray[4],PieceHolder.P7,4,1);
			hold[dimension++]=new Piece(colorArray[5],PieceHolder.P8,1,5);
			hold[dimension++]=new Piece(colorArray[5],PieceHolder.P9,5,1);
			hold[dimension++]=new Piece(colorArray[6],PieceHolder.P10,2,2);
			hold[dimension++]=new Piece(colorArray[6],PieceHolder.P11,2,2);
			hold[dimension++]=new Piece(colorArray[6],PieceHolder.P12,2,2);
			hold[dimension++]=new Piece(colorArray[6],PieceHolder.P13,2,2);
			hold[dimension++]=new Piece(colorArray[7],PieceHolder.P14,3,3);
			hold[dimension++]=new Piece(colorArray[7],PieceHolder.P15,3,3);
			hold[dimension++]=new Piece(colorArray[7],PieceHolder.P16,3,3);
			hold[dimension++]=new Piece(colorArray[7],PieceHolder.P17,3,3);
			hold[dimension++]=new Piece(colorArray[8],PieceHolder.P18,2,2);
			hold[dimension++]=new Piece(colorArray[9],PieceHolder.P19,3,3);
			dimension--;
	    }
	    public static Piece generateRandomPiece(){
	    	RandomNumbers random = RandomNumbers.getInstance();
				return PieceHolder.hold[random.getRandomNumber()];
	    }
	    

}
