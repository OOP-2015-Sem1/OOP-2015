package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import PieceManipulation.Colors;
import PieceManipulation.Piece;
import PieceManipulation.Pieces;

public class UserInterface extends JPanel implements MouseListener,
		MouseMotionListener {
	static int mouseX, mouseY, newMouseX, newMouseY, squareSize = 100;

	public UserInterface() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		Piece[][] chessBoard = MainChess.board.getBoard();
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
		if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
			// if inside the board
			mouseX = e.getX();
			mouseY = e.getY();
			repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		Piece[][] chessBoard = MainChess.board.getBoard();
		if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
			// if inside the board
			newMouseX = e.getX();
			newMouseY = e.getY();
			if (e.getButton() == MouseEvent.BUTTON1) {
				String dragMove = "" + mouseY / squareSize + mouseX
						/ squareSize + newMouseY / squareSize + newMouseX
						/ squareSize
				// + chessBoard[newMouseY / squareSize][newMouseX
				// / squareSize]
				;

				System.out.println(dragMove);
				if (Movement.checkValidMove(dragMove) == true) {

					Movement.makeMove(dragMove, chessBoard);

					MainChess.whiteTurn = !MainChess.whiteTurn;
					MainChess.board.flipBoard(chessBoard);
					if (MainChess.whiteTurn == false) {
						for (int i = 0; i <= 7; i++) {
							for (int j = 0; j <= 7; j++) {
								if (chessBoard[i][j].getType() == Pieces.KING
										&& chessBoard[i][j].getColor() == Colors.WHITE) {
									MainChess.whiteKingX = String.valueOf(i);
									MainChess.whiteKingY = String.valueOf(j);
								}
							}
						}
					} else {
						for (int i = 0; i <= 7; i++) {
							for (int j = 0; j <= 7; j++) {
								if (chessBoard[i][j].getType() == Pieces.KING
										&& chessBoard[i][j].getColor() == Colors.BLACK) {
									MainChess.blackKingX = String.valueOf(i);
									MainChess.blackKingY = String.valueOf(j);
								}
							}
						}
					}
					repaint();
				}

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
