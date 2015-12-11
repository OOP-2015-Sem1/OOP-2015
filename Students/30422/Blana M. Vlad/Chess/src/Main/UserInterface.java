package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Pieces.Colors;
import Pieces.ListOfPieces;
import Pieces.Piece;

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
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.PAWN
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 5;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.ROOK
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 2;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.KNIGHT
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 4;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.BISHOP
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 3;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.QUEEN
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 1;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.KING
					&& chessBoard[i / 8][i % 8].getColor() == Colors.WHITE) {
				j = 0;
				k = 0;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.PAWN
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 5;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.ROOK
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 2;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.KNIGHT
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 4;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.BISHOP
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 3;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.QUEEN
					&& chessBoard[i / 8][i % 8].getColor() == Colors.BLACK) {
				j = 1;
				k = 1;
			}
			if (chessBoard[i / 8][i % 8].getType() == ListOfPieces.KING
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

		/*
		 * g.setColor(new Color(255, 255, 255)); g.fillRect(x - 20, y - 20, 40,
		 * 40); g.drawString("PUla", x, y); Image chessPieceImage;
		 * chessPieceImage = new ImageIcon("ChessPieces.png").getImage();
		 * g.drawImage(chessPieceImage, x, y, x + 64, y + 64, 0, 0, 64, 64,
		 * this);
		 */

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

				// System.out.println(userPosibilities.replaceAll(dragMove,
				// ""));
				if (MainChess.possibleMove(dragMove) == true) {
					// if valid move
					MainChess.makeMove(dragMove);
					MainChess.whiteTurn = !MainChess.whiteTurn;
					// Board.flipBoard(MainChess.board);
				}

			}
			repaint();
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
