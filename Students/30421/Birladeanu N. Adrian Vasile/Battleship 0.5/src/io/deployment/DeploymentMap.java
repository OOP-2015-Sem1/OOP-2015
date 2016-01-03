package io.deployment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;
import io.deployment.shipListeners.*;
import io.deployment.orientationListeners.*;
import resources.MapModel;

public class DeploymentMap extends JPanel {
	
	private static final long serialVersionUID = 4652347973668811154L;
	public MapModel harta;
	public DeploymentMap(){
	/**
	 * Declaration of buttons on the panel:
	 */
		JButton carrier=new JButton("Carrier");
		JButton battleship=new JButton("Battleship");
		JButton cruiser=new JButton("Cruiser");
		JButton destroyer=new JButton("Destroyer");
		JRadioButton horizontal=new JRadioButton("Horizontal");
		JRadioButton vertical=new JRadioButton("Vertical");
		ButtonGroup buttonGroup=new ButtonGroup();
		JButton reset=new JButton("Reset");
		JButton next=new JButton("Next");
		
		this.setSize(new Dimension(1366, 730));
		this.setLayout(new BorderLayout());
		
		/**
		 * Setup the interactive map
		 */
		
		harta=new MapModel();
		harta.interactiveMap.setPreferredSize(new Dimension(630, 630));
		this.add(harta.interactiveMap, BorderLayout.CENTER);
		harta.interactiveMap.addDeploymentListener();
		
		
		/**
		 * Setup the panels
		 */
		
		JPanel northPanel=new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel instructions=new JLabel("Select a type of ship "
				+ "and place it on the map.");
		northPanel.add(instructions);
		northPanel.setPreferredSize(new Dimension(1366, 50));
		northPanel.setBackground(Color.WHITE);
		this.add(northPanel, BorderLayout.NORTH);
		
		JPanel eastPanel=new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(carrier);
		ActionListener carrierListener=new CarrierListener(harta);
		carrier.addActionListener(carrierListener);
		eastPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		eastPanel.add(battleship);
		ActionListener battleshipListener=new BattleshipListener(harta);
		battleship.addActionListener(battleshipListener);
		eastPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		eastPanel.add(cruiser);
		ActionListener cruiserListener=new CruiserListener(harta);
		cruiser.addActionListener(cruiserListener);
		eastPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		eastPanel.add(destroyer);
		ActionListener destroyerListener=new DestroyerListener(harta);
		destroyer.addActionListener(destroyerListener);
		eastPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		ActionListener horizontalListener=new HorizontalListener(harta);
		horizontal.addActionListener(horizontalListener);
		eastPanel.add(horizontal);
		buttonGroup.add(horizontal);
		horizontal.setSelected(true);
		
		ActionListener verticalListener=new VerticalListener(harta);
		vertical.addActionListener(verticalListener);
		buttonGroup.add(vertical);
		eastPanel.add(vertical);
		
		
		eastPanel.setPreferredSize(new Dimension(368, 630));
		eastPanel.setBackground(Color.WHITE);
		this.add(eastPanel, BorderLayout.EAST);
		
		JPanel southPanel=new JPanel();
		southPanel.setPreferredSize(new Dimension(1366, 50));
		southPanel.setBackground(Color.WHITE);
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		ActionListener resetAction=new Reset(harta);
		reset.addActionListener(resetAction);
		ActionListener nextAction=new NextButton(harta);
		next.addActionListener(nextAction);
		southPanel.add(reset);
		southPanel.add(next);
		this.add(southPanel, BorderLayout.SOUTH);
		
		JPanel westPanel=new JPanel();
		westPanel.setBackground(Color.WHITE);
		westPanel.setPreferredSize(new Dimension(368, 630));
		this.add(westPanel, BorderLayout.WEST);
		
		Font font=new Font("Verdana", Font.ROMAN_BASELINE, 20);
		JLabel carrierLabel=new JLabel("Carriers: 1");
		carrierLabel.setFont(font);
		JLabel battleshipLabel=new JLabel("Battleships: 1");
		battleshipLabel.setFont(font);
		JLabel cruiserLabel=new JLabel("Cruisers: 2");
		cruiserLabel.setFont(font);
		JLabel destroyerLabel=new JLabel("Destroyers: 3");
		destroyerLabel.setFont(font);
		westPanel.setLayout(null);
		westPanel.add(carrierLabel);
		carrierLabel.setBounds(100, 50, 200, 50);
		westPanel.add(battleshipLabel);
		battleshipLabel.setBounds(90, 150, 210, 50);
		westPanel.add(cruiserLabel);
		cruiserLabel.setBounds(100, 250, 210, 50);
		westPanel.add(destroyerLabel);
		destroyerLabel.setBounds(90, 350, 210, 50);
		
	}	
}

