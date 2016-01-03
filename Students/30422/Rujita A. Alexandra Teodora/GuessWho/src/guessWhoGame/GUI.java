package guessWhoGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import others.Reading;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private static JButton[] playerCharacterButtons, computerCharacterButtons;
	private JButton characterChoosing;
	private static JComboBox<String> questions;
	private JPanel frameSections[];
	private final static int noPers = 24;
	private final int nrOfSections = 4;
	private Reading text;
	private String[] questionsText;
	private int noOfQuestions;
	private String characterChosenButton;
	private String imagesName[] = new String[26];
	private ImageIcon charactersImag[];
	private static JButton scor;

	public GUI() throws Exception {

		super("Guess Who Game");

		playerCharacterButtons = new JButton[noPers];
		computerCharacterButtons = new JButton[noPers];
		characterChoosing = new JButton("Choose character");
		scor = new JButton();
		scor.setEnabled(false);

		frameSections = new JPanel[nrOfSections];
		String titles[] = { "Player guessing", "Computer guessing", "Score", " " };

		text = new Reading();
		noOfQuestions = text.noOfQuestions();
		questionsText = new String[noOfQuestions];
		text.CitireIntrebari(questionsText, noOfQuestions);
		questions = new JComboBox<String>(questionsText);

		for (int i = 0; i < nrOfSections; i++) {
			frameSections[i] = new JPanel();
			frameSections[i].setBorder(BorderFactory.createTitledBorder(titles[i]));
			add(frameSections[i]);
		}

		addingButtons(frameSections[1], computerCharacterButtons, false);
		addingButtons(frameSections[0], playerCharacterButtons, true);

		frameSections[2].add(scor);

		frameSections[3].setLayout(new BorderLayout());
		characterChoosing.setBorder(BorderFactory.createTitledBorder("My character"));
		characterChoosing.setSize(30, 30);
		frameSections[3].add(questions, BorderLayout.NORTH);
		frameSections[3].add(characterChoosing, BorderLayout.CENTER);

		characterChoosing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonAction();
			}
		});

		setLayout(new GridLayout(2, 2));
		setSize(1200, 750);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void addingButtons(JPanel frameSections2, JButton button[], boolean imag) {

		creatingIcons();

		frameSections2.setLayout(new GridLayout(4, 6));
		for (int i = 0; i < noPers; i++) {
			if (imag) {
				button[i] = new JButton();
				button[i].setIcon(charactersImag[i + 1]);
				button[i].setEnabled(false);
				button[i].setDisabledIcon(charactersImag[i + 1]);
			} else {
				button[i] = new JButton();
				button[i].setIcon(charactersImag[25]);
				button[i].setEnabled(false);
				button[i].setDisabledIcon(charactersImag[25]);
			}
			frameSections2.add(button[i]);
		}
	}

	private void buttonAction() {

		JFrame fereastraAlegere = new JFrame("Character choosing");
		JButton caractereDeAles[] = new JButton[noPers];

		for (int i = 0; i < noPers; i++) {
			caractereDeAles[i] = new JButton(charactersImag[i + 1]);
			caractereDeAles[i].setName(String.valueOf(i));
			fereastraAlegere.add(caractereDeAles[i]);
		}

		for (int i = 0; i < noPers; i++)
			caractereDeAles[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					characterChosenButton = ((JButton) e.getSource()).getName();
					characterChoosing.setIcon(charactersImag[Integer.valueOf(characterChosenButton) + 1]);
					characterChoosing.setEnabled(false);
					characterChoosing.setDisabledIcon(charactersImag[Integer.valueOf(characterChosenButton) + 1]);
					try {
						chooseQuestion(characterChosenButton);
						Game.butoaneTrue = true;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					fereastraAlegere.dispose();
				}
			});

		fereastraAlegere.setSize(600, 600);
		fereastraAlegere.setLayout(new GridLayout(4, 6));
		fereastraAlegere.setVisible(true);
	}

	public static void chooseQuestion(String chosenPlayerCharacter) throws Exception {

		Game rules = new Game();
		questions.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					try {
						rules.checkingCharacters(questions.getSelectedIndex(), chosenPlayerCharacter);
						Game.compChrCreated = true;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});
	}

	public static void disablingButtons(boolean[] buttonsValue, boolean vcomputer, boolean vplayer) {

		for (int i = 0; i < noPers; i++)
			if (vcomputer)
				computerCharacterButtons[i].setVisible(buttonsValue[i]);
			else if (vplayer)
				playerCharacterButtons[i].setVisible(buttonsValue[i]);
	}

	private void creatingIcons() {

		charactersImag = new ImageIcon[26];
		for (int i = 1; i < 25; i++) {
			imagesName[i] = "resources/" + String.valueOf(i) + ".png";
			charactersImag[i] = new ImageIcon(imagesName[i]);
		}
		charactersImag[25] = new ImageIcon("resources/unknown.png");
	}

	public static void score(int score) {

		scor.setText(String.valueOf(score));
	}

}
