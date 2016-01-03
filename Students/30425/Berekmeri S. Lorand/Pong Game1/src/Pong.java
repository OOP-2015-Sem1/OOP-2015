import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pong extends JFrame {
	final static int gui_WIDTH = 707;
	final static int gui_HEIGHT = 483;
	
	public Pong() {
		setSize(gui_WIDTH, gui_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Panel());
		setVisible(true);
	}

	private void setText(String string) {
		setText("Pong game");
		
	}

	public static void main(String args[]) {
		new Pong();
	}

}
