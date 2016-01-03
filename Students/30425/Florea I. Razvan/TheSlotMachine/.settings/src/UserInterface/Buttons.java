package UserInterface;

import static Main.ValuesToWorkWith.bet;
import static Main.ValuesToWorkWith.numberOfLines;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class Buttons {

	public JButton spin = new JButton("Spin");
	public JRadioButton bet1 = new JRadioButton("10", false);
	public JRadioButton bet2 = new JRadioButton("50", false);
	public JRadioButton bet3 = new JRadioButton("100", false);
	public JRadioButton bet4 = new JRadioButton("500", false);
	public JButton collect = new JButton("Collect");
	public JButton gamble = new JButton("Gamble");
	public JRadioButton oneLine = new JRadioButton("1 Line", false);
	public JRadioButton threeLines = new JRadioButton("3 Lines", false);
	public JRadioButton fiveLines = new JRadioButton("5 Lines", false);
	private ButtonGroup groupLines = new ButtonGroup();
	private ButtonGroup groupBet = new ButtonGroup();

	public Buttons() {
		groupLines.add(oneLine);
		groupLines.add(threeLines);
		groupLines.add(fiveLines);

		oneLine.addItemListener(new ClassForLines(oneLine.getText()));
		threeLines.addItemListener(new ClassForLines(threeLines.getText()));
		fiveLines.addItemListener(new ClassForLines(fiveLines.getText()));

		groupBet.add(bet1);
		groupBet.add(bet2);
		groupBet.add(bet3);
		groupBet.add(bet4);

		bet1.addItemListener(new ClassForBet(bet1.getText()));
		bet2.addItemListener(new ClassForBet(bet2.getText()));
		bet3.addItemListener(new ClassForBet(bet3.getText()));
		bet4.addItemListener(new ClassForBet(bet4.getText()));
	}

}

class ClassForLines implements ItemListener {

	private String descriptiveText;

	public ClassForLines(String text) {
		descriptiveText = text;
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (descriptiveText == "1 Line") {
			numberOfLines = 1;
		} else if (descriptiveText == "3 Lines") {
			numberOfLines = 3;
		} else if (descriptiveText == "5 Lines") {
			numberOfLines = 5;
		}

	}

}

class ClassForBet implements ItemListener {

	private int valueToBet;

	public ClassForBet(String bet) {
		valueToBet = Integer.parseInt(bet);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		bet = valueToBet;

	}

}