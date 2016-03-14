package View;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PuzzlePiece {

	private ImageIcon puzzlePieceIcon;
	private JButton puzzlePieceButton;
	private int positionX;
	private int positionY;
	private int pieceOrder;
	private boolean isEmptySlot;

	public PuzzlePiece(ImageIcon puzzlePieceIcon, int positionX, int positionY, boolean isEmptySlot, int pieceOrder) {

		this.puzzlePieceIcon = puzzlePieceIcon;
		this.setPositionX(positionX);
		this.setPositionY(positionY);

		this.puzzlePieceButton = new JButton(this.puzzlePieceIcon);
		this.puzzlePieceButton.setText(this.positionX + "_" + this.positionY);

		this.setPieceOrder(pieceOrder);
		this.isEmptySlot = isEmptySlot;
	}

	public ImageIcon getPuzzlePieceIcon() {
		return puzzlePieceIcon;
	}

	public void setPuzzlePieceIcon(ImageIcon puzzlePieceIcon) {
		this.puzzlePieceIcon = puzzlePieceIcon;
	}

	public JButton getPuzzlePieceButton() {
		return puzzlePieceButton;
	}

	public void setPuzzlePieceButtonText() {
		this.puzzlePieceButton.setText(this.positionX + "_" + this.positionY);
	}

	public void setPuzzlePieceButton(JButton puzzlePieceButton) {
		this.puzzlePieceButton = puzzlePieceButton;
	}

	public boolean isEmptySlot() {
		return isEmptySlot;
	}

	public void setEmptySlot(boolean isEmptySlot) {
		this.isEmptySlot = isEmptySlot;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getPieceOrder() {
		return pieceOrder;
	}

	public void setPieceOrder(int pieceOrder) {
		this.pieceOrder = pieceOrder;
	}

}
