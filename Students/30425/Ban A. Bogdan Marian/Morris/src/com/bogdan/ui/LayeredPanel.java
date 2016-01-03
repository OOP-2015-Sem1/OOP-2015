package com.bogdan.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.bogdan.model.Mill;
import com.bogdan.model.Piece;
import com.bogdan.model.PieceType;
import com.bogdan.ui.utils.ImageUtils;

@SuppressWarnings("serial")
public class LayeredPanel extends JPanel {
	private static final int DEFAULT_BACKGROUND_DEPTH = 0;
	private static final int DEFAULT_PIECE_DEPTH = 1;
	private JLayeredPane layeredPane;
	private JLabel status;
	private JPanel panel1;
	private List<Piece> pieces = new ArrayList<>();
	private List<Mill> mills = new ArrayList<>();

	public LayeredPanel() {
		this.setLayout(new BorderLayout());
		layeredPane = new JLayeredPane();
		panel1 = new JPanel();
		layeredPane.add(getBackGround(), new Integer(DEFAULT_BACKGROUND_DEPTH), 0);
		populateBoard();
		initializeMills();
		this.add(layeredPane, BorderLayout.CENTER);
		status = new JLabel("Please press 'New game' from Main Menu to start game");
		this.add(panel1, BorderLayout.SOUTH);
		panel1.setLayout(new FlowLayout());
		panel1.add(status);
		status.setForeground(Color.RED);
		this.setOpaque(true);
	}

	private JLabel getBackGround() {
		final ImageIcon boardImage = ImageUtils.createImageIcon("/com/bogdan/ui/resources/board.png");
		JLabel backGroundLabel = new JLabel(boardImage);
		backGroundLabel.setOpaque(true);
		backGroundLabel.setBounds(0, 0, boardImage.getIconWidth(), boardImage.getIconHeight());
		return backGroundLabel;
	}

	public void refresh() {
		layeredPane.revalidate();
		layeredPane.repaint();
	}

	public void populateBoard() {

		createAndAddPiece(0, 0);
		createAndAddPiece(0, 300);
		createAndAddPiece(0, 590);
		createAndAddPiece(300, 0);
		createAndAddPiece(590, 0);
		createAndAddPiece(590, 300);
		createAndAddPiece(590, 590);
		createAndAddPiece(300, 590);

		createAndAddPiece(100, 100);
		createAndAddPiece(100, 300);
		createAndAddPiece(100, 490);
		createAndAddPiece(300, 100);
		createAndAddPiece(490, 100);
		createAndAddPiece(490, 300);
		createAndAddPiece(490, 490);
		createAndAddPiece(300, 490);

		createAndAddPiece(200, 200);
		createAndAddPiece(200, 300);
		createAndAddPiece(200, 390);
		createAndAddPiece(300, 200);
		createAndAddPiece(400, 200);
		createAndAddPiece(400, 300);
		createAndAddPiece(400, 390);
		createAndAddPiece(300, 390);
	}
	
	private void initializeMills(){
		Mill mill1 = new Mill(getPieceByCoordonates(0,0),getPieceByCoordonates(0,300),getPieceByCoordonates(0,590));
		mills.add(mill1);
		Mill mill2 = new Mill(getPieceByCoordonates(0,0),getPieceByCoordonates(300,0),getPieceByCoordonates(590,0));
		mills.add(mill2);
		Mill mill3 = new Mill(getPieceByCoordonates(590,0),getPieceByCoordonates(590,300),getPieceByCoordonates(590,590));
		mills.add(mill3);
		Mill mill4 = new Mill(getPieceByCoordonates(0,590),getPieceByCoordonates(300,590),getPieceByCoordonates(590,590));
		mills.add(mill4);
		Mill mill5 = new Mill(getPieceByCoordonates(100,100),getPieceByCoordonates(100,300),getPieceByCoordonates(100,490));
		mills.add(mill5);
		Mill mill6 = new Mill(getPieceByCoordonates(490,100),getPieceByCoordonates(490,300),getPieceByCoordonates(490,490));
		mills.add(mill6);
		Mill mill7 = new Mill(getPieceByCoordonates(200,200),getPieceByCoordonates(200,300),getPieceByCoordonates(200,390));
		mills.add(mill7);
		Mill mill8 = new Mill(getPieceByCoordonates(400,200),getPieceByCoordonates(400,300),getPieceByCoordonates(400,390));
		mills.add(mill8);
		Mill mill9 = new Mill(getPieceByCoordonates(100,100),getPieceByCoordonates(300,100),getPieceByCoordonates(490,100));
		mills.add(mill9);
		Mill mill10 = new Mill(getPieceByCoordonates(100,490),getPieceByCoordonates(300,490),getPieceByCoordonates(490,490));
		mills.add(mill10);
		Mill mill11 = new Mill(getPieceByCoordonates(200,200),getPieceByCoordonates(300,200),getPieceByCoordonates(400,200));
		mills.add(mill11);
		Mill mill12 = new Mill(getPieceByCoordonates(200,390),getPieceByCoordonates(300,390),getPieceByCoordonates(400,390));
		mills.add(mill12);
		Mill mill13 = new Mill(getPieceByCoordonates(0,300),getPieceByCoordonates(100,300),getPieceByCoordonates(200,300));
		mills.add(mill13);
		Mill mill14 = new Mill(getPieceByCoordonates(400,300),getPieceByCoordonates(490,300),getPieceByCoordonates(590,300));
		mills.add(mill14);
		Mill mill15 = new Mill(getPieceByCoordonates(300,390),getPieceByCoordonates(300,490),getPieceByCoordonates(300,590));
		mills.add(mill15);
		Mill mill16 = new Mill(getPieceByCoordonates(300,0),getPieceByCoordonates(300,100),getPieceByCoordonates(300,200));
		mills.add(mill16);
	}
	
	private Piece getPieceByCoordonates(int x, int y) {
		for (Piece piece : pieces) {
			if (piece.getX() == x && piece.getY() == y) {
				return piece;
			}
		}
		return null;
	}

	private void createAndAddPiece(int x, int y) {
		Piece piece = new Piece(PieceType.DEFAULT, x, y);
		addPiece(piece);
		pieces.add(piece);
	}

	public void addPiece(Piece piece) {
		layeredPane.add(piece, new Integer(DEFAULT_PIECE_DEPTH), 0);
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public List<Mill> getMills() {
		return mills;
	}

	public void updateStatusMessage(String text) {
		this.status.setText(text);
	}

}
