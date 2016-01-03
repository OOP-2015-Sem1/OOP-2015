package options;

import javax.swing.*;
import game.UnoPanel;
import game.UnoWindow;

@SuppressWarnings("serial")
public class OptionsDialog extends javax.swing.JDialog {
	private javax.swing.ButtonGroup difficultyButtonGroup;
	private javax.swing.ButtonGroup buttonGroup;
	private javax.swing.JPanel labelsPane;
	private javax.swing.JLabel startingOptionsLabel;
	private javax.swing.JLabel maxCardsLabel;
	private javax.swing.JLabel numPlayersLabel;
	private javax.swing.JLabel numCardsLabel;
	private javax.swing.JLabel difficultyLabel;
	private javax.swing.JLabel extraRulesLabel;
	private javax.swing.JLabel zeroRuleLabel;
	private javax.swing.JLabel wildWinLabel;
	private javax.swing.JPanel okCancelPane;
	private javax.swing.JButton okButton;
	private javax.swing.JButton cancelButton;
	private javax.swing.JButton defaultButton;
	private javax.swing.JPanel optionsPane;
	private javax.swing.JLabel separator1;
	private javax.swing.JLabel maxCardsIndicatorLabel;
	private javax.swing.JPanel numPlayersOptionsPane;
	private javax.swing.JScrollBar numPlayerScroll;
	private javax.swing.JLabel numberPlayersChanchingLabel;
	private javax.swing.JPanel numCardsOptionsPane;
	private javax.swing.JScrollBar numCardsScroll;
	private javax.swing.JLabel numberCardsChanchingLabel;
	private javax.swing.JPanel difficultyOptionsPane;
	private javax.swing.JRadioButton easyRadioButton;
	private javax.swing.JRadioButton mediumRadioButton;
	private javax.swing.JRadioButton hardRadioButton;
	private javax.swing.JLabel separator2;
	private javax.swing.JPanel zeroRuleOptionsPane;
	private javax.swing.JCheckBox zeroRuleCheckBox;
	private javax.swing.JPanel wildWinOptionsPane;
	private javax.swing.JCheckBox wildWinCheckBox;
	private Options options;
	private int level;

	public OptionsDialog(java.awt.Frame parent, boolean modal, Options options) {
		super(parent, modal);
		this.options = options;
		initComponents();

		setResizable(true);

		numPlayerScroll.setValue(options.getNumberPlayers());
		numCardsScroll.setValue(options.getNumberCards());
		zeroRuleCheckBox.setSelected(options.withZeroRule());
		wildWinCheckBox.setSelected(options.withWildWin());

		easyRadioButton.doClick();
		easyRadioButton.setEnabled(true);
		mediumRadioButton.setEnabled(true);
		hardRadioButton.setEnabled(true);

		switch (options.getDifficultyLevel()) {
		case Options.EASY:
			easyRadioButton.doClick();
			break;
		case Options.MEDIUM:
			mediumRadioButton.doClick();
			break;
		case Options.HARD:
			hardRadioButton.doClick();
			break;
		}

	}

	public Options getOptions() {
		return this.options;
	}

	private void initComponents() {
		difficultyButtonGroup = new javax.swing.ButtonGroup();
		setButtonGroup1(new javax.swing.ButtonGroup());
		labelsPane = new javax.swing.JPanel();
		startingOptionsLabel = new javax.swing.JLabel();
		maxCardsLabel = new javax.swing.JLabel();
		numPlayersLabel = new javax.swing.JLabel();
		numCardsLabel = new javax.swing.JLabel();
		difficultyLabel = new javax.swing.JLabel();
		extraRulesLabel = new javax.swing.JLabel();
		zeroRuleLabel = new javax.swing.JLabel();
		wildWinLabel = new javax.swing.JLabel();
		okCancelPane = new javax.swing.JPanel();
		okButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		defaultButton = new javax.swing.JButton();
		optionsPane = new javax.swing.JPanel();
		separator1 = new javax.swing.JLabel();
		maxCardsIndicatorLabel = new javax.swing.JLabel();
		numPlayersOptionsPane = new javax.swing.JPanel();
		numPlayerScroll = new javax.swing.JScrollBar();
		numberPlayersChanchingLabel = new javax.swing.JLabel();
		numCardsOptionsPane = new javax.swing.JPanel();
		numCardsScroll = new javax.swing.JScrollBar();
		numberCardsChanchingLabel = new javax.swing.JLabel();
		difficultyOptionsPane = new javax.swing.JPanel();
		easyRadioButton = new javax.swing.JRadioButton();
		mediumRadioButton = new javax.swing.JRadioButton();
		hardRadioButton = new javax.swing.JRadioButton();
		separator2 = new javax.swing.JLabel();
		zeroRuleOptionsPane = new javax.swing.JPanel();
		zeroRuleCheckBox = new javax.swing.JCheckBox();
		wildWinOptionsPane = new javax.swing.JPanel();
		wildWinCheckBox = new javax.swing.JCheckBox();

		getContentPane().setLayout(new java.awt.GridBagLayout());
		java.awt.GridBagConstraints gridBagConstraints1;

		setTitle("Options");
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setName("optionsDialog");
		setModal(true);
		setResizable(false);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

		labelsPane.setLayout(new java.awt.GridLayout(8, 1));

		startingOptionsLabel.setText("STARTING OPTIONS: ");
		startingOptionsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		startingOptionsLabel.setFont(new java.awt.Font("Dialog", 1, 14));
		labelsPane.add(startingOptionsLabel);

		maxCardsLabel.setText("Number of cards used at start: ");
		maxCardsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		labelsPane.add(maxCardsLabel);

		numPlayersLabel.setText("Number of players (2-6): ");
		numPlayersLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		labelsPane.add(numPlayersLabel);

		numCardsLabel.setText("Starting cards (2-20): ");
		numCardsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		labelsPane.add(numCardsLabel);

		difficultyLabel.setText("Difficulty: ");
		difficultyLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		labelsPane.add(difficultyLabel);

		extraRulesLabel.setText("EXTRA RULES");
		extraRulesLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		extraRulesLabel.setFont(new java.awt.Font("Dialog", 1, 14));
		labelsPane.add(extraRulesLabel);

		zeroRuleLabel.setText("Zero rule?");
		zeroRuleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		labelsPane.add(zeroRuleLabel);

		wildWinLabel.setText("Can win with Wild?");
		wildWinLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		labelsPane.add(wildWinLabel);

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridheight = 2;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints1.insets = new java.awt.Insets(10, 10, 0, 0);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.EAST;
		getContentPane().add(labelsPane, gridBagConstraints1);

		okCancelPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

		okButton.setText("Ok");
		okButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okButtonActionPerformed(evt);
			}
		});

		okCancelPane.add(okButton);

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		okCancelPane.add(cancelButton);

		defaultButton.setText("Default");
		defaultButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				defaultButtonActionPerformed(evt);
			}
		});

		okCancelPane.add(defaultButton);

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.gridwidth = 2;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.insets = new java.awt.Insets(0, 10, 10, 10);
		getContentPane().add(okCancelPane, gridBagConstraints1);

		optionsPane.setLayout(new java.awt.GridLayout(8, 1));

		optionsPane.add(separator1);

		maxCardsIndicatorLabel
				.setText((options.getNumberPlayers() * options.getNumberCards()) + "/" + options.getMaxCards());
		maxCardsIndicatorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		optionsPane.add(maxCardsIndicatorLabel);

		numPlayersOptionsPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		numPlayerScroll.setVisibleAmount(1);
		numPlayerScroll.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
		numPlayerScroll.setMinimum(2);
		numPlayerScroll.setBlockIncrement(1);
		numPlayerScroll.setMaximum(7);
		numPlayerScroll.setValue(4);
		numPlayerScroll.setPreferredSize(new java.awt.Dimension(120, 17));
		numPlayerScroll.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
				adjustChangeHandler(evt);
			}
		});

		numPlayersOptionsPane.add(numPlayerScroll);

		numberPlayersChanchingLabel.setText("4");
		numPlayersOptionsPane.add(numberPlayersChanchingLabel);

		optionsPane.add(numPlayersOptionsPane);

		numCardsOptionsPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		numCardsScroll.setVisibleAmount(1);
		numCardsScroll.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
		numCardsScroll.setMinimum(2);
		numCardsScroll.setBlockIncrement(1);
		numCardsScroll.setMaximum(21);
		numCardsScroll.setValue(7);
		numCardsScroll.setPreferredSize(new java.awt.Dimension(120, 17));
		numCardsScroll.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
				numCardsScrollAdjustmentValueChanged(evt);
			}
		});

		numCardsOptionsPane.add(numCardsScroll);

		numberCardsChanchingLabel.setText("7");
		numCardsOptionsPane.add(numberCardsChanchingLabel);

		optionsPane.add(numCardsOptionsPane);

		easyRadioButton.setSelected(true);
		easyRadioButton.setText("Easy");
		difficultyButtonGroup.add(easyRadioButton);
		easyRadioButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				easyRadioButtonActionPerformed(evt);
			}
		});

		difficultyOptionsPane.add(easyRadioButton);

		mediumRadioButton.setText("Medium");
		difficultyButtonGroup.add(mediumRadioButton);
		mediumRadioButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mediumRadioButtonActionPerformed(evt);
			}
		});

		difficultyOptionsPane.add(mediumRadioButton);

		hardRadioButton.setText("Hard");
		difficultyButtonGroup.add(hardRadioButton);
		hardRadioButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hardRadioButtonActionPerformed(evt);
			}
		});

		difficultyOptionsPane.add(hardRadioButton);

		optionsPane.add(difficultyOptionsPane);

		optionsPane.add(separator2);

		zeroRuleOptionsPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		zeroRuleCheckBox
				.setToolTipText("When someone plays a zero, each player passes his hand to the next player in turn.");

		zeroRuleOptionsPane.add(zeroRuleCheckBox);

		optionsPane.add(zeroRuleOptionsPane);

		wildWinOptionsPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		wildWinCheckBox.setToolTipText("If this is disabled you can't put a Wild as your last card.");
		wildWinOptionsPane.add(wildWinCheckBox);

		optionsPane.add(wildWinOptionsPane);

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.gridheight = 2;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints1.insets = new java.awt.Insets(10, 0, 0, 10);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
		getContentPane().add(optionsPane, gridBagConstraints1);

		pack();

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(new java.awt.Dimension(getWidth(), getHeight()));
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
	}

	private void hardRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
		level = Options.HARD;
	}

	private void mediumRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
		level = Options.MEDIUM;
	}

	private void easyRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
		level = Options.EASY;
	}

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if (numCardsScroll.getValue() * numPlayerScroll.getValue() >= options.getMaxCards()) {
			String s = "There are not enough cards for all the players, reduce the number of players or the number of cards.";
			new JOptionPane(s, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION).createDialog(this, "Alert")
					.setVisible(true);
			return;
		}

		options.setNumberPlayers(numPlayerScroll.getValue());
		options.setNumberCards(numCardsScroll.getValue());
		options.setDifficultyLevel(level);
		options.setZeroRule(zeroRuleCheckBox.isSelected());
		options.setWildWin(wildWinCheckBox.isSelected());
		for (int lcv = 0; lcv < 6; lcv++) {
			UnoPanel.model.setValueAt(0, lcv, 1);
		}
		UnoPanel.played.clear();
		UnoWindow.newGameMenuItemActionPerformed();
		setVisible(false);
		dispose();
	}

	private void defaultButtonActionPerformed(java.awt.event.ActionEvent evt) {
		numPlayerScroll.setValue(4);
		numCardsScroll.setValue(7);

		zeroRuleCheckBox.setSelected(false);
		wildWinCheckBox.setSelected(true);
	}

	private void numCardsScrollAdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
		numberCardsChanchingLabel.setText(numCardsScroll.getValue() + "");
		maxCardsIndicatorLabel
				.setText((numCardsScroll.getValue() * numPlayerScroll.getValue()) + "/" + options.getMaxCards());
	}

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
		setVisible(false);

		numPlayerScroll.setValue(options.getNumberPlayers());
		numCardsScroll.setValue(options.getNumberCards());
		zeroRuleCheckBox.setSelected(options.withZeroRule());
		wildWinCheckBox.setSelected(options.withWildWin());

		dispose();
	}

	private void adjustChangeHandler(java.awt.event.AdjustmentEvent evt) {
		numberPlayersChanchingLabel.setText(numPlayerScroll.getValue() + "");
		maxCardsIndicatorLabel
				.setText((numCardsScroll.getValue() * numPlayerScroll.getValue()) + "/" + options.getMaxCards());
	}

	private void closeDialog(java.awt.event.WindowEvent evt) {
		setVisible(false);
		dispose();
	}

	public javax.swing.ButtonGroup getButtonGroup1() {
		return buttonGroup;
	}

	public void setButtonGroup1(javax.swing.ButtonGroup buttonGroup1) {
		this.buttonGroup = buttonGroup1;
	}
}