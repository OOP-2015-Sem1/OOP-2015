package userInterfacePack;

import java.awt.BorderLayout;

import javax.swing.JPanel;



public class RightPanel extends JPanel{
	
	private static final long serialVersionUID = 8620038472072039564L;
	
	public RightPanel(ButtonsPanel buttonsPanel){
		
		setLayout(new BorderLayout());
		add(buttonsPanel, BorderLayout.CENTER);
	}
	

}
