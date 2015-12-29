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

public class TilesPanel {
	
	public JPanel[][] tile = new JPanel[3][5];
	public JLabel[][] thumb = new JLabel[3][5];
	private Random random = new Random();
	public int[][] imageCode = new int[3][5];
	private JPanel tilesPanel = new JPanel();

	public TilesPanel() {

		Dimension size = tilesPanel.getPreferredSize();
		size.height = 250;
		size.width = 350;
		tilesPanel.setPreferredSize(size);

		tilesPanel.setBorder(BorderFactory.createTitledBorder("Good Luck !"));
		tilesPanel.setLayout(new GridLayout(3, 5));

		createTiles();

	}

	public JPanel[][] createTiles() {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				tile[i][j] = new JPanel();
				tile[i][j].setBackground(Color.WHITE);
				tilesPanel.add(tile[i][j]);

			}

		}
		return tile;
	}

	public int[][] displayValues() {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				imageCode[i][j] = random.nextInt(NUMBER_OF_SPINNING_VALUES);
				thumb[i][j] = new JLabel(
						new ImageIcon(ClassLoader.getSystemResource(imageCode[i][j] + ".jpg")));
				tile[i][j].removeAll();
				tile[i][j].add(thumb[i][j]);
			}
		}
		tilesPanel.setVisible(true);

		return imageCode;
	}

	public JPanel getTilesPanel() {
		return tilesPanel;
	}

	public void setTilesPanel(JPanel tilesPanel) {
		this.tilesPanel = tilesPanel;
	}
	
	public int[][] getImageCode() {
		return imageCode;
	}

	public void setImageCode(int[][] imageCode) {
		this.imageCode = imageCode;
	}
}
