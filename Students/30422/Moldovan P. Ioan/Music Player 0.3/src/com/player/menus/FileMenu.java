package com.player.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import com.player.graphics.MainWindow;
import com.player.graphics.MainWindowLeft;
import com.player.graphics.UpperMenuBar;
import com.player.program.FileList;

public class FileMenu extends JMenu implements ActionListener {
	public static final long serialVersionUID = 1L;
	private JMenuItem menuItem;

	public FileMenu() {
		this.setText("File");
		UpperMenuBar.addMenuItem(this, this, menuItem, "Open File");
		UpperMenuBar.addMenuItem(this, this, menuItem, "Save");
		this.addSeparator();
		UpperMenuBar.addMenuItem(this, this, menuItem, "Close");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Open File")) {
			MainWindowLeft.getFileList().readFiles();
			;
		}
		if (e.getActionCommand().equals("Save")) {

			FileList.setMetadata();

		}
		if (e.getActionCommand().equals("Close")) {
			MainWindow.closeProgram();
		}

	}

}
