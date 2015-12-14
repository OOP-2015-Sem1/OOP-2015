import javax.swing.Icon;
import javax.swing.ImageIcon;

public class MorrisIcons {

	private static MorrisIcons instance;

	public static MorrisIcons getInstance() {
		if (instance == null) {
			instance = new MorrisIcons();
		}
		return instance;

	}

	private MorrisIcons() {
	}

	public Icon VL = new ImageIcon(getClass().getResource("V_Line.png"));
	public Icon OL = new ImageIcon(getClass().getResource("O_Line.png"));
	public Icon Button = new ImageIcon(getClass().getResource("Button.png"));
	public Icon R = new ImageIcon(getClass().getResource("Red.png"));
	public Icon B = new ImageIcon(getClass().getResource("Black.png"));

}
