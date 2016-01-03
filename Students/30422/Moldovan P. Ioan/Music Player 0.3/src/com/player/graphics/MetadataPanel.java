package com.player.graphics;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MetadataPanel extends JPanel {
	public static final long serialVersionUID = 1L;
	private static JLabel artistLabel, albumLabel, titleLabel, yearLabel, genreLabel;
	private static JTextField artistField, albumField, titleField, yearField, genreField;

	public MetadataPanel() {
		super();
		this.setLayout(new GridLayout(10, 1, 30, 0));
		artistLabel = new JLabel("Artist");
		artistField = new JTextField();
		artistLabel.setFont(new Font("Monotype", Font.ITALIC, 16));
		this.add(artistLabel);
		this.add(artistField);
		albumLabel = new JLabel("Album");
		albumField = new JTextField();
		albumLabel.setFont(new Font("Monotype", Font.ITALIC, 16));
		this.add(albumLabel);
		this.add(albumField);
		titleLabel = new JLabel("Title");
		titleField = new JTextField();
		this.add(titleLabel);
		this.add(titleField);
		yearLabel = new JLabel("Year");
		yearField = new JTextField();
		yearLabel.setFont(new Font("Monotype", Font.ITALIC, 16));
		this.add(yearLabel);
		this.add(yearField);
		genreLabel = new JLabel("Genre");
		genreField = new JTextField();
		genreLabel.setFont(new Font("Monotype", Font.ITALIC, 16));
		this.add(genreLabel);
		this.add(genreField);
	}

	public static void setArtist(String text) {
		artistField.setText(text);
	}

	public static void setAlbum(String text) {
		albumField.setText(text);
	}

	public static void setTitle(String text) {
		titleField.setText(text);
	}

	public static void setYear(String text) {
		yearField.setText(text);
	}

	public static void setGenre(String text) {
		genreField.setText(text);
	}

	public static String getTag(String tag) {
		switch (tag.toLowerCase()) {
		case "artist":
			if (artistField.getText() != null) {
				return artistField.getText();
			}
		case "album":
			if (albumField.getText() != null) {
				return albumField.getText();
			}
		case "title":
			if (titleField.getText() != null) {
				return titleField.getText();
			}
		case "year":
			if (yearField.getText() != null) {
				return yearField.getText();
			}
		case "genre":
			if (genreField.getText() != null) {
				return genreField.getText();
			}
		default:
			return "";
		}
	}

}
