import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Game implements ItemListener, Runnable {

	protected JFrame frmPoker;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField RaiseSum,Money,OppMoney,Pot,RepLabel;
	private final int MAX_SELECTIONS_ALLOWED = 3;
	private int numSelected = 0;
	public JCheckBox[] checkBoxes;
	private int YourBet = 20, OppBet=20, count,WinLvl,OppWinLvl;
	public JLabel Card1,Card2,Card3,Card4,Card5;
	
	// Launch the application.

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try {
					Game window = new Game();
					window.frmPoker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Game() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmPoker = new JFrame();
		frmPoker.setTitle("Poker");
		frmPoker.setBounds(50, 50, 1100, 675);
		frmPoker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPoker.getContentPane().setLayout(null);
		String[] images = new String[] { "images/2clubs.png", "images/2diamonds.png", "images/2hearts.png",
				"images/2spades.png", "images/3clubs.png", "images/3diamonds.png", "images/3hearts.png",
				"images/3spades.png", "images/4clubs.png", "images/4diamonds.png", "images/4hearts.png",
				"images/4spades.png", "images/5clubs.png", "images/5diamonds.png", "images/5hearts.png",
				"images/5spades.png", "images/6clubs.png", "images/6diamonds.png", "images/6hearts.png",
				"images/6spades.png", "images/7clubs.png", "images/7diamonds.png", "images/7hearts.png",
				"images/7spades.png", "images/8clubs.png", "images/8diamonds.png", "images/8hearts.png",
				"images/8spades.png", "images/9clubs.png", "images/9diamonds.png", "images/9hearts.png",
				"images/9spades.png", "images/10clubs.png", "images/10diamonds.png", "images/10hearts.png",
				"images/10spades.png", "images/Jclubs.png", "images/Jdiamonds.png", "images/Jhearts.png",
				"images/Jspades.png", "images/Qclubs.png", "images/Qdiamonds.png", "images/Qhearts.png",
				"images/Qspades.png", "images/Kclubs.png", "images/Kdiamonds.png", "images/Khearts.png",
				"images/Kspades.png", "images/Aclubs.png", "images/Adiamonds.png", "images/Ahearts.png",
				"images/Aspades.png" };
		ArrayList<String> img = new ArrayList<String>(Arrays.asList(images));

		JLabel OppCredit = new JLabel("Opp\r\nCredit:");
		OppCredit.setForeground(Color.WHITE);
		OppCredit.setBounds(896, 63, 70, 20);
		frmPoker.getContentPane().add(OppCredit);

		JLabel Prize = new JLabel("Pot:");
		Prize.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Prize.setForeground(Color.WHITE);
		Prize.setBounds(908, 263, 70, 40);
		frmPoker.getContentPane().add(Prize);

		JLabel Credit = new JLabel("\tCredit:");
		Credit.setForeground(Color.WHITE);
		Credit.setBounds(924, 553, 42, 23);
		frmPoker.getContentPane().add(Credit);
		Money = new JTextField();
		Money.setText("1000");
		Money.setEditable(false);
		Money.setBounds(979, 554, 75, 20);
		frmPoker.getContentPane().add(Money);

		OppMoney = new JTextField();
		OppMoney.setEditable(false);
		OppMoney.setText("1000");
		OppMoney.setBounds(970, 63, 70, 20);
		frmPoker.getContentPane().add(OppMoney);
		OppMoney.setColumns(10);

		// checkboxes

		checkBoxes = new JCheckBox[5];
		for (int i = 0; i < checkBoxes.length; i++) {
			checkBoxes[i] = new JCheckBox("Replace Card " + (i + 1));
			checkBoxes[i].addItemListener(this);
			frmPoker.getContentPane().add(checkBoxes[i]);
		}
		checkBoxes[0].setBounds(30, 592, 150, 30);
		checkBoxes[1].setBounds(200, 592, 150, 30);
		checkBoxes[2].setBounds(370, 592, 150, 30);
		checkBoxes[3].setBounds(540, 592, 150, 30);
		checkBoxes[4].setBounds(720, 592, 150, 30);

		// player cards
		JLabel Card1 = new JLabel();
		JLabel Card2 = new JLabel();
		JLabel Card3 = new JLabel();
		JLabel Card4 = new JLabel();
		JLabel Card5 = new JLabel();
		int i1, i2, i3, i4, i5;
		i1 = (int) Math.floor(Math.random() * img.size());
		Card1.setIcon(new ImageIcon(img.get(i1)));
		img.remove(i1);
		i2 = (int) Math.floor(Math.random() * img.size());
		Card2.setIcon(new ImageIcon(img.get(i2)));
		img.remove(i2);
		i3 = (int) Math.floor(Math.random() * img.size());
		Card3.setIcon(new ImageIcon(img.get(i3)));
		img.remove(i3);
		i4 = (int) Math.floor(Math.random() * img.size());
		Card4.setIcon(new ImageIcon(img.get(i4)));
		img.remove(i4);
		i5 = (int) Math.floor(Math.random() * img.size());
		Card5.setIcon(new ImageIcon(img.get(i5)));
		img.remove(i5);

		Card1.setBounds(30, 350, 155, 218);
		Card2.setBounds(200, 350, 150, 218);
		Card3.setBounds(370, 350, 150, 218);
		Card4.setBounds(540, 350, 150, 218);
		Card5.setBounds(720, 350, 150, 218);

		frmPoker.getContentPane().add(Card1);
		frmPoker.getContentPane().add(Card2);
		frmPoker.getContentPane().add(Card3);
		frmPoker.getContentPane().add(Card4);
		frmPoker.getContentPane().add(Card5);

		// opponent cards
		JLabel OppCard1 = new JLabel();
		JLabel OppCard2 = new JLabel();
		JLabel OppCard3 = new JLabel();
		JLabel OppCard4 = new JLabel();
		JLabel OppCard5 = new JLabel();

		OppCard1.setIcon(new ImageIcon("images/cardback.jpg"));
		OppCard2.setIcon(new ImageIcon("images/cardback.jpg"));
		OppCard3.setIcon(new ImageIcon("images/cardback.jpg"));
		OppCard4.setIcon(new ImageIcon("images/cardback.jpg"));
		OppCard5.setIcon(new ImageIcon("images/cardback.jpg"));
		OppCard1.setBounds(30, 25, 155, 218);
		OppCard2.setBounds(200, 25, 155, 218);
		OppCard3.setBounds(370, 25, 155, 218);
		OppCard4.setBounds(540, 25, 155, 218);
		OppCard5.setBounds(720, 25, 155, 218);

		JLabel backIMG = new JLabel("");
		backIMG.setIcon(new ImageIcon("images/background.jpg"));
		backIMG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		backIMG.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		backIMG.setBounds(0, 0, 1088, 650);

		frmPoker.getContentPane().add(OppCard1);
		frmPoker.getContentPane().add(OppCard2);
		frmPoker.getContentPane().add(OppCard3);
		frmPoker.getContentPane().add(OppCard4);
		frmPoker.getContentPane().add(OppCard5);

		JButton Replace = new JButton("Replace Cards");
		Replace.setBounds(924, 587, 130, 23);
		frmPoker.getContentPane().add(Replace);
		
		JRadioButton Fold = new JRadioButton("Fold");
		Fold.setEnabled(false);
		buttonGroup.add(Fold);
		Fold.setBounds(924, 462, 109, 23);
		frmPoker.getContentPane().add(Fold);

		JRadioButton Check = new JRadioButton("Check");
		Check.setEnabled(false);
		buttonGroup.add(Check);
		Check.setBounds(924, 381, 109, 23);
		frmPoker.getContentPane().add(Check);
		RaiseSum = new JTextField();
		RaiseSum.setEnabled(false);
		RaiseSum.setEditable(false);
		RaiseSum.setBounds(924, 435, 109, 20);
		frmPoker.getContentPane().add(RaiseSum);
		JRadioButton Raise = new JRadioButton("Raise");
		Raise.setEnabled(false);
		buttonGroup.add(Raise);
		Raise.setBounds(924, 410, 109, 23);
		frmPoker.getContentPane().add(Raise);
		Raise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Raise.isSelected()) {
					RaiseSum.setEditable(true);
				}
			}
		});
		
		Pot = new JTextField();
		Pot.setEditable(false);
		Pot.setBounds(979, 260, 100, 40);
		frmPoker.getContentPane().add(Pot);
		Pot.setColumns(10);
		Pot.setText(Integer.toString(YourBet+OppBet));
		
		JButton Decide = new JButton("Ok");
		Decide.setEnabled(false);
		
		Replace.addActionListener(new ActionListener() {
			int re;

			public void actionPerformed(ActionEvent e) {

				if (checkBoxes[0].isSelected()) {
					re = (int) Math.floor(Math.random() * img.size());
					Card1.setIcon(new ImageIcon(img.get(re)));
					img.remove(re);
				}
				if (checkBoxes[1].isSelected()) {
					re = (int) Math.floor(Math.random() * img.size());
					Card2.setIcon(new ImageIcon(img.get(re)));
					img.remove(re);
				}
				if (checkBoxes[2].isSelected()) {
					re = (int) Math.floor(Math.random() * img.size());
					Card3.setIcon(new ImageIcon(img.get(re)));
					img.remove(re);
				}
				if (checkBoxes[3].isSelected()) {
					re = (int) Math.floor(Math.random() * img.size());
					Card4.setIcon(new ImageIcon(img.get(re)));
					img.remove(re);
				}
				if (checkBoxes[4].isSelected()) {
					re = (int) Math.floor(Math.random() * img.size());
					Card5.setIcon(new ImageIcon(img.get(re)));
					img.remove(re);
				}
				for (int i = 0; i < 5; i++) {
					checkBoxes[i].setEnabled(false);
				}
				Replace.setEnabled(false);
				RepLabel.setText("Place your bet!");
				Decide.setEnabled(true);
				Check.setEnabled(true);
				Fold.setEnabled(true);
				Raise.setEnabled(true);
				RaiseSum.setEnabled(true);
				
			}
		});

		

		RepLabel = new JTextField();
		RepLabel.setEditable(false);
		RepLabel.setHorizontalAlignment(SwingConstants.CENTER);
		RepLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		RepLabel.setText("Replace up to 3 cards");
		RepLabel.setBounds(130, 262, 670, 41);
		
		Decide.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (Raise.isSelected()) {
					YourBet = YourBet + Integer.parseInt(RaiseSum.getText());
				}
				if (Fold.isSelected()) {
					OppMoney.setText(Integer.toString(Integer.parseInt(OppMoney.getText())+YourBet));
					YourBet = 0;
					RepLabel.setText("Fold");
				}
				if (Check.isSelected()) {
					if (!(YourBet == OppBet))
						JOptionPane.showMessageDialog(null, "You can't check if your bet is different");
				}
				int i1, i2, i3, i4, i5;
				i1 = (int) Math.floor(Math.random() * img.size());
				OppCard1.setIcon(new ImageIcon(img.get(i1)));
				img.remove(i1);
				i2 = (int) Math.floor(Math.random() * img.size());
				OppCard2.setIcon(new ImageIcon(img.get(i2)));
				img.remove(i2);
				i3 = (int) Math.floor(Math.random() * img.size());
				OppCard3.setIcon(new ImageIcon(img.get(i3)));
				img.remove(i3);
				i4 = (int) Math.floor(Math.random() * img.size());
				OppCard4.setIcon(new ImageIcon(img.get(i4)));
				img.remove(i4);
				i5 = (int) Math.floor(Math.random() * img.size());
				OppCard5.setIcon(new ImageIcon(img.get(i5)));
				img.remove(i5);
				
				
					
			}
		});
		Decide.setBounds(924, 492, 109, 23);
		frmPoker.getContentPane().add(Decide);

		frmPoker.getContentPane().add(RepLabel);
		RepLabel.setColumns(10);
		frmPoker.getContentPane().add(backIMG);

	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			numSelected++;
			if (numSelected == MAX_SELECTIONS_ALLOWED) {
				for (int i = 0; i < checkBoxes.length; i++) {
					checkBoxes[i].setEnabled(checkBoxes[i].isSelected());
				}
			}
		} else {
			numSelected--;
			for (int i = 0; i < checkBoxes.length; i++) {
				checkBoxes[i].setEnabled(true);
			}
		}

	}

	@Override
	public void run() {
	}
}
