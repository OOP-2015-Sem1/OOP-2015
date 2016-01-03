import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Input extends KeyAdapter {

	GameEngine game;
	
	public Input(GameEngine game){
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
