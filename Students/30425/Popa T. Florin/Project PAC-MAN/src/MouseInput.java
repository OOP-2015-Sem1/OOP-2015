import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		Rectangle mouse = new Rectangle(e.getX(),e.getY(),1,1);
		Rectangle newGameButton = new Rectangle(170,220,213,51);
		Rectangle exitButton = new Rectangle(170,300,213,51);
		Rectangle exitOverButton = new Rectangle(170,400,213,51);
		Rectangle mainMenuButton = new Rectangle(170,300,213,51);
		
		if(GameEngine.State == GameEngine.STATE.MENU){
		if(mouse.intersects(newGameButton)){
			GameEngine.State = GameEngine.STATE.GAME;
		}else if(mouse.intersects(exitButton)){
			System.exit(1);
		}
		}else if(GameEngine.State == GameEngine.STATE.OVER){
			if(mouse.intersects(exitOverButton)){
				System.exit(1);
			}else if(mouse.intersects(mainMenuButton)){
				GameEngine.State = GameEngine.STATE.MENU;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
