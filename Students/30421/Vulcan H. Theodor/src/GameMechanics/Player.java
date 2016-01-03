package GameMechanics;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import UI.Board;

public class Player {

	public RealEstateTile[] ownedProperties;
	public int wallet=10000;
	public int position;
	public boolean isBankrupt= false;
	public String name;
	public ImageIcon token;
	public int bailoutCard= 0;
	public boolean hasRolled=false;
	
	
	public int getWallet() {
		return wallet;
	}
	public void buyProperty(int price) {
		if ((wallet-price)>0) this.wallet = wallet-price;
		else Board.notEnoughMoneyPrompt();
	}
	
	public void setName(){
		ImageIcon icon = new ImageIcon("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\chooseNameImage.png");
		String name= null;
		boolean condition=true;
		
		while (condition==true){
			String[] options = {"OK"};
			
			JPanel panel = new JPanel();
			JPanel emptyPanel = new JPanel();
			JPanel containerPanel = new JPanel();
			JLabel message = new JLabel("Enter Your name: ");
			JTextField userInput = new JTextField(10);
			userInput.setPreferredSize(new Dimension(10, 20));
			
			emptyPanel.setPreferredSize(new Dimension(20, 40));
			containerPanel.add(message, BorderLayout.CENTER);
			containerPanel.add(userInput, BorderLayout.CENTER);
			
			panel.setLayout(new BorderLayout());
			panel.add(containerPanel, BorderLayout.CENTER);
			panel.add(emptyPanel, BorderLayout.NORTH);
			
			int selectedOption = JOptionPane.showOptionDialog(null, panel, "Monopoly",
					JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					icon, options , options[0]);	

			if(selectedOption == 0)
			{
			    name = userInput.getText();
			}
			
			condition= (name==null) || name.isEmpty() || name.length()>10;
			if (condition==true) JOptionPane.showMessageDialog(panel,
					"Choose a name no more than 10 characters long", "Error",
					JOptionPane.ERROR_MESSAGE);

		System.out.println(name);

		}
		this.name= name;	
	}
	
	public String setToken(Object[] possibleValues){
		
		ImageIcon icon = new ImageIcon("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\chooseIconImage.png");
		
		 Object selectedValue = JOptionPane.showInputDialog(null,
		             "Select your token: ", "Monopoly",
		             JOptionPane.INFORMATION_MESSAGE, icon,
		             possibleValues, possibleValues[0]);
		
		 String stringSelection= selectedValue.toString();
		 System.out.println(stringSelection);
		 switch (stringSelection){
		 case "dog" : this.token= new ImageIcon("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\50dog.png");
		 				break;
		 case "hat" : this.token= new ImageIcon("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\50hat.png");
		 				break;
		 case "car" : this.token= new ImageIcon("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\50car.png");
		 				break;
		 case "barrow" : this.token= new ImageIcon("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\50barrow.png");
		 				break;
		 case "thimble" : this.token= new ImageIcon("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\50thimble.png");
		 				break;
		 case "boot" : this.token= new ImageIcon("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\50boot.png");
		 				break;
		 default : this.token=null;

		 }
		 
		 return stringSelection;
	}
	
	
}
