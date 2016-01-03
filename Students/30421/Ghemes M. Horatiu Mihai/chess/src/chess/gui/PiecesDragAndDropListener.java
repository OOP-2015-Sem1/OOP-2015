package chess.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import chess.logic.ChessGame;
import chess.logic.Piece;

public class PiecesDragAndDropListener implements MouseListener, MouseMotionListener {

	private List<GuiPiece> guiPieces;
	private ChessGui chessGui;

	private int dragOffsetX;
	private int dragOffsetY;

	public PiecesDragAndDropListener(List<GuiPiece> guiPieces, ChessGui chessGui) {
		this.guiPieces = guiPieces;
		this.chessGui = chessGui;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;

		for (int i = this.guiPieces.size() - 1; i >= 0; i--) {
			GuiPiece guiPiece = this.guiPieces.get(i);
			if (guiPiece.isCaptured())
				continue;

			if (mouseOverPiece(guiPiece, x, y)) {

				if ((this.chessGui.getGameState() == ChessGame.GAME_STATE_WHITE
						&& guiPiece.getColor() == Piece.COLOR_WHITE)
						|| (this.chessGui.getGameState() == ChessGame.GAME_STATE_BLACK
								&& guiPiece.getColor() == Piece.COLOR_BLACK)) {

					this.dragOffsetX = x - guiPiece.getX();
					this.dragOffsetY = y - guiPiece.getY();
					this.chessGui.setDragPiece(guiPiece);
					this.chessGui.repaint();
					break;
				}
			}
		}

		if (this.chessGui.getDragPiece() != null) {
			this.guiPieces.remove(this.chessGui.getDragPiece());
			this.guiPieces.add(this.chessGui.getDragPiece());
		}
	}

	private boolean mouseOverPiece(GuiPiece guiPiece, int x, int y) {

		return guiPiece.getX() <= x && guiPiece.getX() + guiPiece.getWidth() >= x && guiPiece.getY() <= y
				&& guiPiece.getY() + guiPiece.getHeight() >= y;
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if (this.chessGui.getDragPiece() != null) {
			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;

			chessGui.setNewPieceLocation(this.chessGui.getDragPiece(), x, y);
			this.chessGui.repaint();
			this.chessGui.setDragPiece(null);
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		if (this.chessGui.getDragPiece() != null) {

			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;

			System.out.println("row:" + ChessGui.convertYToRow(y) + " column:" + ChessGui.convertXToColumn(x));

			GuiPiece dragPiece = this.chessGui.getDragPiece();
			dragPiece.setX(x);
			dragPiece.setY(y);

			this.chessGui.repaint();
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

}
