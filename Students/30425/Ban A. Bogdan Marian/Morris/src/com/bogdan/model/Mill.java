package com.bogdan.model;

public class Mill {
	private Piece piece1;
	private Piece piece2;
	private Piece piece3;
	private boolean isActive;

	public Mill(Piece piece1, Piece piece2, Piece piece3) {
		this.piece1 = piece1;
		this.piece2 = piece2;
		this.piece3 = piece3;
	}

	public boolean isActualMill(PieceType piecetype) {
		return (this.piece1.getPieceType().equals(piecetype) && this.piece2.getPieceType().equals(piecetype)
				&& this.piece3.getPieceType().equals(piecetype));
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
