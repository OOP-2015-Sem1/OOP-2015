package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import game.Constants;

public class PiecesDragAndDropListener implements MouseListener, MouseMotionListener {

	private List<GuiPiece> guiPieces;
	private BackgammonGui backgammonGui;
	
	private GuiPiece dragPiece;
	private int dragOffsetX;
	private int dragOffsetY;
	

	public PiecesDragAndDropListener(List<GuiPiece> guiPieces, BackgammonGui backgammonGui) {
		this.guiPieces = guiPieces;
		this.backgammonGui = backgammonGui;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		
		// find out which piece to move.
		// we check the list from top to buttom
		// (therefore we itereate in reverse order)
		//
		for (int i = this.guiPieces.size()-1; i >= 0; i--) {
			GuiPiece guiPiece = this.guiPieces.get(i);
			if (guiPiece.isCaptured()) continue;

			if(mouseOverPiece(guiPiece,x,y)){
				
				if( (	this.backgammonGui.getGameState() == Constants.GAME_STATE_WHITE
						&& guiPiece.getColor() == Constants.COLOR_WHITE
					) ||
					(	this.backgammonGui.getGameState() ==  Constants.GAME_STATE_RED
							&& guiPiece.getColor() == Constants.COLOR_RED
						)
					){
					// calculate offset, because we do not want the drag piece
					// to jump with it's upper left corner to the current mouse
					// position
					//
					this.dragOffsetX = x - guiPiece.getX();
					this.dragOffsetY = y - guiPiece.getY();
					this.dragPiece = guiPiece;
					break;
				}
			}
		}
		
		// move drag piece to the top of the list
		if(this.dragPiece != null){
			this.guiPieces.remove( this.dragPiece );
			this.guiPieces.add(this.dragPiece);
		}
	}

	/**
	 * check whether the mouse is currently over this piece
	 * @param piece the playing piece
	 * @param x x coordinate of mouse
	 * @param y y coordinate of mouse
	 * @return true if mouse is over the piece
	 */
	private boolean mouseOverPiece(GuiPiece guiPiece, int x, int y) {

		return guiPiece.getX() <= x 
			&& guiPiece.getX()+guiPiece.getWidth() >= x
			&& guiPiece.getY() <= y
			&& guiPiece.getY()+guiPiece.getHeight() >= y;
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if( this.dragPiece != null){
			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;
			
			// set game piece to the new location if possible
			//
			backgammonGui.setNewPieceLocation(this.dragPiece, x, y);
			this.backgammonGui.repaint();
			this.dragPiece = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		if(this.dragPiece != null){
			
			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;
			
			System.out.println(
					"row:"+BackgammonGui.convertYToRow(y)
					+" column:"+(BackgammonGui.convertXToColumn(x)+1));
			
			this.dragPiece.setX(x);
			this.dragPiece.setY(y);
			
			this.backgammonGui.repaint();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}

}
