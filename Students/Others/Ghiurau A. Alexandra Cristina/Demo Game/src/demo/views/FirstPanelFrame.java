package demo.views;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class FirstPanelFrame extends MainFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton easyButton;
	private JButton mediumButton;
	private JButton hardButton;

	public FirstPanelFrame(String title) {
		super(title);

		contentPane.setLayout(new GridLayout(0, 3, 0, 0));
		JPanel panel = new JPanel();
		
		
		
		contentPane.add(panel);

		JPanel pan = new JPanel();
		contentPane.add(pan);

		SpringLayout s1Panel = new SpringLayout();
		pan.setLayout(s1Panel);

		easyButton = new JButton("Easy");
		s1Panel.putConstraint(SpringLayout.NORTH, easyButton, 30, SpringLayout.NORTH, pan);
		s1Panel.putConstraint(SpringLayout.WEST, easyButton, 70, SpringLayout.WEST, pan);
		pan.add(easyButton);

		mediumButton = new JButton("Medium");
		s1Panel.putConstraint(SpringLayout.NORTH, mediumButton, 65, SpringLayout.NORTH, pan);
		s1Panel.putConstraint(SpringLayout.WEST, mediumButton, 60, SpringLayout.WEST, pan);
		pan.add(mediumButton);

		hardButton = new JButton("Hard");
		s1Panel.putConstraint(SpringLayout.NORTH, hardButton, 95, SpringLayout.NORTH, pan);
		s1Panel.putConstraint(SpringLayout.WEST, hardButton, 70, SpringLayout.WEST, pan);
		pan.add(hardButton);

		JPanel panel2 = new JPanel();
		contentPane.add(panel2);
		setVisible(true);

	}

	public void setEasyButtonActionListener(ActionListener a) {
		easyButton.addActionListener(a);
	}
	
	public void setMediumButtonActionListener(ActionListener a) {
		mediumButton.addActionListener(a);
	}
	
	public void setHardButtonActionListener(ActionListener a) {
		hardButton.addActionListener(a);
	}

	@Override
	public void goBack() {
		// TODO Auto-generated method stub
		
	}

}
