package mineSweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.GradientPaint;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Frame extends JFrame implements MouseInputListener {
	private Screen screen;
	private World world;
	private static   int width = 400;
	private static  int  height = 400;
	private Font font;
	private int insetLeft;
	private int insetTop;//Nu mergeau bine clickurile pe patratele

	public Frame() {

		super("MineSweeper"); // Tittle
		screen = new Screen();
		world= new World();
		add(screen);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addMouseListener(this);
		setSize(width+insetLeft+getInsets().right, height+getInsets().bottom+getInsets().top);
		setVisible(true);
		setLocationRelativeTo(null);
		font=new Font("SansSerif",0,12);
		pack();
		insetLeft=getInsets().left;
		insetTop=getInsets().top;

	}

	public class Screen extends JPanel {

		public void paintComponent(Graphics g) {
			g.setFont(font);
			world.draw(g);
		}
	}

	public static  int getGameWidth() {
		
		return width; }

	public int getGameHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	//Functii date de MouseListener
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		

	}

	@Override
	public void mouseExited(MouseEvent e) {
		

	}

	@Override
	public void mousePressed(MouseEvent e) {
		

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println(e.getButton());
		System.out.println(e.getX()+ ";" +e.getY());
		if(e.getButton()==1)world.clickedLeft(e.getX()-insetLeft,e.getY()-insetTop);
		if(e.getButton()==3)world.clickedRight(e.getX()-insetLeft,e.getY()-insetTop);
		screen.repaint();

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		

	}

}
