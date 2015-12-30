package Logic;

import java.util.Random;

public class RandomLogic {
	
	private Random rand = new Random();
	
	public int getRandom(int max) {
		    // nextInt is normally exclusive of the top value,
		    // so add 1 to make it inclusive
		 int randomNum = rand.nextInt(max);
		 return randomNum;
	}
}
