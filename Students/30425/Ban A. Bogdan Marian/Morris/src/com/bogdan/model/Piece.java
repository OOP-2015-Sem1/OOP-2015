package com.bogdan.model;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.bogdan.ui.utils.ImageUtils;

@SuppressWarnings("serial")
public class Piece extends JLabel {
	private static final int PIECE_ICON_HEIGHT = 50;
	private static final int PIECE_ICON_WIDTH = 50;
	private ImageIcon pieceImage;
	private int x, y;
	private PieceType pieceType;

	public Piece(PieceType pieceType) {
		this(pieceType, 0, 0);
	}

	public Piece(PieceType pieceType, int x, int y) {
		this.pieceType = pieceType;
		this.x = x;
		this.y = y;
		setPieceIcon(pieceType);
		this.setOpaque(true);
		this.setBounds(x, y, PIECE_ICON_WIDTH, PIECE_ICON_HEIGHT);
	}

	public void setPieceIcon(PieceType pieceType) {
		switch (pieceType) {
		case WHITE:
			pieceImage = ImageUtils.createImageIcon("/com/bogdan/ui/resources/white.png");
			break;
		case BLACK:
			pieceImage = ImageUtils.createImageIcon("/com/bogdan/ui/resources/black.png");
			break;
		case DEFAULT:
			pieceImage = ImageUtils.createImageIcon("/com/bogdan/ui/resources/select.png");
			// this.setBackground(new Color(255, 255, 255, 0));
			break;
		default:
			pieceImage = ImageUtils.createImageIcon("/com/bogdan/ui/resources/select.png");
			break;
		}
		this.setIcon(pieceImage);
		this.pieceType = pieceType;
		refresh();
	}

	public void refresh() {
		this.repaint();
		this.revalidate();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public PieceType getPieceType() {
		return pieceType;
	}

}
