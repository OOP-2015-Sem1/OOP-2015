package go;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TopPanel extends JPanel {
	static final String unmove = "Unmove";
	static final String pass = "pass";

	TopPanel(Game game) {
		init();
		this.game = game;
		gui = new Gui(this, game.board, 500, 520);
		Dimension d = new Dimension(510, 530);
		gui.setSize(d);
		gui.setPreferredSize(d);
		gui.setMaximumSize(d);
		gui.setMinimumSize(d);
		gui.setOpaque(true);
		gui.setBackground(Color.orange);
		add(gui);
	}

	void makeMove(Point point) {
		Move move = game.makeMove(point);
		if (move != null)
			gui.makeMove(move);
	}

	void unmove() {
		Move move = game.unmove();
		if (move != null)
			gui.unmove(move);
		else
			System.out.println("no moves to undo!");
	}

	/*
	void pass(){
		Move move = game.pass();
		if (move != null)
			gui.pass(move);
	}
	*/
	private void init() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JButton jButton = new JButton(unmove);
		jButton.setName(unmove);
		add(jButton);
		/*
		jButton = new JButton(pass);
		jButton.setName(pass);
		add(jButton);
		*/
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object object = e.getSource();
				if (object instanceof JButton)
					if (((JButton) object).getName().equals(unmove))
						unmove();
					//else if (((JButton) object).getName().equals(pass)) {
					//	pass();
					//}
			}
		});
	}

	final Gui gui;
	Game game;
	private static final long serialVersionUID = 1L;
}