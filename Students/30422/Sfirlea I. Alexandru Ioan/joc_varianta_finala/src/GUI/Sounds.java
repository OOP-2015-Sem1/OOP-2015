package GUI;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Main.Constants;

public class Sounds {
	
	public void SoundIt() {
		try {
			File soundFile = new File("src\\1010Sound.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			
			if(Constants.isMusicOn == true && Constants.wantMusic== true){
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