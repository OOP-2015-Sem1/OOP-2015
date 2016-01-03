package model;

import java.io.File;

import javax.sound.sampled.AudioSystem;

import javax.sound.sampled.Clip;

public class SoundPlayer {

	public static void playSound(File Sound, boolean startOrStop) {

		try {

			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();

			// Thread.sleep(clip.getMicrosecondLength()/10000);
		} catch (Exception e) {
			System.out.println("Something went wrong with the sound File" + e.getStackTrace());
		}
	}

}
