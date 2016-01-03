package UserInterface;

import java.awt.Font;

public class Fonts {

	private Font staticLabelFont;
	private Font dynamicLabelFont;

	public Fonts() {
		staticLabelFont = new Font(null, Font.PLAIN, 18);
		dynamicLabelFont = new Font(null, Font.BOLD + Font.ITALIC, 18);
	}

	public Font getStaticLabelFont() {
		return staticLabelFont;
	}

	public Font getDynamicLabelFont() {
		return dynamicLabelFont;
	}

}
