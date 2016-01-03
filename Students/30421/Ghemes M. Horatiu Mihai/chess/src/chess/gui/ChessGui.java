package chess.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chess.logic.ChessGame;
import chess.logic.Move;
import chess.logic.MoveValidator;
import chess.logic.Piece;

public class ChessGui extends JPanel {

	private static final long serialVersionUID = -8207574964820892354L;

	private static final int BOARD_START_X = 299;
	private static final int BOARD_START_Y = 49;

	private static final int SQUARE_WIDTH = 50;
	private static final int SQUARE_HEIGHT = 50;

	private static final int PIECE_WIDTH = 48;
	private static final int PIECE_HEIGHT = 48;

	private static final int PIECES_START_X = BOARD_START_X + (int) (SQUARE_WIDTH / 2.0 - PIECE_WIDTH / 2.0);
	private static final int PIECES_START_Y = BOARD_START_Y + (int) (SQUARE_HEIGHT / 2.0 - PIECE_HEIGHT / 2.0);

	private static final int DRAG_TARGET_SQUARE_START_X = BOARD_START_X - (int) (PIECE_WIDTH / 2.0);
	private static final int DRAG_TARGET_SQUARE_START_Y = BOARD_START_Y - (int) (PIECE_HEIGHT / 2.0);

	private Image imgBackground;
	private JLabel lblGameState;
	public GuiPiece rookPiece;
	private ChessGame chessGame;

	public static final int wkFlagMoved = 1;
	public static final int wkFlag = 0;
	public int wkState = wkFlag;

	public int getwkState() {
		return this.wkState;
	}

	private List<GuiPiece> guiPieces = new ArrayList<GuiPiece>();

	private GuiPiece dragPiece;

	private Move lastMove; // for highlight only

	public ChessGui() {
		this.setLayout(null);

		URL urlBackgroundImg = getClass().getResource("/chess/gui/img/bo.png");
		this.imgBackground = new ImageIcon(urlBackgroundImg).getImage();

		this.chessGame = new ChessGame();

		for (Piece piece : this.chessGame.getPieces()) {
			createAndAddGuiPiece(piece);
		}

		// add listeners for drag and drop
		//
		PiecesDragAndDropListener listener = new PiecesDragAndDropListener(this.guiPieces, this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);

		String labelText = this.getGameStateAsText();
		this.lblGameState = new JLabel(labelText);
		lblGameState.setBounds(0, 30, 80, 30);
		lblGameState.setForeground(Color.WHITE);
		this.add(lblGameState);

		JFrame f = new JFrame();
		f.setSize(80, 80);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.setSize(imgBackground.getWidth(null), imgBackground.getHeight(null));
	}

	private String getGameStateAsText() {
		String state = "unknown";
		switch (this.chessGame.getGameState()) {
		case ChessGame.GAME_STATE_BLACK:
			state = "Black's turn to move";
			break;
		case ChessGame.GAME_STATE_END:
			state = "Checkmate!!";
			break;
		case ChessGame.GAME_STATE_WHITE:
			state = "White's turn to move";
			break;
		}
		return state;
	}

	private void createAndAddGuiPiece(Piece piece) {
		Image img = this.getImageForPiece(piece.getColor(), piece.getType());
		GuiPiece guiPiece = new GuiPiece(img, piece);
		this.guiPieces.add(guiPiece);
	}

	private Image getImageForPiece(int color, int type) {

		String filename = "";

		filename += (color == Piece.COLOR_WHITE ? "w" : "b");
		switch (type) {
		case Piece.TYPE_BISHOP:
			filename += "b";
			break;
		case Piece.TYPE_KING:
			filename += "k";
			break;
		case Piece.TYPE_KNIGHT:
			filename += "n";
			break;
		case Piece.TYPE_PAWN:
			filename += "p";
			break;
		case Piece.TYPE_QUEEN:
			filename += "q";
			break;
		case Piece.TYPE_ROOK:
			filename += "r";
			break;
		}
		filename += ".png";

		URL urlPieceImg = getClass().getResource("/chess/gui/img/" + filename);
		return new ImageIcon(urlPieceImg).getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.imgBackground, 0, 0, null);

		for (GuiPiece guiPiece : this.guiPieces) {
			if (!guiPiece.isCaptured()) {
				g.drawImage(guiPiece.getImage(), guiPiece.getX(), guiPiece.getY(), null);
			}
		}

		if (!isUserDraggingPiece() && this.lastMove != null) {
			int highlightSourceX = convertColumnToX(this.lastMove.sourceColumn);
			int highlightSourceY = convertRowToY(this.lastMove.sourceRow);
			int highlightTargetX = convertColumnToX(this.lastMove.targetColumn);
			int highlightTargetY = convertRowToY(this.lastMove.targetRow);

			g.setColor(Color.WHITE);
			g.drawRoundRect(highlightSourceX + 4, highlightSourceY + 4, SQUARE_WIDTH - 8, SQUARE_HEIGHT - 8, 10, 10);
			g.drawRoundRect(highlightTargetX + 4, highlightTargetY + 4, SQUARE_WIDTH - 8, SQUARE_HEIGHT - 8, 10, 10);

		}

		if (isUserDraggingPiece()) {

			MoveValidator moveValidator = this.chessGame.getMoveValidator();
			// board iteration
			for (int column = Piece.COLUMN_A; column <= Piece.COLUMN_H; column++) {
				for (int row = Piece.ROW_1; row <= Piece.ROW_8; row++) {
					int sourceRow = this.dragPiece.getPiece().getRow();
					int sourceColumn = this.dragPiece.getPiece().getColumn();

					if (moveValidator.isMoveValid(new Move(sourceRow, sourceColumn, row, column))) {

						int highlightX = convertColumnToX(column);
						int highlightY = convertRowToY(row);
						g.setColor(Color.BLACK);
						g.drawRoundRect(highlightX + 5, highlightY + 5, SQUARE_WIDTH - 8, SQUARE_HEIGHT - 8, 10, 10);
						g.setColor(Color.GREEN);
						g.drawRoundRect(highlightX + 4, highlightY + 4, SQUARE_WIDTH - 8, SQUARE_HEIGHT - 8, 10, 10);
					}
				}
			}
		}

		this.lblGameState.setText(this.getGameStateAsText());
	}

	private boolean isUserDraggingPiece() {
		return this.dragPiece != null;
	}

	public static void main(String[] args) {
		new ChessGui();
	}

	public int getGameState() {
		return this.chessGame.getGameState();
	}

	public static int convertColumnToX(int column) {
		return PIECES_START_X + SQUARE_WIDTH * column;
	}

	public static int convertRowToY(int row) {
		return PIECES_START_Y + SQUARE_HEIGHT * (Piece.ROW_8 - row);
	}

	public static int convertXToColumn(int x) {
		return (x - DRAG_TARGET_SQUARE_START_X) / SQUARE_WIDTH;
	}

	public static int convertYToRow(int y) {
		return Piece.ROW_8 - (y - DRAG_TARGET_SQUARE_START_Y) / SQUARE_HEIGHT;
	}

	public void setNewPieceLocation(GuiPiece dragPiece, int x, int y) {
		int targetRow = ChessGui.convertYToRow(y);
		int targetColumn = ChessGui.convertXToColumn(x);

		if (targetRow < Piece.ROW_1 || targetRow > Piece.ROW_8 || targetColumn < Piece.COLUMN_A
				|| targetColumn > Piece.COLUMN_H) {
			dragPiece.resetToUnderlyingPiecePosition();
		}

		System.out.println("moving " + /* dragPiece.getPiece().getType() */dragPiece.getPiece().wkFlag + " to "
				+ targetRow + "/" + targetColumn);
		Move move = new Move(dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn(), targetRow, targetColumn);
		boolean wasMoveSuccessfull = this.chessGame.movePiece(move);
		// flag conditions for castle-ing.
		// Castle-ing is impossible if a king has moved, or if a rook on that
		// side of the king has moved
		/*
		 * if(dragPiece.getPiece().getType()==5 && wasMoveSuccessfull &&
		 * dragPiece.getPiece().getColor() == Piece.COLOR_WHITE) {
		 * dragPiece.getPiece().wkFlag=false; if(targetRow==0 &&
		 * targetColumn==6) { System.out.println("kappa"); //Piece piece = new
		 * Piece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1,
		 * Piece.COLUMN_H);
		 * 
		 * 
		 * } } if(dragPiece.getPiece().getType()==5 && wasMoveSuccessfull &&
		 * dragPiece.getPiece().getColor() == Piece.COLOR_BLACK)
		 * 
		 */

		/*
		 * if(dragPiece.getPiece().getType()==5 &&
		 * rookPiece.getPiece().getRow()==0 &&
		 * rookPiece.getPiece().getColumn()==7 &&
		 * rookPiece.getPiece().getType()==1) {Move move2 = new Move(
		 * rookPiece.getPiece().getRow(), rookPiece.getPiece().getColumn() , 0,
		 * 5); }
		 */
		// if the last move was successfully executed, we remember it for
		// highlighting it in the user interface
		if (wasMoveSuccessfull) {
			this.lastMove = move;
		}

		dragPiece.resetToUnderlyingPiecePosition();
	}

	public void setDragPiece(GuiPiece guiPiece) {
		this.dragPiece = guiPiece;
	}

	public GuiPiece getDragPiece() {
		return this.dragPiece;
	}

}
