package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

//import Controller.ButtonController;
import Model.UpdateAction;

public class ButtonPanel extends JPanel {
	JButton btnNew, btnCheck, btnExit; // Used buttons.
	JCheckBox cbHelp; // Used check box.
	ButtonGroup bgNumbers; // Group for grouping the toggle buttons.
	JToggleButton[] btnNumbers; // Used toggle buttons.

	/* Constructs the panel and arranges all components. */
	public ButtonPanel() {
		super(new BorderLayout());

		JPanel pnlAlign = new JPanel();
		pnlAlign.setLayout(new BoxLayout(pnlAlign, BoxLayout.PAGE_AXIS));
		add(pnlAlign, BorderLayout.NORTH);

		JPanel pnlOptions = new JPanel(new FlowLayout(FlowLayout.LEADING));
		pnlOptions.setBorder(BorderFactory.createTitledBorder(" Options "));
		pnlAlign.add(pnlOptions);

		btnNew = new JButton("New");
		btnNew.setFocusable(false);
		pnlOptions.add(btnNew);

		btnCheck = new JButton("Check");
		btnCheck.setFocusable(false);
		pnlOptions.add(btnCheck);

		btnExit = new JButton("Exit");
		btnExit.setFocusable(false);
		pnlOptions.add(btnExit);

		JPanel pnlNumbers = new JPanel();
		pnlNumbers.setLayout(new BoxLayout(pnlNumbers, BoxLayout.PAGE_AXIS));
		pnlNumbers.setBorder(BorderFactory.createTitledBorder(" Numbers "));
		pnlAlign.add(pnlNumbers);

		JPanel pnlNumbersHelp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		pnlNumbers.add(pnlNumbersHelp);

		cbHelp = new JCheckBox("Help on", true);
		cbHelp.setFocusable(false);
		pnlNumbersHelp.add(cbHelp);

		JPanel pnlNumbersNumbers = new JPanel(new FlowLayout(FlowLayout.LEADING));
		pnlNumbers.add(pnlNumbersNumbers);

		bgNumbers = new ButtonGroup();
		btnNumbers = new JToggleButton[9];
		for (int i = 0; i < 9; i++) {
			btnNumbers[i] = new JToggleButton("" + (i + 1));
			btnNumbers[i].setPreferredSize(new Dimension(50, 50));
			btnNumbers[i].setFocusable(false);
			bgNumbers.add(btnNumbers[i]);
			pnlNumbersNumbers.add(btnNumbers[i]);
		}
	}

}
