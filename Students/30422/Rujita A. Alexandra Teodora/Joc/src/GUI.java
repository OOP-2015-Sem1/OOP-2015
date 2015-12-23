import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private JButton[] playerCharacterButtons, computerCharacterButtons;
	private JButton characterChoosing;
	private JComboBox<String> questions;
	private JPanel frameSections[];
	final int noPers = 24, nrOfSections = 4;
	Reading text;
	String[] questionsText;
	int noOfQuestions, choosenCharacter;
	String characterChosenButton;
	String imagesName[] = new String[25];
	ImageIcon images[];

	public GUI() throws Exception {

		playerCharacterButtons = new JButton[noPers];
		computerCharacterButtons = new JButton[noPers];
		characterChoosing = new JButton("Choose character");
		frameSections = new JPanel[nrOfSections];

		text = new Reading();
		noOfQuestions = text.indexIntrebari();
		questionsText = new String[noOfQuestions];
		text.CitireIntrebari(questionsText, noOfQuestions);
		questions = new JComboBox<String>(questionsText);

		for (int i = 0; i < nrOfSections; i++) {
			frameSections[i] = new JPanel();
			add(frameSections[i]);
		}

		adaugareButoane(frameSections[0], playerCharacterButtons);
		adaugareButoane(frameSections[1], computerCharacterButtons);

		frameSections[3].setLayout(new BorderLayout());
		characterChoosing.setSize(30, 30);
		frameSections[3].add(questions, BorderLayout.NORTH);
		frameSections[3].add(characterChoosing, BorderLayout.CENTER);

		characterChoosing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonAction();
			}
		});

		setLayout(new GridLayout(2, 2));
		setSize(1200,750);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void adaugareButoane(JPanel frameSections2, JButton b[]) {

		creatingIcons();
		frameSections2.setLayout(new GridLayout(4, 6));
		for (int i = 0; i < noPers; i++) {
			b[i] = new JButton(images[i + 1]);
			frameSections2.add(b[i]);
			//b[i].setSize(70, 100);
		}
	}

	private void buttonAction() {

		JFrame fereastraAlegere = new JFrame();
		JButton caractereDeAles[] = new JButton[noPers];

		for (int i = 0; i < noPers; i++) {
			caractereDeAles[i] = new JButton(images[i + 1]);
			caractereDeAles[i].setName(String.valueOf(i));
			fereastraAlegere.add(caractereDeAles[i]);
		}

		for (int i = 0; i < noPers; i++)
			caractereDeAles[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					characterChosenButton = ((JButton) e.getSource()).getName();
					characterChoosing.setIcon(images[Integer.valueOf(characterChosenButton) + 1]);
					characterChoosing.setEnabled(false);
					characterChoosing.setDisabledIcon(images[Integer.valueOf(characterChosenButton) + 1]);
					try {
						chooseQuestion(characterChosenButton);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					fereastraAlegere.dispose();
				}
			});

		fereastraAlegere.setSize(600,600);
		fereastraAlegere.setLayout(new GridLayout(4, 6));
		fereastraAlegere.setVisible(true);
	}

	public void chooseQuestion(String ales2) throws Exception {

		Game g = new Game();
		questions.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					try {
						g.eliminateCharacters(questions.getSelectedIndex(), ales2);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});
	}

	public void disablingButtons(boolean[] buttonsValue, boolean vcomputer, boolean vplayer) {

		for (int i = 0; i < noPers; i++)
			if (vcomputer)
				computerCharacterButtons[i].setVisible(buttonsValue[i]);
			else if (vplayer)
				playerCharacterButtons[i].setVisible(buttonsValue[i]);
	}

	private void creatingIcons() {

		images = new ImageIcon[25];
		for (int i = 1; i < 25; i++)
			imagesName[i] = String.valueOf(i) + ".png";
		for (int i = 1; i < 25; i++)
			images[i] = new ImageIcon(imagesName[i]);
	}
}
