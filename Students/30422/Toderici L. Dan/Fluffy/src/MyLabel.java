import javax.swing.JLabel;

public class MyLabel extends JLabel {
	private String text;
	private int x;
	private int y;

	public MyLabel(String text, int x, int y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}
}
