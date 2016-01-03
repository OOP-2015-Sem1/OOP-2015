package com.player.graphics;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.player.program.FileList;

public class ControlPanel extends JPanel implements ActionListener {
	public static final long serialVersionUID = 1L;
	private JButton prevButton, playButton, nextButton, stopButton, renameButton, saveButton, closeButton;
	private JPanel panel;
	public static final String[] options={"1: Artist - Title","2: Title","3: Artist - Title - Album","4: Title - Album"};

	public ControlPanel() {
		super();
		this.setLayout(new GridLayout(5, 1, 0, 30));
		panel = new JPanel(new GridLayout(1, 3, 0, 0));
		ImageIcon icon = new ImageIcon("res/prev.png");
		prevButton = new JButton(icon);
		icon = new ImageIcon("res/play.png");
		playButton = new JButton(icon);
		icon = new ImageIcon("res/next.png");
		nextButton = new JButton(icon);
		panel.add(prevButton);
		panel.add(playButton);
		panel.add(nextButton);
		icon = new ImageIcon("res/stop.png");
		stopButton = new JButton(icon);
		stopButton.setPreferredSize(new Dimension(25, 35));
		renameButton = new JButton("Rename");
		saveButton = new JButton("Save");
		closeButton = new JButton("Close");
		addCommands();
		this.add(panel);
		this.add(stopButton);
		this.add(renameButton);
		this.add(saveButton);
		this.add(closeButton);

	}

	private void addCommands() {

		prevButton.addActionListener(this);
		prevButton.setActionCommand("Prev");
		playButton.addActionListener(this);
		playButton.setActionCommand("Play");
		nextButton.addActionListener(this);
		nextButton.setActionCommand("Next");
		stopButton.addActionListener(this);
		stopButton.setActionCommand("Stop");
		renameButton.addActionListener(this);
		renameButton.setActionCommand("Rename");
		saveButton.addActionListener(this);
		saveButton.setActionCommand("Save");
		closeButton.addActionListener(this);
		closeButton.setActionCommand("Close");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Close")) {
			MainWindow.closeProgram();
		}
		if (e.getActionCommand().equals("Play")) {
			FileList.playFile();
			playButton.setActionCommand("Pause");
		}
		if (e.getActionCommand().equals("Stop")) {
			FileList.stopFile();
			playButton.setActionCommand("Play");
		}
		if (e.getActionCommand().equals("Pause")) {
			FileList.pauseFile();
			playButton.setActionCommand("Play");
		}
		if (e.getActionCommand().equals("Save")) {
			FileList.setMetadata();
		}
		if (e.getActionCommand().equals("Rename")) {
			int option;
			String input = (String)JOptionPane.showInputDialog(null,"Choose your pattern","Rename files",JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
			option = JOptionPane.showConfirmDialog(null,
					"Warning! If there are any files with null or damaged ID3 tags, those those files will not be renamed!",
					"Confirm rename", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				
				FileList.renameFiles(input);
			}

		}
		if(e.getActionCommand().equals("Next")){
			if(FileList.getLastIndex()<FileList.getFileVector().size()-1){
				FileList.setLastIndex(FileList.getLastIndex()+1);
				FileList.createAudioFile();
				FileList.playFile();
			}
		}
		if(e.getActionCommand().equals("Prev")){
			if((FileList.getLastIndex()>0)){
				FileList.setLastIndex(FileList.getLastIndex()-1);
				FileList.createAudioFile();
				FileList.playFile();
			}
		}
	}
}
