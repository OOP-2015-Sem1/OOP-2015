package chess.gui;

import java.awt.Image;

import chess.logic.Piece;

public class GuiPiece {

	private Image img;
	private int x;
	private int y;
	private Piece piece;

	public GuiPiece(Image img, Piece piece) {
		this.img = img;
		this.piece = piece;

		this.resetToUnderlyingPiecePosition();
	}

	public Image getImage() {
		return img;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return img.getHeight(null);
	}

	public int getHeight() {
		return img.getHeight(null);
	}

	public int getColor() {
		return this.piece.getColor();
	}

	@Override
	public String toString() {
		return this.piece + " " + x + "/" + y;
	}

	public void resetToUnderlyingPiecePosition() {
		this.x = ChessGui.convertColumnToX(piece.getColumn());
		this.y = ChessGui.convertRowToY(piece.getRow());
	}

	public Piece getPiece() {
		return piece;
	}

	public boolean isCaptured() {
		return this.piece.isCaptured();
	}

}
