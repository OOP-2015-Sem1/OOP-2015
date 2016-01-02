
package Functionality;

import UserInterface.TilesPanel;
import static Main.ValuesToWorkWith.bet;

public class Winnings {

	private int[] spinQuery = new int[2];
	private int[] result = new int[2];
	private WinningPlanValues plan = new WinningPlanValues();
	private int[][] valueToMultiplyWithBet = new int[8][5];

	private String queryForTileType(int cod) {

		String type = new String();
		switch (cod) {

		case 0:
			type = "stars";
			break;
		case 1:
			type = "lemons";
			break;
		case 2:
			type = "cherries";
			break;
		case 3:
			type = "plums";
			break;
		case 4:
			type = "oranges";
			break;
		case 5:
			type = "melons";
			break;
		case 6:
			type = "grapes";
			break;
		case 7:
			type = "sevens";
			break;
		default:
			type = "invalid type";
			break;
		}

		return type;
	}

	private int[] oneWinningLine(int lineNumber) {

		int matchingIterator = 1;
		int code = 8;
		boolean match = true;
		int tileIterator = 0;

		while (match && tileIterator < 4) {
			if (TilesPanel.imageCode[lineNumber][tileIterator] == TilesPanel.imageCode[lineNumber][tileIterator + 1]) {
				matchingIterator++;
				tileIterator++;

			} else {
				match = false;
			}
		}

		code = TilesPanel.imageCode[lineNumber][tileIterator];
		spinQuery[0] = matchingIterator;
		spinQuery[1] = code;
		return spinQuery;

	}

	private int[] ascendingLine() {

		int matchingIterator = 1;
		int code = 8;
		boolean match = true;

		if (TilesPanel.imageCode[2][0] == TilesPanel.imageCode[1][1] && match) {
			matchingIterator++;
		} else {
			match = false;
		}

		if (TilesPanel.imageCode[1][1] == TilesPanel.imageCode[0][2] && match) {
			matchingIterator++;
		} else {
			match = false;
		}

		if (TilesPanel.imageCode[0][2] == TilesPanel.imageCode[1][3] && match) {
			matchingIterator++;
		} else {
			match = false;
		}

		if (TilesPanel.imageCode[1][3] == TilesPanel.imageCode[2][4] && match) {
			matchingIterator++;
		} else {
			match = false;
		}

		code = TilesPanel.imageCode[2][0];
		spinQuery[0] = matchingIterator;
		spinQuery[1] = code;
		return spinQuery;
	}

	private int[] descendingLine() {

		int matchingIterator = 1;
		int code = 8;
		boolean match = true;

		if (TilesPanel.imageCode[0][0] == TilesPanel.imageCode[1][1] && match) {
			matchingIterator++;
		} else {
			match = false;
		}

		if (TilesPanel.imageCode[1][1] == TilesPanel.imageCode[2][2] && match) {
			matchingIterator++;
		} else {
			match = false;
		}

		if (TilesPanel.imageCode[2][2] == TilesPanel.imageCode[1][3] && match) {
			matchingIterator++;
		} else {
			match = false;
		}

		if (TilesPanel.imageCode[1][3] == TilesPanel.imageCode[0][4] && match) {
			matchingIterator++;
		} else {
			match = false;
		}

		code = TilesPanel.imageCode[0][0];
		spinQuery[0] = matchingIterator;
		spinQuery[1] = code;
		return spinQuery;
	}

	public int lookForWinnings(int nrOfLines) {

		int sum = 0;
		valueToMultiplyWithBet = plan.getWinPlan();

		// result[0] has the number of matching tiles
		// result[1] has the code of the tile type

		if (nrOfLines == 1) {

			System.out.println();

			result[0] = oneWinningLine(1)[0];
			result[1] = oneWinningLine(1)[1];
			System.out.println("MidLine: " + result[0] + " " + queryForTileType(result[1]) + " match");
			System.out.println("Bet x " + valueToMultiplyWithBet[result[1]][result[0] - 1]);
			sum = bet * valueToMultiplyWithBet[result[1]][result[0] - 1];

		}

		if (nrOfLines == 3) {

			System.out.println();

			for (int k = 0; k < 3; k++) {
				result[0] = oneWinningLine(k)[0];
				result[1] = oneWinningLine(k)[1];
				System.out.println("Line " + k + ": " + result[0] + " " + queryForTileType(result[1]) + " match");
				System.out.println("Bet x " + valueToMultiplyWithBet[result[1]][result[0] - 1]);
				sum += bet * valueToMultiplyWithBet[result[1]][result[0] - 1];
			}

		}

		if (nrOfLines == 5) {
			System.out.println();

			sum = lookForWinnings(3);

			result[0] = ascendingLine()[0];
			result[1] = ascendingLine()[1];
			System.out.println("AscendingLine: " + result[0] + " " + queryForTileType(result[1]) + " match");
			System.out.println("Bet x " + valueToMultiplyWithBet[result[1]][result[0] - 1]);
			sum += bet * valueToMultiplyWithBet[result[1]][result[0] - 1];

			result[0] = descendingLine()[0];
			result[1] = descendingLine()[1];
			System.out.println("DescendingLine: " + result[0] + " " + queryForTileType(result[1]) + " match");
			System.out.println("Bet x " + valueToMultiplyWithBet[result[1]][result[0] - 1]);
			sum += bet * valueToMultiplyWithBet[result[1]][result[0] - 1];

		}

		System.out.println("Winning should be: " + sum);
		return sum;
	}
}
