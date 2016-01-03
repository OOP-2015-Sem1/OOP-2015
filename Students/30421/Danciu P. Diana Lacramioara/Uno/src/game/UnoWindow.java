package game;

import java.awt.event.*;
import javax.swing.*;

import options.Options;
import options.OptionsDialog;

@SuppressWarnings("serial")
public class UnoWindow extends JFrame {

	private javax.swing.JMenuBar menuBar;
	public javax.swing.JMenu fileMenu;
	private javax.swing.JMenuItem newGameMenuItem;
	private javax.swing.JMenuItem optionsMenuItem;
	private javax.swing.JSeparator separator1;
	private javax.swing.JMenuItem exitUnoMenuItem;
	private static UnoPanel gamePane;

	private static UnoGame game;
	private static OptionsDialog optionsDialog;
	private static Options options;

	public void main(String[] args) {
		new UnoWindow().setVisible(true);
	}

	public UnoWindow() {
		options = new Options();
		game = new UnoGame(options);

		initComponents();
		repaint();
	}

	private void initComponents() {// GEN-BEGIN:initComponents
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		newGameMenuItem = new JMenuItem();
		optionsMenuItem = new JMenuItem();
		separator1 = new JSeparator();
		exitUnoMenuItem = new JMenuItem();
		optionsDialog = new OptionsDialog(this, true, options);

		fileMenu.setText("File");
		newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
		newGameMenuItem.setText("New game");
		newGameMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new game?",
						"New Game?", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					newGameMenuItemActionPerformed(evt);
					UnoPanel.played.clear();
				}

			}
		});
		fileMenu.add(newGameMenuItem);

		optionsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));
		optionsMenuItem.setText("Options...");
		optionsMenuItem.setMnemonic('O');
		optionsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				optionsMenuItemActionPerformed(evt);
			}
		});

		fileMenu.add(optionsMenuItem);
		fileMenu.add(separator1);
		
		exitUnoMenuItem.setText("Exit UNO");
		exitUnoMenuItem.setMnemonic('X');
		exitUnoMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Game?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					exitUnoMenuItemActionPerformed(evt);
				}
			}
		});

		fileMenu.add(exitUnoMenuItem);
		menuBar.add(fileMenu);

		setTitle("UNO");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setName("unoFrameWindow");
		setResizable(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});

		setJMenuBar(menuBar);
		pack();
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(new java.awt.Dimension(610, 450));
		setLocation((screenSize.width - 610) / 2, (screenSize.height - 450) / 2);

		setIconImage(java.awt.Toolkit.getDefaultToolkit()
				.createImage(getClass().getClassLoader().getResource("\\resources\\unoicon.png")));

		gamePane = new UnoPanel(this, game, options);
		getContentPane().add(gamePane, java.awt.BorderLayout.CENTER);
	}

	private void optionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		optionsDialog.setVisible(true);
		gamePane.repaint();
	}

	private void exitUnoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	public void newGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		options = optionsDialog.getOptions();
		gamePane.newGame(options);
	}

	public static void newGameMenuItemActionPerformed() {
		options = optionsDialog.getOptions();
		gamePane.newGame(options);
	}

	private void exitForm(java.awt.event.WindowEvent evt) {
		int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Game?",
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}