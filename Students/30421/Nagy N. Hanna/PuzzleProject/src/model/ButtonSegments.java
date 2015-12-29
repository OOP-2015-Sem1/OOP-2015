package model;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonSegments extends JButton {

	private static final long serialVersionUID = 3074537498066060775L;

	private boolean isLastButton;

	public ButtonSegments() {
		super();
		buttonInAction();
	}

	public ButtonSegments(Image image) {
		super(new ImageIcon(image));
		buttonInAction();

	}

	public void buttonInAction() {
		isLastButton = false; 

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(Color.yellow));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		});
	}

	public void setLastButton() {

		isLastButton = true;
	}

	public boolean isLastButton() {

		return isLastButton;
	}
}