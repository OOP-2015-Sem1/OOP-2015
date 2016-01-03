package UserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static Main.Constants.NUMBER_OF_SPINNING_VALUES;

public class TilesPanel extends JPanel {

	private static final long serialVersionUID = 2039233574904754139L;
	public static JPanel[][] tile = new JPanel[3][5];
	public static JLabel[][] thumb = new JLabel[3][5];
	private static Random random = new Random();
	public static int[][] imageCode = new int[3][5];

	public TilesPanel() {

		Dimension size = getPreferredSize();
		size.height = 250;
		size.width = 350;
		setPreferredSize(size);

		setBorder(BorderFactory.createTitledBorder("Good Luck !"));
		setLayout(new GridLayout(3, 5));

		createTiles();

	}

	public JPanel[][] createTiles() {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				tile[i][j] = new JPanel();
				tile[i][j].setBackground(Color.WHITE);
				add(tile[i][j]);

			}

		}
		return tile;
	}

	public static int[][] displayValues() {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				imageCode[i][j] = random.nextInt(NUMBER_OF_SPINNING_VALUES);
				thumb[i][j] = new JLabel(new ImageIcon(ClassLoader.getSystemResource(imageCode[i][j] + ".jpg")));
				tile[i][j].removeAll();
				tile[i][j].add(thumb[i][j]);
			}
		}

		return imageCode;
	}

}
