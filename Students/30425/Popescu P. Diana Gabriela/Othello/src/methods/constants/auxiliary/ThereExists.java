package methods.constants.auxiliary;

import game.BoardConfiguration;

public class ThereExists {

	public boolean thereExistsAtLeastOnePossibleMoveForThisColor(BoardConfiguration boardConfiguration, int color, int ROWS, int COLS) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(boardConfiguration, i, j, color, ROWS, COLS))
					return true;
			}
		}
		return false;
	}

	public boolean thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(
			BoardConfiguration boardConfiguration, int i, int j, int colorToBePut, int ROWS, int COLS) {

		if (isGreenAndHasAtLeastOneNeighborOfOppositeColor(boardConfiguration, i, j, colorToBePut, ROWS, COLS)) {
			int colorTheNeighborShouldBe;
			if (colorToBePut == Constants.WHITE) {
				colorTheNeighborShouldBe = Constants.BLACK;
			} else {
				colorTheNeighborShouldBe = Constants.WHITE;
			}
			// horizontal right
			if (thereExistChangeableTokensOnHorizontalRight(boardConfiguration, i, j, colorTheNeighborShouldBe, colorToBePut, ROWS, COLS)) {
				return true;
			}
			// horizontal left
			if (thereExistChangeableTokensOnHorizontalLeft(boardConfiguration, i, j, colorTheNeighborShouldBe, colorToBePut)) {
				return true;
			}
			// vertical up
			if (thereExistChangeableTokensOnVerticalUp(boardConfiguration, i, j, colorTheNeighborShouldBe, colorToBePut)) {
				return true;
			}
			// vertical down
			if (thereExistChangeableTokensOnVerticalDown(boardConfiguration, i, j, colorTheNeighborShouldBe, colorToBePut, ROWS, COLS)) {
				return true;
			}
			// leading diagonal right
			if (thereExistChangeableTokensOnLeadingRight(boardConfiguration, i, j, colorTheNeighborShouldBe, colorToBePut, ROWS, COLS)) {
				return true;
			}
			// leading diagonal left
			if (thereExistChangeableTokensOnLeadingLeft(boardConfiguration, i, j, colorTheNeighborShouldBe, colorToBePut)) {
				return true;
			}
			// trailing diagonal right
			if (thereExistChangeableTokensOnTrailingRight(boardConfiguration, i, j, colorTheNeighborShouldBe, colorToBePut, ROWS, COLS)) {
				return true;
			}
			// trailing diagonal left
			if (thereExistChangeableTokensOnTrailingLeft(boardConfiguration, i, j, colorTheNeighborShouldBe, colorToBePut, ROWS, COLS)) {
				return true;
			}
		}
		return false;
	}

	public boolean thereExistChangeableTokensOnHorizontalRight(BoardConfiguration boardConfiguration, int i, int j,
			int colorTheNeighborShouldBe, int colorToBePut, int ROWS, int COLS) {
		if(existsHorizontalRightNeighbor(boardConfiguration, i, j, ROWS, COLS)){
			if(getHorizontalRightNeighbor(boardConfiguration, i, j)==colorTheNeighborShouldBe){
				int searchTheOtherColor=j+1;
				while(existsHorizontalRightNeighbor(boardConfiguration, i, searchTheOtherColor, ROWS, COLS) 
						&& getHorizontalRightNeighbor(boardConfiguration, i, searchTheOtherColor) != Constants.GREEN){
					if(getHorizontalRightNeighbor(boardConfiguration, i, searchTheOtherColor) == colorToBePut){
						System.out.println("horizontalRight OK");
						return true;
					}
					searchTheOtherColor++;
				}
			}
		}
		return false;
	}

	public boolean thereExistChangeableTokensOnHorizontalLeft(BoardConfiguration boardConfiguration, int i, int j,
			int colorTheNeighborShouldBe, int colorToBePut) {
		if(existsHorizontalLeftNeighbor(boardConfiguration, i, j)){
			if(getHorizontalLeftNeighbor(boardConfiguration, i, j)==colorTheNeighborShouldBe){
				int searchTheOtherColor=j-1;
				while(existsHorizontalLeftNeighbor(boardConfiguration, i, searchTheOtherColor) 
						&& getHorizontalLeftNeighbor(boardConfiguration, i, searchTheOtherColor) != Constants.GREEN){
					if(getHorizontalLeftNeighbor(boardConfiguration, i, searchTheOtherColor) == colorToBePut){
						System.out.println("horizontalLeft OK");
						return true;
					}
					searchTheOtherColor--;
				}
			}
		}
		return false;
	}

	public boolean thereExistChangeableTokensOnVerticalUp(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe, int colorToBePut) {
		if(existsVerticalUpNeighbor(boardConfiguration, i, j)){
			if(getVerticalUpNeighbor(boardConfiguration, i, j)==colorTheNeighborShouldBe){
				int searchTheOtherColor=i-1;
				while(existsVerticalUpNeighbor(boardConfiguration, searchTheOtherColor, j) 
						&& getVerticalUpNeighbor(boardConfiguration, searchTheOtherColor, j) != Constants.GREEN){
					if(getVerticalUpNeighbor(boardConfiguration, searchTheOtherColor, j) == colorToBePut){
						System.out.println("verticalUp OK");
						return true;
					}
					searchTheOtherColor--;
				}
			}
		}
		return false;
	}

	public boolean thereExistChangeableTokensOnVerticalDown(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe, int colorToBePut, int ROWS, int COLS) {
		if(existsVerticalDownNeighbor(boardConfiguration, i, j, ROWS, COLS)){
			if(getVerticalDownNeighbor(boardConfiguration, i, j) == colorTheNeighborShouldBe){
				int searchTheOtherColor=i+1;
				while(existsVerticalDownNeighbor(boardConfiguration, searchTheOtherColor, j, ROWS, COLS)
						&& getVerticalDownNeighbor(boardConfiguration, searchTheOtherColor, j) != Constants.GREEN){
					if(getVerticalDownNeighbor(boardConfiguration, searchTheOtherColor, j) == colorToBePut){
						System.out.println("verticalDown OK");
						return true;
					}
					searchTheOtherColor++;
				}
			}
		}
		return false;
	}

	public boolean thereExistChangeableTokensOnLeadingRight(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe, int colorToBePut, int ROWS, int COLS) {
		if(existsLeadingRightNeighbor(boardConfiguration, i, j, ROWS, COLS)){
			if(getLeadingRightNeighbor(boardConfiguration, i, j) == colorTheNeighborShouldBe){
				int searchTheOtherColor = i+1;
				int searchTheOtherColorSecond = j+1;
				while (existsLeadingRightNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond, ROWS, COLS)
						&& getLeadingRightNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond) != Constants.GREEN) {
					if(getLeadingRightNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond) == colorToBePut){
						System.out.println("leadingRight OK");
						return true;
					}
					searchTheOtherColor++;
					searchTheOtherColorSecond++;
				}
			}
		}
		return false;
	}

	public boolean thereExistChangeableTokensOnLeadingLeft(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe, int colorToBePut) {
		if(existsLeadingLeftNeighbor(boardConfiguration, i, j)){
			if(getLeadingLeftNeighbor(boardConfiguration, i, j) == colorTheNeighborShouldBe){
				int searchTheOtherColor = i-1;
				int searchTheOtherColorSecond = j-1;
				while (existsLeadingLeftNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond)
						&& getLeadingLeftNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond) != Constants.GREEN) {
					if(getLeadingLeftNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond) == colorToBePut){
						System.out.println("leadingLeft OK");
						return true;
					}
					searchTheOtherColor--;
					searchTheOtherColorSecond--;
				}
			}
		}
		return false;
	}

	public boolean thereExistChangeableTokensOnTrailingRight(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe, int colorToBePut, int ROWS, int COLS) {
		if(existsTrailingRightNeighbor(boardConfiguration, i, j, ROWS, COLS)){
			if(getTrailingRightNeighbor(boardConfiguration, i, j) == colorTheNeighborShouldBe){
				int searchTheOtherColor = i-1;
				int searchTheOtherColorSecond = j+1;
				while (existsTrailingRightNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond, ROWS, COLS)
						&& getTrailingRightNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond) != Constants.GREEN) {
					if(getTrailingRightNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond) == colorToBePut){
						System.out.println("trailingRight OK");
						return true;
					}
					searchTheOtherColor--;
					searchTheOtherColorSecond++;
				}
			}
		}
		return false;
	}

	public boolean thereExistChangeableTokensOnTrailingLeft(BoardConfiguration boardConfiguration, int i, int j, int colorTheNeighborShouldBe, int colorToBePut, int ROWS, int COLS) {
		if(existsTrailingLeftNeighbor(boardConfiguration, i, j, ROWS, COLS)){
			if(getTrailingLeftNeighbor(boardConfiguration, i, j) == colorTheNeighborShouldBe){
				int searchTheOtherColor = i+1;
				int searchTheOtherColorSecond = j-1;
				while (existsTrailingLeftNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond, ROWS, COLS)
						&& getTrailingLeftNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond) != Constants.GREEN) {
					if(getTrailingLeftNeighbor(boardConfiguration, searchTheOtherColor, searchTheOtherColorSecond) == colorToBePut){
						System.out.println("trailingLeft OK");
						return true;
					}
					searchTheOtherColor++;
					searchTheOtherColorSecond--;
				}
			}
		}
		return false;
	}

	public boolean isGreenAndHasAtLeastOneNeighborOfOppositeColor(BoardConfiguration boardConfiguration, int i,
			int j, int colorToBePut, int ROWS, int COLS) {
		if (boardConfiguration.getBoard()[i][j] == Constants.GREEN) {
			if (greenHasAtLeastOneNeighborOfOppositeColor(boardConfiguration, i, j, colorToBePut, ROWS, COLS)) {
				return true;
			}
		}
		return false;
	}

	public boolean greenHasAtLeastOneNeighborOfOppositeColor(BoardConfiguration boardConfiguration, int i, int j,
			int colorToBePut, int ROWS, int COLS) {
		int positionI;
		int positionJ;
		for (int aux1 = 0; aux1 < 3; aux1++) {
			for (int aux2 = 0; aux2 < 3; aux2++) {
				positionI=i-1+aux1;
				positionJ=j-1+aux2;
				if ((positionI >= 0 && positionI < ROWS)
						&& (positionJ >= 0 && positionJ < COLS) && !(positionI==i && positionJ==j)) {
					if (boardConfiguration.getBoard()[positionI][positionJ] != Constants.GREEN
							&& boardConfiguration.getBoard()[positionI][positionJ] != colorToBePut) {
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean existsHorizontalRightNeighbor(BoardConfiguration boardConfiguration, int i, int j, int ROWS, int COLS){
		if(j+1 < COLS){
			return true;
		}
		return false;
	}
	private int getHorizontalRightNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		return boardConfiguration.getBoard()[i][j+1];
	}
	private boolean existsHorizontalLeftNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		if(j-1 >= 0){
			return true;
		}
		return false;
	}
	private int getHorizontalLeftNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		return boardConfiguration.getBoard()[i][j-1];
	}
	//
	private boolean existsVerticalUpNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		if(i-1 >= 0){
			return true;
		}
		return false;
	}
	private int getVerticalUpNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		return boardConfiguration.getBoard()[i-1][j];
	}
	private boolean existsVerticalDownNeighbor(BoardConfiguration boardConfiguration, int i, int j, int ROWS, int COLS){
		if(i+1 < ROWS){
			return true;
		}
		return false;
	}
	private int getVerticalDownNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		return boardConfiguration.getBoard()[i+1][j];
	}
	//
	private boolean existsLeadingRightNeighbor(BoardConfiguration boardConfiguration, int i, int j, int ROWS, int COLS){
		if(i+1 < ROWS && j+1 < COLS){
			return true;
		}
		return false;
	}
	private int getLeadingRightNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		return boardConfiguration.getBoard()[i+1][j+1];
	}
	private boolean existsLeadingLeftNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		if(i-1 >=0 && j-1 >=0){
			return true;
		}
		return false;
	}
	private int getLeadingLeftNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		return boardConfiguration.getBoard()[i-1][j-1];
	}
	//
	private boolean existsTrailingRightNeighbor(BoardConfiguration boardConfiguration, int i, int j, int ROWS, int COLS){
		if(i-1 >= 0 && j+1 < COLS){
			return true;
		}
		return false;
	}
	private int getTrailingRightNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		return boardConfiguration.getBoard()[i-1][j+1];
	}
	private boolean existsTrailingLeftNeighbor(BoardConfiguration boardConfiguration, int i, int j, int ROWS, int COLS){
		if(i+1 < ROWS && j-1 >=0){
			return true;
		}
		return false;
	}
	private int getTrailingLeftNeighbor(BoardConfiguration boardConfiguration, int i, int j){
		return boardConfiguration.getBoard()[i+1][j-1];
	}
}
/*
public boolean thereExistsAtLeastOnePossibleMoveForRandomColor(BoardConfiguration boardConfiguration) {
	for (int i = 0; i < ROWS; i++) {
		for (int j = 0; j < COLS; j++) {
			if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(boardConfiguration, i, j,
					Constants.WHITE))
				return true;
			if (thereExistsAtLeastOnePossibleMoveForThisColorAndThisPosition(boardConfiguration, i, j,
					Constants.BLACK))
				return true;
		}
	}
	return false;
}
*/