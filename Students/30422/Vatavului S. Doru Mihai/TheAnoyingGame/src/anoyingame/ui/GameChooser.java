package anoyingame.ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameChooser extends JFrame {

	private JPanel blankPanel = new JPanel();
	private JButton game1 = new JButton("The Anoying Color Game");
	private JButton game2 = new JButton("The Even More Anoying Memory Game");
	private JLabel imageLabel;
	private ImageIcon image;
	
	public GameChooser(){
		this.setLayout(new GridLayout(2,1));
		image = new ImageIcon(getClass().getResource("GameLogo.png"));
		imageLabel = new JLabel(image);
		imageLabel.setBackground(Color.WHITE);
		this.setBackground(Color.WHITE);
		this.add(imageLabel);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5,1));
		blankPanel = new JPanel();
		buttonPanel.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);		
		game1.setBackground(new Color(0,178,192));
		game1.setForeground(new Color(251,233,163));
		game2.setBackground(new Color(0,178,192));
		game2.setForeground(new Color(251,233,163));
		game1.setFont(game1.getFont().deriveFont(Font.BOLD));
		game2.setFont(game2.getFont().deriveFont(Font.BOLD));
		buttonPanel.add(game1);
		buttonPanel.add(game2);
		blankPanel = new JPanel();
		buttonPanel.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
		blankPanel = new JPanel();
		buttonPanel.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
		
		this.add(buttonPanel);
		this.setSize(400,500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public JButton getButton1(){
		return game1;
	}
	
	public JButton getButton2(){
		return game2;
	}
}
