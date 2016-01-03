package com.bogdan.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.bogdan.model.Mill;
import com.bogdan.model.Piece;
import com.bogdan.model.PieceType;
import com.bogdan.model.PlayerType;

public class MorrisMouseListener implements MouseListener, MouseMotionListener {
	private static final int NR_OF_PIECES = 9;
	private Board board;
	private boolean playerOneMill;
	private boolean playerTwoMill;
	private Piece dragPiece;
	private int dragOffsetX;
	private int dragOffsetY;
	private int originalX;
	private int originalY;

	public MorrisMouseListener(Board board) {
		this.board = board;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getPoint().x;
		int y = e.getPoint().y - 50;
		// System.out.println(x + "-----" + y);
		// board.updateStatusMessage("Please click on an actual item");

		for (Piece piece : board.getPieces()) {
			if (mouseOverPiece(piece, x, y)) {
				if (piece.getPieceType().equals(PieceType.DEFAULT)) {
					if (board.getCurrentPlayer().equals(PlayerType.Player1)
							&& board.getPlayerOne().getAvailablePieces() < NR_OF_PIECES) {
						board.getPlayerOne().increasePieces();
						piece.setPieceIcon(PieceType.WHITE);
						if (isMill(PieceType.WHITE)) {
							board.updateStatusMessage("Remove a black piece from the board");
							playerOneMill = true;
						} else {
							board.setCurrentPlayer(PlayerType.Player2);
							board.updateStatusMessage(board.getPlayerTwo().getPlayerName() + "'s turn");
						}
					} else if (board.getCurrentPlayer().equals(PlayerType.Player2)
							&& board.getPlayerTwo().getAvailablePieces() < NR_OF_PIECES) {
						piece.setPieceIcon(PieceType.BLACK);
						board.getPlayerTwo().increasePieces();
						if (isMill(PieceType.BLACK)) {
							board.updateStatusMessage("Remove a white piece from the board");
							playerTwoMill = true;
						} else {
							board.setCurrentPlayer(PlayerType.Player1);
							board.updateStatusMessage(board.getPlayerOne().getPlayerName() + "'s turn");
						}
					}
				} else if (piece.getPieceType().equals(PieceType.BLACK) && playerOneMill) {
					piece.setPieceIcon(PieceType.DEFAULT);
					board.getPlayerTwo().decreasePieces();
					playerOneMill = false;
					board.setCurrentPlayer(PlayerType.Player2);
					if (board.getPlayerTwo().hasLost()) {
						board.updateStatusMessage(board.getPlayerOne().getPlayerName() + " has won !!!");
					} else {
						board.updateStatusMessage(board.getPlayerTwo().getPlayerName() + "'s turn");
					}
				} else if (piece.getPieceType().equals(PieceType.WHITE) && playerTwoMill) {
					piece.setPieceIcon(PieceType.DEFAULT);
					board.getPlayerOne().decreasePieces();
					playerTwoMill = false;
					board.setCurrentPlayer(PlayerType.Player1);
					if (board.getPlayerOne().hasLost()) {
						board.updateStatusMessage(board.getPlayerTwo().getPlayerName() + " has won !!!");
					} else {
						board.updateStatusMessage(board.getPlayerOne().getPlayerName() + "'s turn");
					}
				} else if ((board.getCurrentPlayer().equals(PlayerType.Player1))
						&& (piece.getPieceType().equals(PieceType.WHITE))) {
					dragPiece(piece, x, y);
				} else if ((board.getCurrentPlayer().equals(PlayerType.Player2))
						&& (piece.getPieceType().equals(PieceType.BLACK))) {
					dragPiece(piece, x, y);
				}
			}
		}
	}

	private void dragPiece(Piece piece, int x, int y) {
		this.dragOffsetX = x - piece.getX();
		this.dragOffsetY = y - piece.getY();
		this.originalX = piece.getX();
		this.originalY = piece.getY();
		this.dragPiece = piece;
	}

	private boolean mouseOverPiece(Piece piece, int x, int y) {
		return piece.getX() <= x && piece.getX() + piece.getWidth() >= x && piece.getY() <= y
				&& piece.getY() + piece.getHeight() >= y;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		boolean releasedOverPiece = false;
		int x = e.getPoint().x;
		int y = e.getPoint().y - 50;
		for (Piece piece : board.getPieces()) {
			if (mouseOverPiece(piece, x, y) && this.dragPiece != null) {
				if (piece.getPieceType().equals(PieceType.DEFAULT)) {
					piece.setPieceIcon(dragPiece.getPieceType());
					releasedOverPiece = true;
				}
			}
		}
		if (this.dragPiece != null) {
			if (releasedOverPiece) {
				this.dragPiece.setPieceIcon(PieceType.DEFAULT);
				if (isMill(PieceType.WHITE) && (board.getCurrentPlayer().equals(PlayerType.Player1))) {
					board.updateStatusMessage("Remove a black piece from the board");
					playerOneMill = true;
				} else if (isMill(PieceType.BLACK) && (board.getCurrentPlayer().equals(PlayerType.Player2))) {
					board.updateStatusMessage("Remove a white piece from the board");
					playerTwoMill = true;
				} else if (board.getCurrentPlayer().equals(PlayerType.Player1)) {
					board.setCurrentPlayer(PlayerType.Player2);
					board.updateStatusMessage(board.getPlayerTwo().getPlayerName() + "'s turn");
				} else if (board.getCurrentPlayer().equals(PlayerType.Player2)) {
					board.setCurrentPlayer(PlayerType.Player1);
					board.updateStatusMessage(board.getPlayerOne().getPlayerName() + "'s turn");
				}
			}
			this.dragPiece.setX(originalX);
			this.dragPiece.setY(originalY);
			this.board.repaint();
			this.dragPiece = null;
		}

	}

	public void mouseDragged(MouseEvent e) {
		if (this.dragPiece != null) {
			this.dragPiece.setX(e.getPoint().x - this.dragOffsetX);
			this.dragPiece.setY(e.getPoint().y - this.dragOffsetY);
			this.board.repaint();
		}
	}

	private boolean isMill(PieceType piecetype) {
		for (Mill mill : board.getMills()) {
			if (mill.isActualMill(piecetype) && mill.isActive() == false) {
				mill.setActive(true);
				return true;
			}
		}
		return false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
