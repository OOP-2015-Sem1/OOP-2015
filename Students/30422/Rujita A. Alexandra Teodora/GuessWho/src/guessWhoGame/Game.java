package guessWhoGame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import others.Characteristics;
import others.Computer;
import others.Reading;

public class Game {

	private Characteristics pers1[];
	private final int noPers = 24;
	private int eliminatedCompChr = noPers;
	private int remainingPlayerChr = noPers;
	private boolean player, computer;
	private boolean[] valButoaneCalc, valButoaneJucator;
	private String intrebariText[];
	public static boolean compChrCreated, butoaneTrue = false;
	private int playerChr, computerChr;
	private Computer c = new Computer();

	public Game() throws Exception {

		valButoaneCalc = new boolean[noPers];
		valButoaneJucator = new boolean[noPers];

		if (!butoaneTrue)
			buttonsValTrue();

		Reading text = new Reading();
		int count = text.noOfQuestions();
		intrebariText = new String[count];
		text.CitireIntrebari(intrebariText, count);
	}

	private void buttonsValTrue() {

		for (int i = 0; i < noPers; i++) {
			valButoaneCalc[i] = true;
			valButoaneJucator[i] = true;
		}
	}

	private void settingCharacteristics(Characteristics[] pers) {

		pers[0] = new Characteristics(false, false, false, false, true, false, false, true, true, true, true, false,
				false, false, false, false, false, false);
		pers[1] = new Characteristics(false, true, false, false, true, true, false, false, false, false, false, true,
				true, false, false, false, true, false);
		pers[2] = new Characteristics(false, false, false, true, false, false, false, false, false, false, false, true,
				false, false, false, false, false, false);
		pers[3] = new Characteristics(true, true, false, true, false, true, false, false, false, false, true, true,
				false, false, false, false, false, false);
		pers[4] = new Characteristics(false, false, false, true, false, false, false, false, false, false, false, true,
				true, false, false, false, false, false);
		pers[5] = new Characteristics(true, true, true, false, false, false, true, true, false, false, true, true, true,
				false, false, false, false, false);
		pers[6] = new Characteristics(false, false, false, false, true, false, false, false, true, true, false, false,
				false, false, false, false, false, false);
		pers[7] = new Characteristics(false, false, false, false, false, false, false, false, false, true, false, true,
				true, false, true, false, false, true);
		pers[8] = new Characteristics(true, false, true, false, false, false, true, true, true, false, true, false,
				false, false, false, false, false, false);
		pers[9] = new Characteristics(false, false, false, false, false, false, false, false, true, true, true, false,
				false, false, false, true, false, false);
		pers[10] = new Characteristics(false, true, false, true, false, false, false, false, false, true, true, true,
				true, false, false, false, false, false);
		pers[11] = new Characteristics(false, false, false, false, true, false, false, false, false, false, true, true,
				true, false, false, false, false, false);
		pers[12] = new Characteristics(false, false, true, false, false, false, false, false, false, true, false, true,
				false, false, false, false, false, false);
		pers[13] = new Characteristics(false, true, false, false, false, false, false, false, false, true, false, true,
				false, false, false, true, false, false);
		pers[14] = new Characteristics(false, false, false, false, false, false, true, true, true, false, true, false,
				false, false, false, true, false, false);
		pers[15] = new Characteristics(false, false, false, false, true, false, false, false, true, true, true, false,
				false, false, false, false, true, false);
		pers[16] = new Characteristics(false, false, false, true, false, false, false, true, true, false, true, false,
				false, false, false, false, false, false);
		pers[17] = new Characteristics(false, false, false, false, false, false, true, false, true, true, false, false,
				false, false, false, true, true, false);
		pers[18] = new Characteristics(false, true, false, false, false, false, true, false, false, false, true, true,
				true, true, false, true, false, false);
		pers[19] = new Characteristics(false, false, false, true, false, false, false, false, false, false, false, true,
				true, false, false, false, false, false);
		pers[20] = new Characteristics(false, false, false, true, false, false, false, false, false, true, false, true,
				false, true, false, false, false, false);
		pers[21] = new Characteristics(false, false, false, true, false, false, false, false, false, false, true, true,
				false, false, false, false, false, false);
		pers[22] = new Characteristics(false, false, false, false, false, true, true, false, false, false, false, true,
				false, false, false, true, false, false);
		pers[23] = new Characteristics(false, true, false, false, false, false, false, false, false, true, false, true,
				false, false, false, true, false, false);

	}

	private void settingButtonsValue(int i, boolean prop, boolean areProp, int indexChosenChr, boolean[] chrButtonValue,
			boolean chrEliminated) {

		if (chrButtonValue[i]) {
			chrButtonValue[i] = !(prop ^ areProp);
			String val = String.valueOf(chrButtonValue[i]);
			if (computer)
				remainingPlayerChr -= (val.length() - 4);
			else if (player)
				eliminatedCompChr -= (val.length() - 4);
		}
	}

	private void checkingProp(int indexQuestion, int indexChosenChr, boolean[] chrButtonValue, boolean chrEliminated1) {

		pers1 = new Characteristics[noPers];
		settingCharacteristics(pers1);
		Characteristics ales = pers1[indexChosenChr];

		for (int i = 0; i < noPers; i++) {
			if (i != indexChosenChr) {
				if (indexQuestion == 17)
					settingButtonsValue(i, pers1[i].africanAmerican, ales.africanAmerican, indexChosenChr,
							chrButtonValue, chrEliminated1);
				if (indexQuestion == 3)
					settingButtonsValue(i, pers1[i].beard, ales.beard, indexChosenChr, chrButtonValue, chrEliminated1);
				if (indexQuestion == 2)
					settingButtonsValue(i, pers1[i].blackHair, ales.blackHair, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 9)
					settingButtonsValue(i, pers1[i].blondeHair, ales.blondeHair, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 11)
					settingButtonsValue(i, pers1[i].brownHair, ales.brownHair, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 14)
					settingButtonsValue(i, pers1[i].bunnyEars, ales.bunnyEars, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 15)
					settingButtonsValue(i, pers1[i].curlyHair, ales.curlyHair, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 5)
					settingButtonsValue(i, pers1[i].earrings, ales.earrings, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 6)
					settingButtonsValue(i, pers1[i].female, ales.female, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 13)
					settingButtonsValue(i, pers1[i].glasses, ales.glasses, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 1)
					settingButtonsValue(i, pers1[i].longHair, ales.longHair, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 4)
					settingButtonsValue(i, pers1[i].moustache, ales.moustache, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 16)
					settingButtonsValue(i, pers1[i].naked, ales.naked, indexChosenChr, chrButtonValue, chrEliminated1);
				if (indexQuestion == 12)
					settingButtonsValue(i, pers1[i].old, ales.old, indexChosenChr, chrButtonValue, chrEliminated1);
				if (indexQuestion == 8)
					settingButtonsValue(i, pers1[i].pinkHair, ales.pinkHair, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 18)
					settingButtonsValue(i, pers1[i].pinkShirt, ales.pinkShirt, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 10)
					settingButtonsValue(i, pers1[i].whiteHair, ales.whiteHair, indexChosenChr, chrButtonValue,
							chrEliminated1);
				if (indexQuestion == 7)
					settingButtonsValue(i, pers1[i].man, ales.man, indexChosenChr, chrButtonValue, chrEliminated1);
			}
		}
	}

	public void checkingCharacters(int indexIntr, String chosenCharacter) throws Exception {

		if (!compChrCreated)
			computerChr = c.chooseChr();
		playerChr = Integer.valueOf(chosenCharacter);
		if (computerChr != 0)
			playerTurn(playerChr, indexIntr, computerChr, chosenCharacter);
	}

	private void playerTurn(int playerChr, int indexIntr, int computerChr, String chosenCharacter) throws Exception {

		computer = false;
		player = true;
		checkingProp(indexIntr, computerChr, valButoaneCalc, computer);
		score(24 - eliminatedCompChr);
		GUI.disablingButtons(valButoaneCalc, computer, player);
		computerTurn(playerChr, indexIntr, computerChr, chosenCharacter);
	}

	private void computerTurn(int playerChr, int indexIntr, int computerChr, String chosenCharacter) throws Exception {

		computer = true;
		player = false;
		int indexIntrCalc = c.askQuestions();
		questionDialog(indexIntrCalc);
		checkingProp(indexIntrCalc, playerChr, valButoaneJucator, player);
		GUI.disablingButtons(valButoaneJucator, computer, player);
		if (eliminatedCompChr == 1)
			newGame(0);
		else if (remainingPlayerChr == 1)
			newGame(1);
		GUI.chooseQuestion(chosenCharacter);
	}

	private void questionDialog(int indexIntrCalc) {

		JDialog.setDefaultLookAndFeelDecorated(true);
		JOptionPane.showConfirmDialog(null, intrebariText[indexIntrCalc], "Answer", JOptionPane.YES_NO_OPTION);

	}

	public void score(int persons) {

		GUI.score(persons * 10);
	}

	private void newGame(int player) throws Exception {

		String winningMessage[] = { "Congratulation the player won!", "Sorry! But the computer won this time!" };
		JOptionPane.showMessageDialog(null, winningMessage[player]);
		new GUI();
	}

}