package fluffy.the.cat.io.frame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BoardEntity extends JLabel {
	
	protected ImageIcon img;
	protected JLabel image;
	
	public BoardEntity(String imagePath) {
		img = new ImageIcon(imagePath);
		setIcon(img);
	}
}
