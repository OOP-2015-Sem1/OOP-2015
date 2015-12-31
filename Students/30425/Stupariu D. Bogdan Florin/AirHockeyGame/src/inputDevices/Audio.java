package inputDevices;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
	public Audio() {
		File Collision_sound = new File ("res/Laser_Shoot7");
	}
	
	static void PlaySound (File Sound){
		try{
			Clip clip =  AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			
			Thread.sleep(clip.getMicrosecondLength()/1000);
		}catch (Exception e){
			
		}
	}
	
	
}
