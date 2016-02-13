package game;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame {
	 public final static int gui_WIDTH = 800;
	public final static int gui_HEIGHT = 600;
	
	public Game() {
		super("OOP Project");
		setSize(gui_WIDTH, gui_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Panel());
		setVisible(true);
	}

	private void setText(String string) {
		setText("OOP Project");
		
	}

	
}
