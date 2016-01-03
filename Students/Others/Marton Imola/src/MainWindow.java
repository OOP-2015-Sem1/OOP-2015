import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame implements GameStateManipulator{

	JPanel lifePanel=new JPanel();
	JPanel columnRulePanel=new JPanel();
	JPanel rowRulePanel=new JPanel();
	JPanel tablePanel=new JPanel();
	int life;
	JLabel lifeLabel=new JLabel("life",SwingConstants.CENTER);
	Table table;
	static int SQUARE_SIZE = 50;
	static int RULES_SIZE = 90;
	
	public void setLevel(String title, Table table)
	{
		this.table=table;
		this.setTitle(title);
		
		rowRulePanel.removeAll();
		columnRulePanel.removeAll();
		tablePanel.removeAll();
		//GridLayout windowLayout=new GridLayout(2, 2);
		//this.setLayout(windowLayout);
		life=table.getNumberOfColumns()*table.getNumberOfRows();

		updateLifeLabel();
		
		
		GridLayout rowRulePanelLayout=new GridLayout(table.getNumberOfRows(),1);
		rowRulePanel.setLayout(rowRulePanelLayout);
		for(int row=0; row<table.getNumberOfRows(); row++)
		{
			rowRulePanel.add(new JLabel(table.getRowRule(row),SwingConstants.CENTER));
		}
		
		GridLayout columnRulePanelLayout=new GridLayout(1, table.getNumberOfColumns());
		columnRulePanel.setLayout(columnRulePanelLayout);
		for(int column=0; column<table.getNumberOfColumns(); column++)
		{
			columnRulePanel.add(new JLabel(table.getColumnRule(column),SwingConstants.CENTER));
		}
		
		GridLayout grid=new GridLayout(table.getNumberOfRows(), table.getNumberOfColumns());
		tablePanel.setLayout(grid);
		for(int row=0; row<table.getNumberOfRows(); row++)
			for(int column=0; column<table.getNumberOfColumns(); column++)
			{
				Square square =table.getSquare(column, row);
				SquareJButton buton=new SquareJButton(square);
				square.setLife(this);
				tablePanel.add(buton);
			}
		

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//do layout
		int layoutTableWidth=SQUARE_SIZE*table.getNumberOfColumns();
		int layoutTableHeight=SQUARE_SIZE*table.getNumberOfRows();
		this.setSize(RULES_SIZE*2+layoutTableWidth, RULES_SIZE*2+layoutTableHeight);
		lifePanel.setBounds(0, 0, RULES_SIZE, RULES_SIZE);
		columnRulePanel.setBounds(RULES_SIZE, 0, layoutTableWidth, RULES_SIZE);
		rowRulePanel.setBounds(0, RULES_SIZE, RULES_SIZE, layoutTableHeight);
		tablePanel.setBounds(RULES_SIZE, RULES_SIZE, layoutTableWidth, layoutTableHeight);
		
		// TODO Auto-generated constructor stub
	}
	
	public MainWindow(Table table) throws HeadlessException {

		this.setLayout(null);
		this.add(lifePanel);
		this.add(columnRulePanel);
		this.add(rowRulePanel);
		this.add(tablePanel);
		this.setJMenuBar(createMenuBar());
		
		GridLayout lifePanelLayout=new GridLayout(1, 1);
		lifePanel.setLayout(lifePanelLayout);
		lifePanel.add(lifeLabel);
		
		this.setLevel(Levels.LevelData[0].levelName, table);
	}
	private JMenuBar createMenuBar()
	{
		JMenuBar menuBar=new JMenuBar();
		JMenu menu=new JMenu("Levels");
		menuBar.add(menu);
		

		Level level = Levels.LevelData[0];
		JMenuItem menuItem=new JMenuItem(level.levelName);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Level level = Levels.LevelData[0];
				Table table = TableCreator.CreateFromString(level.rows, level.columns, level.levelSource);
				MainWindow.this.setLevel(level.levelName, table);
			}
		});
		

		level = Levels.LevelData[1];
		menuItem=new JMenuItem(level.levelName);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Level level = Levels.LevelData[1];
				Table table = TableCreator.CreateFromString(level.rows, level.columns, level.levelSource);
				MainWindow.this.setLevel(level.levelName, table);
			}
		});		

		level = Levels.LevelData[2];
		menuItem=new JMenuItem(level.levelName);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Level level = Levels.LevelData[2];
				Table table = TableCreator.CreateFromString(level.rows, level.columns, level.levelSource);
				MainWindow.this.setLevel(level.levelName, table);
			}
		});
		

		level = Levels.LevelData[3];
		menuItem=new JMenuItem(level.levelName);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Level level = Levels.LevelData[3];
				Table table = TableCreator.CreateFromString(level.rows, level.columns, level.levelSource);
				MainWindow.this.setLevel(level.levelName, table);
			}
		});
		
		return menuBar;
	}

	private void updateLifeLabel() {
		// TODO Auto-generated method stub
		lifeLabel.setText("Life: "+life);
	}

	@Override
	public void decrementLife(int amount) {
		// TODO Auto-generated method stub
		life-=amount;
		updateLifeLabel();
		
	}

	@Override
	public int getCurrentLife() {
		// TODO Auto-generated method stub
		return life;
	}
	
	public boolean hasWon(){
		int countGoal=0, countSuccess=0;
		for(int row=0; row<table.getNumberOfRows(); row++)
			for(int column=0; column<table.getNumberOfColumns(); column++)
			{
				Square square =table.getSquare(column, row);
				if(square.isGoal())
				{
					countGoal++;
				}
				if(square.isSuccess())
				{
					countSuccess++;
				}
				
			}
		if(countGoal==countSuccess)
			return true;
		else return false;
	}
	
	public boolean hasLost()
	{
		if(life<=0)
			return true;
		else
			return false;
	}
	@Override
	public void userActionPerformed() {
		// TODO Auto-generated method stub
		if(hasLost())
		{
			disableInputs();
			JOptionPane.showMessageDialog(this, "YOU FAILED!");
			
		}
		else if(hasWon())
		{
			disableInputs();
			JOptionPane.showMessageDialog(this, "CONGRAAAAAAAAATS!!! :)");
		}
	}

	private void disableInputs() {
		// TODO Auto-generated method stub
		Component[] components=tablePanel.getComponents();
		for(int i=0; i<components.length; i++)
		{
			components[i].setEnabled(false);
		}
	}
	
	
}
