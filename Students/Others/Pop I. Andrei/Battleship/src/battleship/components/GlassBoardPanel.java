package battleship.components;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class GlassBoardPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	private int shipHeight = 50;
	private  int shipWidth = 100;
	private Rectangle2D myRect;
	private boolean drawEnabled = false;
	private final int HORIZONTAL = 0;
	private int position;
	private final int xLeftLimit = 262, xRightLimit = 840; // the sizes of the play board
	private final int yUpLimit = 27, yDownLimit = 555;
	
	public GlassBoardPanel() {
		
		addMouseListener(this);
		addMouseMotionListener(this);
		position = 1;
		setSize(600, 600);
		setVisible(true);
		setOpaque(false);
		//setFocusable(true);
	}
	
	public void setShipHeight(int height){
		this.shipHeight = height;
	}
	
	public void setShipWidth(int width) {
		this.shipWidth = width;
	}
	
	public int getShipHeight() {
		return shipHeight;
	}
	
	public int getShipWidth() {
		return shipWidth;
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.RED);
		if(drawEnabled) {
			g2.draw(myRect);
		}
//		
//		g2.setColor(Color.BLACK);
//		g2.fillRect(10, 10, H, W);
	}
	
	private boolean xIsBetweenLimits(int x) {
		int xmin = 0, xmax = 0;
		
		if(position == HORIZONTAL) {
			xmin = x-(shipWidth/2);
			xmax = x + (shipWidth / 2);
			System.out.println(" xmin: " + xmin + " xmax: " + xmax +" ");
			
			if(x - (shipWidth/2) >= xLeftLimit && x + shipWidth/2 <= xRightLimit)
				return true;
		}
		else{
			xmin = x - (shipHeight /2);
			xmax = x + (shipHeight/2);
			System.out.println(" xmin: " + xmin + " xmax: " + xmax +" ");
			
			if((x - shipHeight /2 >= xLeftLimit) && (x + shipHeight/2 <= xRightLimit))
				return true;
		}
		
		return false;
	}
	
	private boolean yIsBetweenLimits(int y) {
		int ymin = 0, ymax = 0;
		
		if(position == HORIZONTAL) {
			ymin = y - (shipHeight / 2);
			ymax = y + (shipHeight / 2);
			System.out.println(" ymin: " + ymin + " ymax: " + ymax + " ");
			
			if((y - shipHeight / 2 >= yUpLimit) && (y + shipHeight/2 <= yDownLimit))
				return true;
		}
		else {
			ymin = y - (shipWidth / 2);
			ymax = y + (shipWidth / 2);
			System.out.println(" ymin: " + ymin + " ymax: " + ymax + " ");
			
			if((y - shipWidth / 2 >= yUpLimit) && (y + shipWidth/2 <= yDownLimit))
				return true;
		}
		
		return false;
	}
	
	private boolean coordOnBoard(int x, int y) {
		 return (xIsBetweenLimits(x) && yIsBetweenLimits(y));
	}
	
	private void drawRectangle(int x, int y) {
		
		if(position == HORIZONTAL) {
			if(coordOnBoard(x, y))
				drawEnabled = true;
			else
				drawEnabled = false;
			myRect = new Rectangle2D.Double(x, y, shipWidth, shipHeight);
		}
		else{
			if(coordOnBoard(x, y))
				drawEnabled = true;
			else
				drawEnabled = false;
			myRect = new Rectangle2D.Double(x, y, shipHeight, shipWidth);
		}
		repaint();
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3)
			position = 1-position;
		System.out.println(position);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		drawEnabled = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		drawEnabled = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		System.out.println("lasat");
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public int getXRectCoord(int XMouseCoord) {
		if(position == HORIZONTAL) {
			return XMouseCoord - shipWidth/2;
		}
		else
			return (XMouseCoord - (shipHeight/2));
	}
	
	public int getYRectCoord(int XMouseCoord) {
		if(position == HORIZONTAL) {
			return XMouseCoord - shipHeight/2;
		}
		else
			return (XMouseCoord - shipWidth/2);
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		
		int x = getXRectCoord(e.getX());
		int y = getYRectCoord(e.getY());
		
		System.out.println(e.getX() + "  " + e.getY());
		System.out.println(x + " " + y + " ");
		
		drawRectangle(x, y);
	}
}
