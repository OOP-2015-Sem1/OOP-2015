import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class SquareJButton extends JButton implements MouseListener{
	private Square square;

	public SquareJButton(Square square) {
		super();
		this.square = square;
		this.applyStyle();
		this.addMouseListener(this);
	}


	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(event))
		{
			square.click();
			this.applyStyle();
		}
		else
			if(SwingUtilities.isRightMouseButton(event))
			{
				square.mark();
				this.applyStyle();
			}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void applyStyle(){
		if(square.isBrick())
		{
			this.setEnabled(false);
			this.setBackground(new Color(0.5f, 0.5f, 0.5f));
		}
		else
		if(square.isSuccess())
		{
			this.setEnabled(false);
			this.setBackground(new Color(0.9f, 0.5f, 0.5f));
		}
		else
		if(square.isFail())
		{
			this.setEnabled(false);
			this.setBackground(new Color(0.1f, 0.1f, 0.1f));
		}
		else
		if(square.isMarked())
		{	
			this.setEnabled(true);
			this.setBackground(new Color(1f, 1f, 1f));
		}
		else{

			this.setEnabled(true);
			this.setBackground(new Color(0.8f, 0.8f, 0.8f));
		}
	}
	
}
