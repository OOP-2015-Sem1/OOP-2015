package gui;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import game.Constants;
import logic.Piece;

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

	/**
	 * move the gui piece back to the coordinates that correspond with the
	 * underlying piece's row and column
	 */
	public void resetToUnderlyingPiecePosition() {
		this.x = BackgammonGui.convertColumnToX(piece.getColumn());
		this.y = BackgammonGui.convertRowToY(piece.getRow());
	}

	public Piece getPiece() {
		return piece;
	}

	public boolean isCaptured() {
		return this.piece.isCaptured();
	}

	public Image getImageForPiece(int color) { // load image for given color
		String filename = "";

		switch (color) {
		case Constants.COLOR_WHITE:
			filename += "whitePiece";
			break;
		case Constants.COLOR_RED:
			filename += "redPiece";
			break;
		}
		filename += ".jpg";

		URL urlPieceImg = getClass().getResource(filename);
		return new ImageIcon(urlPieceImg).getImage();
	}
}
