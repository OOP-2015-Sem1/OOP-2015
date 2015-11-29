import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private static final int HUMAN = 1;
	private static final int PC = 2;

	// Constructor
	public void SoundIt(int Winner) {
		try {
			// Open an audio input stream.
			if (Winner == HUMAN) {
				File soundFile = new File("C:/Users/Andi/workspace/ProjectT/src/Sounds/t1.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} else if (Winner == PC) {
				File soundFile = new File("C:/Users/Andi/workspace/ProjectT/src/Sounds/ouch.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} else if (Winner == 3) {
				File soundFile = new File("C:/Users/Andi/workspace/ProjectT/src/Sounds/t2.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			}

			// Get a sound clip resource.

			// Open audio clip and load samples from the audio input stream.

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
