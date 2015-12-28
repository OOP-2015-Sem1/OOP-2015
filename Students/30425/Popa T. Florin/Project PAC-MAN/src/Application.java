import javax.swing.JFrame;

public class Application {
	
	
	public static void main(String[] args) {
		
		GameEngine game = new GameEngine();
		
		JFrame frame = new JFrame();
		frame.add(game);
		frame.setTitle(game.TITLE);
		frame.setSize(576,530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
		
		
	}
}
