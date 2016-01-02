package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import pieces.Colors;
import pieces.Piece;
import pieces.Pieces;

public class UserInterface extends JPanel implements MouseListener,
		MouseMotionListener {
	int mouseX, mouseY, newMouseX, newMouseY, squareSize = 100;
	private Movement dragMove = new Movement();
	private Controller controller = new Controller();
	private boolean moveHighlightMatrix[][] = new boolean[8][8];

	public UserInterface() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		Piece[][] chessBoard = controller.board.getBoard();

		super.paintComponent(g);
		this.setBackground(new Color(80, 80, 80));

		for (int i = 0; i < 64; i += 2) {
			g.setColor(new Color(255, 200, 100));
			g.fillRect(((i % 8) + (i / 8) % 2) * squareSize, (i / 8)
					* squareSize, squareSize, squareSize);
			g.setColor(new Color(150, 50, 30));
			g.fillRect((((i + 1) % 8) - ((i + 1) / 8) % 2) * squareSize,
					(i / 8) * squareSize, squareSize, squareSize);
		}
		for (int i = 0; i < 64; i++) {
			if (moveHighlightMatrix[i / 8][i % 8] == true) {
				g.setColor(new Color(12, 200, 12, 100));
				g.fillRect(i % 8 * squareSize, (i / 8) * squareSize,
						squareSize, squareSize);

			}
		}
		Image chessPieceImage;
		chessPieceImage = new ImageIcon("ChessPieces.png").getImage();

		for (int i = 0; i < 64; i++) {
			int j = -1, k = -1;
			if (chessBoard[i / 8][i % 8].getType() == Pieces.PAWN
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 5;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.ROOK
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 2;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.KNIGHT
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 4;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.BISHOP
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 3;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.QUEEN
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 1;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.KING
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 0;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.PAWN
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 5;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.ROOK
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 2;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.KNIGHT
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 4;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.BISHOP
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 3;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.QUEEN
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 1;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == Pieces.KING
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 0;
				k = 1;
			}
			if (j != -1 && k != -1) {
				g.drawImage(chessPieceImage, (i % 8) * squareSize, (i / 8)
						* squareSize, (i % 8 + 1) * squareSize, (i / 8 + 1)
						* squareSize, j * 64, k * 64, (j + 1) * 64,
						(k + 1) * 64, this);
			}
		}

	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		Piece[][] chessBoard = controller.board.getBoard();
		if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
			// if inside the board
			for (int i = 0; i <= 7; i++) {
				for (int j = 0; j <= 7; j++) {
					moveHighlightMatrix[i][j] = false;
				}
			}
			mouseX = e.getX();
			mouseY = e.getY();
			int row, column;
			row = mouseY / squareSize;
			column = mouseX / squareSize;
			List<Movement> list = new ArrayList<Movement>();
			list = chessBoard[row][column].possibleMove(row, column,
					chessBoard, true, controller);
			for (Movement element : list) {
				moveHighlightMatrix[element.destination.x][element.destination.y] = true;
			}
			repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		Piece[][] chessBoard = controller.board.getBoard();
		if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
			// if inside the board
			newMouseX = e.getX();
			newMouseY = e.getY();
			for (int i = 0; i <= 7; i++) {
				for (int j = 0; j <= 7; j++) {
					moveHighlightMatrix[i][j] = false;
				}
			}
			repaint();
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (chessBoard[newMouseY / squareSize][newMouseX / squareSize] != controller.emptySpace) {
					dragMove.setMove(mouseY / squareSize, mouseX / squareSize,
							newMouseY / squareSize, newMouseX / squareSize,
							true);
				} else {
					dragMove.setMove(mouseY / squareSize, mouseX / squareSize,
							newMouseY / squareSize, newMouseX / squareSize,
							false);
				}
				System.out.println(dragMove.encodeMoveToString());
				if (controller.movementManager.checkValidMove(dragMove,
						controller) == true) {

					controller.movementManager.makeMove(dragMove, chessBoard,
							controller);

					controller.whiteTurn = !controller.whiteTurn;
					controller.board.flipBoard(chessBoard);
					if (controller.whiteTurn == false) {
						for (int i = 0; i <= 7; i++) {
							for (int j = 0; j <= 7; j++) {
								moveHighlightMatrix[i / 8][i % 8] = false;
								if (chessBoard[i][j].getType() == Pieces.KING
										&& chessBoard[i][j].getColor() == Colors.WHITE) {
									controller.whiteKingPosition.destination.x = i;
									controller.whiteKingPosition.destination.y = j;
								}
							}
						}
					} else {
						for (int i = 0; i <= 7; i++) {
							for (int j = 0; j <= 7; j++) {
								if (chessBoard[i][j].getType() == Pieces.KING
										&& chessBoard[i][j].getColor() == Colors.BLACK) {
									controller.blackKingPosition.destination.x = i;
									controller.blackKingPosition.destination.y = j;
								}
							}
						}
					}
					//

					repaint();

					//
				}
				Restrictions.isCheckMate(controller, chessBoard);
				Restrictions.isKingInCheck(controller, chessBoard);
			}

		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}
