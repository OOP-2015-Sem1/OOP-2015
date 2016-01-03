package com.player.program;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v1;
import org.farng.mp3.id3.ID3v2_2;

import com.player.graphics.MetadataPanel;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioFile {
	public static final long serialVersionUID = 1L;
	private File audioFile;
	private static Media media = new Media(Paths.get("res/a.mp3").toUri().toString());
	private static MediaPlayer player = new MediaPlayer(media);
	private static boolean playerIsRunning = false;
	private MP3File mp3File;

	public AudioFile(File selectedFile) {
		audioFile = selectedFile;
		player.dispose();
		media = new Media(Paths.get(audioFile.getPath()).toUri().toString());
		player = new MediaPlayer(media);
		try {
			mp3File = new MP3File(selectedFile);
		} catch (IOException | TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getMetadata() {
		
		if (mp3File.hasID3v2Tag()) {
			AbstractID3v2 id3v2Tag = mp3File.getID3v2Tag();
		/*	MetadataPanel.setTag("album", id3v2Tag.getAlbumTitle());
			MetadataPanel.setTag("artist", id3v2Tag.getLeadArtist());
			MetadataPanel.setTag("genre", id3v2Tag.getSongGenre());
			MetadataPanel.setTag("title", id3v2Tag.getSongTitle());
			MetadataPanel.setTag("year", id3v2Tag.getYearReleased()); */
			
			MetadataPanel.setAlbum(id3v2Tag.getAlbumTitle());
			MetadataPanel.setArtist(id3v2Tag.getLeadArtist());
			MetadataPanel.setTitle(id3v2Tag.getSongTitle());
			MetadataPanel.setYear(id3v2Tag.getYearReleased());
			MetadataPanel.setGenre(id3v2Tag.getSongGenre());

		} else if (mp3File.hasID3v1Tag()) {
			ID3v1 id3v1Tag = mp3File.getID3v1Tag();
			MetadataPanel.setAlbum(id3v1Tag.getAlbumTitle());
			MetadataPanel.setArtist(id3v1Tag.getLeadArtist());
			MetadataPanel.setTitle(id3v1Tag.getSongTitle());
			MetadataPanel.setYear(id3v1Tag.getYearReleased());
			MetadataPanel.setGenre(id3v1Tag.getSongGenre());		}
	}

	public void setMetadata() {
		AbstractID3v2 id3v2Tag;
		if (mp3File.hasID3v2Tag()) {
			id3v2Tag = mp3File.getID3v2Tag();
			System.out.println("has id3v2");
		} else {
			id3v2Tag = new ID3v2_2();
		}

		mp3File.setID3v2Tag(id3v2Tag);
		System.out.println("Setting data");
		id3v2Tag.setAlbumTitle(MetadataPanel.getTag("album"));
		id3v2Tag.setLeadArtist(MetadataPanel.getTag("artist"));
		id3v2Tag.setSongTitle(MetadataPanel.getTag("title"));
		id3v2Tag.setYearReleased(MetadataPanel.getTag("year"));
		id3v2Tag.setSongGenre(MetadataPanel.getTag("genre"));
		try {
			mp3File.save();
		} catch (IOException | TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void renameFile(String option) {
		
		Path path = audioFile.toPath();
		String newPath;
		if (mp3File.hasID3v2Tag()) {
			AbstractID3v2 id3v2Tag = mp3File.getID3v2Tag();
			switch (option) {
			case "1: Artist - Title":
				newPath = id3v2Tag.getLeadArtist() + " - " + id3v2Tag.getSongTitle();
			case "2: Title":
				newPath = id3v2Tag.getSongTitle();
			case "3: Artist - Title - Album":
				newPath = id3v2Tag.getLeadArtist() + " - " + id3v2Tag.getSongTitle() + " - " + id3v2Tag.getAlbumTitle();
			case "4: Title - Album":
				newPath = id3v2Tag.getSongTitle() + " - " + id3v2Tag.getAlbumTitle();
			default:
				newPath = id3v2Tag.getLeadArtist() + " - " + id3v2Tag.getSongTitle();
			}
			try {
				char[] extension = new char[4];
				//path.toString().getChars(path.toString().length() - 5, path.toString().length(), extension, 0);
				player.dispose();
				Files.move(path, path.resolveSibling(newPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (mp3File.hasID3v2Tag()) {
			ID3v1 id3v1Tag = mp3File.getID3v1Tag();
			switch (option) {
			case "1: Artist - Title":
				newPath = id3v1Tag.getLeadArtist() + " - " + id3v1Tag.getSongTitle();
			case "2: Title":
				newPath = id3v1Tag.getSongTitle();
			case "3: Artist - Title - Album":
				newPath = id3v1Tag.getLeadArtist() + " - " + id3v1Tag.getSongTitle() + " - " + id3v1Tag.getAlbumTitle();
			case "4: Title - Album":
				newPath = id3v1Tag.getSongTitle() + " - " + id3v1Tag.getAlbumTitle();
			default:
				newPath = id3v1Tag.getLeadArtist() + " - " + id3v1Tag.getSongTitle();
			}
			try {
				char[] extension = new char[4];
				//path.toString().getChars(path.toString().length() - 5, path.toString().length(), extension, 0);
				player.dispose();
				Files.move(path, path.resolveSibling(newPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void startPlaying() {
		if (playerIsRunning) {
			player.stop();
		}
		player.play();
		playerIsRunning = true;
		player.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				playerIsRunning = false;
			}
		});

	}

	public void stopPlaying() {
		if (playerIsRunning) {
			playerIsRunning = false;
			player.stop();
		}
	}

	public void pausePlaying() {
		if (playerIsRunning) {
			playerIsRunning = false;
			player.pause();
		}
	}

}
