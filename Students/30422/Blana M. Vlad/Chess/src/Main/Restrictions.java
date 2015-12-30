package Main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import pieces.Colors;
import pieces.Piece;

public class Restrictions {

	public static boolean kingSafety(Piece[][] chessBoard, Colors color,
			Controller controller) {
		List<Movement> list = new ArrayList<Movement>();
		controller.board.flipBoard(chessBoard);
		controller.whiteTurn = !controller.whiteTurn;
		if (color == Colors.WHITE) {
			list = controller.movementManager.generateAllMoves(chessBoard,
					Colors.BLACK, controller, false);
			for (Movement element : list) {
				controller.whiteKingPosition.source = element.source;

				if (element.equals(controller.whiteKingPosition)) {
					controller.whiteTurn = !controller.whiteTurn;
					controller.board.flipBoard(chessBoard);
					return false;
				}
			}

		} else {
			list = controller.movementManager.generateAllMoves(chessBoard,
					Colors.WHITE, controller, false);
			for (Movement element : list) {
				controller.blackKingPosition.source = element.source;
				if (element.equals(controller.blackKingPosition)) {
					controller.whiteTurn = !controller.whiteTurn;
					controller.board.flipBoard(chessBoard);
					return false;
				}
			}
		}
		controller.whiteTurn = !controller.whiteTurn;
		controller.board.flipBoard(chessBoard);
		// System.out.println(controller.whiteKingPosition.destination);

		return true;
	}

	public static boolean checkSquareSafety(int row, int column, Colors color,
			Piece[][] chessBoard, Controller controller) {
		boolean temp1, temp2, temp3, temp4;
		temp1 = controller.enableLeftWhiteCastle;
		temp2 = controller.enableRightWhiteCastle;
		temp3 = controller.enableLeftBlackCastle;
		temp4 = controller.enableRightBlackCastle;

		controller.enableLeftWhiteCastle = false;
		controller.enableRightWhiteCastle = false;
		controller.enableLeftBlackCastle = false;
		controller.enableRightBlackCastle = false;
		List<Movement> list = new ArrayList<Movement>();
		controller.board.flipBoard(chessBoard);
		controller.whiteTurn = !controller.whiteTurn;
		if (color == Colors.WHITE) {
			list = controller.movementManager.generateAllMoves(chessBoard,
					Colors.BLACK, controller, false);
			for (Movement element : list) {
				if (element.destination.x == row
						&& element.destination.y == column) {
					controller.whiteTurn = !controller.whiteTurn;
					controller.board.flipBoard(chessBoard);
					controller.enableLeftWhiteCastle = temp1;
					controller.enableRightWhiteCastle = temp2;
					controller.enableLeftBlackCastle = temp3;
					controller.enableRightBlackCastle = temp4;
					return false;
				}
			}

		} else {
			list = controller.movementManager.generateAllMoves(chessBoard,
					Colors.WHITE, controller, false);
			for (Movement element : list) {
				if (element.destination.x == row
						&& element.destination.y == column) {
					controller.whiteTurn = !controller.whiteTurn;
					controller.board.flipBoard(chessBoard);
					controller.enableLeftWhiteCastle = temp1;
					controller.enableRightWhiteCastle = temp2;
					controller.enableLeftBlackCastle = temp3;
					controller.enableRightBlackCastle = temp4;
					return false;
				}
			}
		}
		controller.whiteTurn = !controller.whiteTurn;
		controller.board.flipBoard(chessBoard);
		controller.enableLeftWhiteCastle = temp1;
		controller.enableRightWhiteCastle = temp2;
		controller.enableLeftBlackCastle = temp3;
		controller.enableRightBlackCastle = temp4;

		return true;
	}

	public static void isKingInCheck(Controller controller, Piece[][] chessBoard) {
		if (controller.whiteTurn == true) {
			if (kingSafety(chessBoard, Colors.WHITE, controller) == false) {
				JOptionPane.showMessageDialog(null, "Check!");
			}
		} else {
			if (kingSafety(chessBoard, Colors.BLACK, controller) == false) {
				JOptionPane.showMessageDialog(null, "Check!");
			}
		}

	}

	public static void isCheckMate(Controller controller, Piece[][] chessBoard) {
		List<Movement> list = new ArrayList<Movement>();
		if (controller.whiteTurn == false) {
			if (kingSafety(chessBoard, Colors.BLACK, controller) == false) {
				list = controller.movementManager.generateAllMoves(chessBoard,
						Colors.BLACK, controller, true);
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(null, "CheckMate!");
					System.exit(0);
				}
			}
		} else {
			if (kingSafety(chessBoard, Colors.WHITE, controller) == false) {
				list = controller.movementManager.generateAllMoves(chessBoard,
						Colors.WHITE, controller, true);
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(null, "CheckMate!");
					System.exit(0);
				}
			}
		}
	}
}
