import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Game {

	private Characteristics pers1[];
	private final int nrPers = 24;
	public int score;
	private int chrRamaseCalc, chrRamaseJucator = nrPers;
	private boolean jucator = true;
	private boolean calc = false;
	boolean[] valButoaneCalc, valButoaneJucator;
	private String intrebariText[];

	Game() throws Exception {

		valButoaneCalc = new boolean[nrPers];
		valButoaneJucator = new boolean[nrPers];
		for (int i = 0; i < nrPers; i++) {
			valButoaneCalc[i] = true;
			valButoaneJucator[i] = true;
		}

		Reading text = new Reading();
		int count = text.indexIntrebari();
		intrebariText = new String[count];
		text.CitireIntrebari(intrebariText, count);
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

	private void settingButtonsValue(boolean areProp, int pozAles, boolean[] v_butonCaracter, int chrEliminated) {

		pers1 = new Characteristics[nrPers];
		settingCharacteristics(pers1);
		System.out.println("intra in elimina");
		for (int i = 0; i < nrPers; i++) {
			if (i != pozAles && v_butonCaracter[i]) {
				v_butonCaracter[i] = pers1[i].african_american ^ areProp;
				v_butonCaracter[i] = pers1[i].beard ^ areProp;
				v_butonCaracter[i] = pers1[i].black_hair ^ areProp;
				v_butonCaracter[i] = pers1[i].blonde_hair ^ areProp;
				v_butonCaracter[i] = pers1[i].brown_hair ^ areProp;
				v_butonCaracter[i] = pers1[i].bunny_ears ^ areProp;
				v_butonCaracter[i] = pers1[i].curly_hair ^ areProp;
				v_butonCaracter[i] = pers1[i].earrings ^ areProp;
				v_butonCaracter[i] = pers1[i].female ^ areProp;
				v_butonCaracter[i] = pers1[i].glasses ^ areProp;
				v_butonCaracter[i] = pers1[i].long_hair ^ areProp;
				v_butonCaracter[i] = pers1[i].moustache ^ areProp;
				v_butonCaracter[i] = pers1[i].naked ^ areProp;
				v_butonCaracter[i] = pers1[i].old ^ areProp;
				v_butonCaracter[i] = pers1[i].pink_hair ^ areProp;
				v_butonCaracter[i] = pers1[i].pink_shirt ^ areProp;
				v_butonCaracter[i] = pers1[i].white_hair ^ areProp;
				v_butonCaracter[i] = pers1[i].man ^ areProp;
				chrEliminated += -("false".indexOf("" + v_butonCaracter[i]));
			}
		}
		System.out.println("caractere eliminate " + chrEliminated + "\n");

	}

	private boolean checkingProp(int indexQuestion, int pozAles) {

		pers1 = new Characteristics[nrPers];
		settingCharacteristics(pers1);
		Characteristics ales = pers1[pozAles];
		System.out.println("intra in properties");

		if (ales.african_american && (indexQuestion == 17))
			return true;
		if (ales.beard && (indexQuestion == 3))
			return true;
		if (ales.black_hair && (indexQuestion == 2))
			return true;
		if (ales.blonde_hair && (indexQuestion == 9))
			return true;
		if (ales.brown_hair && (indexQuestion == 11))
			return true;
		if (ales.bunny_ears && (indexQuestion == 14))
			return true;
		if (ales.curly_hair && (indexQuestion == 15))
			return true;
		if (ales.earrings && (indexQuestion == 5))
			return true;
		if (ales.female && (indexQuestion == 6))
			return true;
		if (ales.glasses && (indexQuestion == 13))
			return true;
		if (ales.long_hair && (indexQuestion == 1))
			return true;
		if (ales.moustache && (indexQuestion == 4))
			return true;
		if (ales.naked && (indexQuestion == 16))
			return true;
		if (ales.old && (indexQuestion == 12))
			return true;
		if (ales.pink_hair && (indexQuestion == 8))
			return true;
		if (ales.pink_shirt && (indexQuestion == 18))
			return true;
		if (ales.white_hair && (indexQuestion == 10))
			return true;
		if (ales.man && (indexQuestion == 7))
			return true;
		return false;
	}

	public void score(int persons, int aScore) {

		score += persons * 10;
	}

	public void newGame() throws Exception {

		JOptionPane.showMessageDialog(null, "Congratulation you won! /n Your score is: " + score);
		new GUI();

	}

	protected void eliminateCharacters(int indexIntr, String chrAles2) throws Exception {

		Computer c = new Computer();
		GUI g = new GUI();
		int playerChr = Integer.valueOf(chrAles2);
		int computerChr = c.chooseChr();
		boolean hasProp;

		while (chrRamaseCalc >= 1 || chrRamaseJucator >= 1)
			if (chrRamaseCalc == 1 || chrRamaseJucator == 1)
				newGame();
			else {
				if (jucator) {
					System.out.println("tura jucator");
					int nrChrElim = 0;
					hasProp = checkingProp(indexIntr, computerChr);
					settingButtonsValue(hasProp, computerChr, valButoaneCalc, nrChrElim);
					g.disablingButtons(valButoaneCalc, calc, jucator);
					chrRamaseCalc -= nrChrElim;
					g.chooseQuestion(chrAles2);
					calc = true;
					jucator = false;
				} else {
					System.out.println("tura calc");
					int nrChrElim = 0;
					int indexIntrCalc = c.askQuestions();
					if (questionDialog(indexIntrCalc)) {
						hasProp = checkingProp(indexIntrCalc, playerChr);
						settingButtonsValue(hasProp, playerChr, valButoaneJucator, nrChrElim);
						g.disablingButtons(valButoaneJucator, calc, jucator);
					}
					calc = false;
					jucator = true;
					chrRamaseJucator -= nrChrElim;
				}
			}
	}

	private boolean questionDialog(int indexIntrCalc2) {

		JDialog j = new JDialog();
		j.setDefaultLookAndFeelDecorated(true);
		int response = JOptionPane.showConfirmDialog(null, intrebariText[indexIntrCalc2], "Answer",
				JOptionPane.YES_NO_OPTION);
		if (response == JOptionPane.CLOSED_OPTION)
			return false;
		j.dispose();
		return true;

	}
}