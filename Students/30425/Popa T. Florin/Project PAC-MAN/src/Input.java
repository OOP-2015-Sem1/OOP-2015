import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Input extends KeyAdapter {
	
	GameEngine game;
	
	public Input(GameEngine game){
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e){
		
		game.keyPressed(e);
		
	}
	
	public void keyReleased(KeyEvent e){
		
		game.keyReleased(e);

	}
	

}
