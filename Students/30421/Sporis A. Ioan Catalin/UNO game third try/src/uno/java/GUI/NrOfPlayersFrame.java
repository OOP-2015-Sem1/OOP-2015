package uno.java.GUI;

import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uno.java.constants.Constants;

public class NrOfPlayersFrame extends JFrame implements Designer {

	private JLabel nrPlayers;
	private Rectangle messageBounds = new Rectangle(50, 50, 300, 20);
	private JList players = new JList(Constants.PLAYERS);
	private Rectangle playersListBounds = new Rectangle(75, 75, 50, 60);
	private JButton okBtn = new JButton(Constants.ConfirmBtn);
	private Rectangle okBtnBounds = new Rectangle(130, 75, 70, 30);
	private PlayersSelectHandler handler = new PlayersSelectHandler();

	public NrOfPlayersFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 200);
		this.setVisible(true);
		this.setLayout(null);
		this.setTitle("Nr Of Players");
		this.arrangeItems();
		this.players.addListSelectionListener(handler);
		this.okBtn.addActionListener(handler);
	}

	public JLabel setLabelText(String message) {
		JLabel txt;
		txt = new JLabel(message);
		txt.setFont(new Font("Times New Roman", Font.BOLD, 18));
		return txt;
	}

	public void setPlayerList(JList list) {
		list.setVisibleRowCount(1);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	@Override
	public void componentSetBounds(Component component, Rectangle bounds) {
		component.setBounds(bounds);
	}

	@Override
	public void arrangeItems() {
		this.nrPlayers = this.setLabelText(Constants.NR_OF_PLAYERS_MESSAGE);
		this.componentSetBounds(this.nrPlayers, this.messageBounds);
		this.add(this.nrPlayers);

		this.componentSetBounds(this.players, this.playersListBounds);
		this.setPlayerList(this.players);

		this.add(this.players);

		this.componentSetBounds(this.okBtn, this.okBtnBounds);
		this.add(this.okBtn);
	}

}
