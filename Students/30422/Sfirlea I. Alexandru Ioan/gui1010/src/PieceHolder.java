import java.awt.Color;
import java.util.ArrayList;

public class PieceHolder {
	 public static Piece[] hold= new Piece[19];
	 public static ArrayList<int[][]> pieces;
	 	
	    public static final int[][] P1 = { { 1 } };

	    public static final int[][] P2 = { { 2}, 
	                                       { 2} };

	    public static final int[][] P3 = { { 2, 2} };

	    public static final int[][] P4 = { { 3}, 
	                                       { 3}, 
	                                       { 3 }, 
	                                           };
	    
	    public static final int[][] P5 = { { 3, 3, 3 }};
	    
	    public static final int[][] P6 = { { 4, 4, 4, 4 }};
	    
	    public static final int[][] P7 = { { 4 }, 
                						   { 4 }, 
                						   { 4 }, 
                						   { 4 },
                							    };
	    
	    public static final int[][] P8 = { { 5, 5, 5, 5, 5 }, 
	    								       };

public static final int[][] P9 = { { 5 }, 
                				       { 5 }, 
                				       { 5 }, 
                				       { 5 },
                				       { 5 } };

public static final int[][] P10 = { { 3, 0 }, 
									{ 3, 3 }, 
									 };
	
public static final int[][] P11 = { { 0, 3}, 
									{ 3, 3 }, 
									 };

public static final int[][] P12 = { { 3, 3 }, 
									{ 3, 0 }, 
								   };

public static final int[][] P13 = { { 3, 3 }, 
									{ 0, 3 }, 
							         };

public static final int[][] P14 = { { 5, 5, 5 }, 
				   					{ 5, 0, 0 }, 
				   				    { 5, 0, 0 }, 
				   				     };
	    
public static final int[][] P15 = { { 5, 5, 5}, 
		   						        { 0, 0, 5}, 
		   						        { 0, 0, 5}, 
		   						         };

public static final int[][] P16 = { { 5, 0, 0}, 
									{ 5, 0, 0}, 
									{ 5, 5, 5 }, 
									 };

public static final int[][] P17 = { { 0, 0, 5 }, 
									{ 0, 0, 5}, 
									{ 5, 5, 5 }, 
									 };

public static final int[][] P18 = { { 4, 4 }, 
		   							{ 4, 4 }, 
		   							 };

public static final int[][] P19 = { { 9, 9, 9}, 
									{ 9, 9, 9 }, 
									{ 9, 9, 9 }, 
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
			hold[dimension++]=new Piece(new Color(130,18,229),PieceHolder.P1,1,1);
			hold[dimension++]=new Piece(new Color(84,218,6),PieceHolder.P2,2,1);
			hold[dimension++]=new Piece(new Color(84,218,6),PieceHolder.P3,1,2);
			hold[dimension++]=new Piece(new Color(255,204,0),PieceHolder.P4,3,1);
			hold[dimension++]=new Piece(new Color(225,204,0),PieceHolder.P5,1,3);
			hold[dimension++]=new Piece(new Color(148,21,118),PieceHolder.P6,1,4);
			hold[dimension++]=new Piece(new Color(148,21,118),PieceHolder.P7,4,1);
			hold[dimension++]=new Piece(new Color(152,0,0),PieceHolder.P8,1,5);
			hold[dimension++]=new Piece(new Color(152,0,0),PieceHolder.P9,5,1);
			hold[dimension++]=new Piece(new Color(44,148,57),PieceHolder.P10,2,2);
			hold[dimension++]=new Piece(new Color(44,148,57),PieceHolder.P11,2,2);
			hold[dimension++]=new Piece(new Color(44,148,57),PieceHolder.P12,2,2);
			hold[dimension++]=new Piece(new Color(44,148,57),PieceHolder.P13,2,2);
			hold[dimension++]=new Piece(new Color(29,212,240),PieceHolder.P14,3,3);
			hold[dimension++]=new Piece(new Color(29,212,240),PieceHolder.P15,3,3);
			hold[dimension++]=new Piece(new Color(29,212,240),PieceHolder.P16,3,3);
			hold[dimension++]=new Piece(new Color(29,212,240),PieceHolder.P17,3,3);
			hold[dimension++]=new Piece(new Color(10,255,67),PieceHolder.P18,2,2);
			hold[dimension++]=new Piece(new Color(10,255,223),PieceHolder.P19,3,3);
			dimension--;
	    }
	    public Piece generatePiece(){
	    	RandomNumbers random = RandomNumbers.getInstance();
				return PieceHolder.hold[random.getRandomNumber()];
	    }
	    

}
