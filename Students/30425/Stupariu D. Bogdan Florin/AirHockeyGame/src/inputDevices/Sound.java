package inputDevices;
import java.applet.Applet;
import java.applet.AudioClip;

public class Sound 
{
	AudioClip clip;
	public Sound(String sound)
	{
		clip = Applet.newAudioClip(getClass().getResource(sound));
	}
	
	public void PlaySound()
	{
		clip.play();
	}
}