package com.player.program;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileList {
	public static final long serialVersionUID = 1L;
	private static DefaultListModel<File> listModel;
	private static ArrayList<File> fileVector;
	private static JList<File> list;
	private static AudioFile audioFile;
	private static int lastIndex = 0;

	public FileList() {
		super();
		listModel = new DefaultListModel<File>();
		list = new JList<File>();
		list.setModel(listModel);
		list.addMouseListener(mouseListener);
		fileVector = new ArrayList<File>();
	}
	
	public static void createAudioFile(){
		audioFile = new AudioFile(getFileVector().get(lastIndex));
	}

	private MouseListener mouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				lastIndex = getSelectedFilesIndex();
				createAudioFile();
				getMetadata();

			}
		}
	};

	public static void renameFiles(String option) {
		for (int i = 0; i < fileVector.size(); i++) {
			audioFile = new AudioFile(fileVector.get(i));
			audioFile.renameFile(option);
		}
	}
	
	public static int getLastIndex(){
		return lastIndex;
	}
	
	public static void setLastIndex(int index) {
		lastIndex = index;
	}

	public static void playFile() {

		audioFile.startPlaying();
	}

	public static void pauseFile() {
		audioFile.pausePlaying();
	}

	public static void stopFile() {
		audioFile.stopPlaying();
	}

	public static void getMetadata() {
		audioFile.getMetadata();
	}

	public static void setMetadata() {
		audioFile.setMetadata();
	}

	public JList<File> getList() {
		return list;
	}

	public static ArrayList<File> getFileVector() {
		return fileVector;
	}

	public static void updateListModel() {
		listModel = new DefaultListModel<File>();

		for (int j = 0; j < fileVector.size(); j++) {
			listModel.add(j, fileVector.get(j));
		}
	}

	public void addFile(File selectedFile) {
		if (fileVector.indexOf(selectedFile) < 0) {
			fileVector.add(selectedFile);
		}
	}

	public void readFiles() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.MP3", "mp3");
		fileChooser.addChoosableFileFilter(filter);
		filter = new FileNameExtensionFilter("*.WAV", "wav");
		fileChooser.addChoosableFileFilter(filter);
		int result = fileChooser.showOpenDialog(new JFrame("Choose File"));
		if (JFileChooser.APPROVE_OPTION == result) {
			File[] selectedFiles = fileChooser.getSelectedFiles();
			for (int i = 0; i < selectedFiles.length; i++) {
				addFile(selectedFiles[i]);
			}
		}
		updateListModel();
		list.setModel(listModel);
	}

	public static int getSelectedFilesIndex() {
		return list.getSelectedIndex();
	}

}
