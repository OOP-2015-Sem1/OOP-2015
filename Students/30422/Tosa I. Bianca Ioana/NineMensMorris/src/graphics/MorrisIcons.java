package graphics;
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
	public Icon VL = new ImageIcon("Icons/V_Line.png");
	public Icon OL = new ImageIcon("Icons/O_Line.png");
	public Icon Button = new ImageIcon("Icons/Button.png");
	public Icon R = new ImageIcon("Icons/Red.png");
	public Icon B = new ImageIcon("Icons/Black.png");
	
}
