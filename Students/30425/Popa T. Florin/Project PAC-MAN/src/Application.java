import java.awt.Dimension;

import javax.swing.JFrame;

public class Application {
	
	public static void main(String[] args) {
		
		GameEngine game = new GameEngine();
		
		game.setPreferredSize(new Dimension(game.WIDTH * game.SCALE, game.HEIGHT * game.SCALE));
		game.setMinimumSize(new Dimension(game.WIDTH * game.SCALE, game.HEIGHT * game.SCALE));
		game.setMaximumSize(new Dimension(game.WIDTH * game.SCALE, game.HEIGHT * game.SCALE));
		
		JFrame frame = new JFrame();
		frame.add(game);
		frame.setTitle(game.TITLE);
		frame.setSize(640, 503);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
		
		
	}
}
