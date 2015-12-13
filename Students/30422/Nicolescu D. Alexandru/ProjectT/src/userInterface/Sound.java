package userInterface;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private static final int HUMAN_WON = 1;
	private static final int PC_WON = 2;
	private static final int PC_LOST=3;
	private String dirName="C:/Users/Andi/workspace/ProjectT/Sounds";

	public void SoundIt(int choice) {
		try {
			if (choice == HUMAN_WON) {
				File soundFile = new File(dirName+"/t1.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} else if (choice == PC_WON) {
				File soundFile = new File(dirName+"/ouch.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} else if (choice == PC_LOST) {
				File soundFile = new File(dirName+"/t2.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			}

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
