package UserInterface;

import static Main.ValuesToWorkWith.credit;
import static Main.ValuesToWorkWith.winning;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Functionality.ButtonsFunctionality;

public class ButtonsPanel extends JPanel {

	private static final long serialVersionUID = -8040401353962174500L;

	private Buttons buttons = new Buttons();
	private Labels labels = new Labels();
	private Fonts fonts = new Fonts();
	private ButtonsFunctionality functions = new ButtonsFunctionality();

	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	private JPanel middlePanel = new JPanel();

	public ButtonsPanel() {

		Dimension size = getPreferredSize();
		size.height = 150;
		size.width = 350;
		setPreferredSize(size);
		setLayout(new GridLayout(1, 3));

		leftPanel = createLeftPanel();
		rightPanel = createRightPanel();
		middlePanel = createMiddlePanel();

		add(leftPanel);
		add(middlePanel);
		add(rightPanel);

	}

	public JPanel createLeftPanel() {

		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4, 1));
		panel.add(labels.getLinesLabel());
		panel.add(buttons.oneLine);
		panel.add(buttons.threeLines);
		panel.add(buttons.fiveLines);

		return panel;

	}

	public JPanel createMiddlePanel() {

		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(3, 2));

		labels.getTotalCredit().setFont(fonts.getStaticLabelFont());
		panel.add(labels.getTotalCredit());
		Labels.creditLabel.setFont(fonts.getDynamicLabelFont());
		panel.add(newCredit());

		labels.getCurrentWinning().setFont(fonts.getStaticLabelFont());
		panel.add(labels.getCurrentWinning());
		Labels.winningLabel.setFont(fonts.getDynamicLabelFont());
		panel.add(newWinning());

		panel.add(buttons.gamble);
		panel.add(buttons.collect);

		buttons.collect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				functions.actionForCollect();
			}
		});

		buttons.gamble.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				functions.actionForGamble();
			}

		});

		return panel;
	}

	public JPanel createRightPanel() {

		JPanel panel0 = new JPanel();
		panel0.setLayout(new GridLayout(2, 2));

		panel0.add(buttons.bet1);
		panel0.add(buttons.bet2);
		panel0.add(buttons.bet3);
		panel0.add(buttons.bet4);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		labels.getBetLabel().setFont(fonts.getStaticLabelFont());
		panel.add(labels.getBetLabel());
		panel.add(panel0);
		panel.add(buttons.spin);

		buttons.spin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				functions.actionForSpin();
			}
		});

		return panel;

	}

	public static JLabel newCredit() {
		Labels.creditLabel.setText("" + credit);
		Labels.creditLabel.setForeground(Color.BLUE);
		return Labels.creditLabel;
	}

	public static JLabel newWinning() {
		Labels.winningLabel.setText("" + winning);
		Labels.winningLabel.setForeground(Color.RED);
		return Labels.winningLabel;

	}

}
