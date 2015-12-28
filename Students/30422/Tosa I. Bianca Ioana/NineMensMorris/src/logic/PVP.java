package logic;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.SynthStyle;


public class PVP {

	private static final int PART_ONE = 1;
	private static final int PART_TWO = 2;
	private static final int PART_THREE = 3;
	public int gamePart = 1;

	private GameLogic logic = GameLogic.getInstance();

	public void playerVsPlayer() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				logic.buttons[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton source = (JButton) e.getSource();
						Point matrixIndeces = getMatrixIndeces(source);
						int indexI = matrixIndeces.x;
						int indexJ = matrixIndeces.y;
						if (gamePart == PART_ONE) {
							gamePart = logic.gamePartOne(source, indexI, indexJ, PART_ONE);
						}
						if (gamePart == PART_TWO) {
							gamePart = logic.gamePartTwo(source, indexI, indexJ, PART_TWO);
						}
						if (gamePart == PART_THREE) {
							logic.gamePartThree();
						}

					}
				});
			}
		}

	}

	protected Point getMatrixIndeces(JButton source) {
		Point matrixIndeces = new Point();

		matrixIndeces.x = Integer.parseInt(source.getName()) / 100;
		matrixIndeces.y = Integer.parseInt(source.getName()) % 100;
		return matrixIndeces;
	}

}