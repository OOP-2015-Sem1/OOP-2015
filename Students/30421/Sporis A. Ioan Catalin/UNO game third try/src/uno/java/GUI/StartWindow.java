package uno.java.GUI;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import uno.java.constants.Constants;

public class StartWindow extends JFrame implements ButonDesigner {

	private Dimension dimension;

	private ImageIcon bImage;
	private JLabel backgroundImage;
	private JButton startGameBtn = new JButton(Constants.START_NEW_GAME);
	private Rectangle startNewGameBtnBounds = new Rectangle(50, 100, 200, 50);
	private JButton exitBtn = new JButton(Constants.EXIT_GAME);
	private Rectangle exitBtnBounds = new Rectangle(50, 220, 200, 50);
	private JButton helpBtn = new JButton(Constants.HELP);
	private Rectangle helpBtnBounds = new Rectangle(50, 160, 200, 50);

	private StartWindowHandler handler = new StartWindowHandler();

	public StartWindow() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setTitle("UNO Game");
		this.setLayout(null);

		this.setButtons(this.startGameBtn, this.startNewGameBtnBounds);
		this.setButtons(this.exitBtn, this.exitBtnBounds);
		this.setButtons(this.helpBtn, this.helpBtnBounds);
		this.setBackgroundImage("D:\\Java assignments\\UNO game third try\\Resorces\\UNO background.jpg");

	}

	public StartWindow(Dimension dimension) {
		this();
		this.dimension = dimension;
		this.setSize(this.dimension);

	}

	public void setBackgroundImage(String imagePath) {
		this.bImage = new ImageIcon(imagePath);
		this.backgroundImage = new JLabel(bImage);
		this.dimension = new Dimension(this.backgroundImage.getPreferredSize());
		this.setSize(this.dimension);
		this.backgroundImage.setBounds(0, 0, this.backgroundImage.getPreferredSize().width,
				this.backgroundImage.getPreferredSize().height);
		this.add(this.backgroundImage);
	}

	public void setButtons(JButton button, Rectangle bounds) {
		button.setBounds(bounds);
		button.addActionListener(handler);
		this.add(button);
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public ImageIcon getbImage() {
		return bImage;
	}

	public void setbImage(ImageIcon bImage) {
		this.bImage = bImage;
	}

	public JLabel getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(JLabel backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	@Override
	public void designButton(JButton button) {
		// TODO Auto-generated method stub

	}
}
