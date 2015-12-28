package theGame;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Audio {

	public static Map<String, Sound> soundMap = new HashMap<String, Sound>();

	public static void load() {

		try {
			soundMap.put("collision_sound", new Sound("res/Laser_Shoot7.ogg"));
			soundMap.put("scored_sound", new Sound("res/Randomize20.ogg"));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
}
