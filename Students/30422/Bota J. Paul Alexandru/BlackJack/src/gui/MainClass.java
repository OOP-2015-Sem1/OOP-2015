package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class MainClass {

	public static void main(String[] args) {

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Interface p = new Interface();
		f.setResizable(false);
		f.setTitle("BlackJack");
		f.add(p);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 4, screenSize.height / 8);
		Point newLocation = new Point(middle.x - (f.getWidth() / 4), middle.y - (f.getHeight() / 8));
		f.setLocation(newLocation);

		// f.setLocationRelativeTo(null);
		f.setSize(1000, 807);
		f.setVisible(true);

	}

}
