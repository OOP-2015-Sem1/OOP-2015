package demo.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import demo.views.utilities.Clock;
import demo.views.utilities.FrameStack;

public abstract class MainFrame extends JFrame implements Interface_MainFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;

	public MainFrame(String title) {

		
		FrameStack.getInstance().push(this);
		setTitle(title);
		setSize(600, 400);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.GRAY);
		add(contentPane, BorderLayout.CENTER);

	}

	public void setToolBar(ActionListener a) {

		JToolBar toolBar = new JToolBar("Draggable");
		setPreferredSize(new Dimension(450, 130));
		setBackButtonActionListener(a, toolBar);
		Clock clock = new Clock();
		clock.setClock(toolBar);
		add(toolBar, BorderLayout.PAGE_START);
	}

	public void setBackButtonActionListener(ActionListener a, JToolBar toolBar) {
		
		JButton backButton = new JButton("Back");
		backButton.setToolTipText("Retreat!");
		toolBar.add(backButton);
		backButton.addActionListener(a);
	}

}
