package com.player.graphics;

import javax.swing.JScrollPane;

import com.player.program.FileList;

public class MainWindowLeft extends JScrollPane {
	public static final long serialVersionUID = 1L;
	private static FileList fileList=new FileList();

	public MainWindowLeft() {
		super(fileList.getList());

	}

	public static FileList getFileList() {
		return fileList;
	}
}
