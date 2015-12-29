package GameShapes;

import sun.audio.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.*;

public class Sound {

	public static void PlaySound(File Sound) {

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();

			// Thread.sleep(clip.getMicrosecondLength()/1000);
		} catch (Exception error) {

		}
		
	
	}
}
