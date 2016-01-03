package others;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Computer {

	public int chooseChr() {

		Random randomGenerator = new Random();
		return randomGenerator.nextInt(24);
	}

	public int askQuestions() {

		List<Integer> askedQuestions = new ArrayList<Integer>();
		Random randomGenerator = new Random();
		int randomQuestion = randomGenerator.nextInt(17) + 1;
		Iterator<Integer> it = askedQuestions.iterator();

		while (it.hasNext()) {
			if (it.next() == randomQuestion)
				return askQuestions();
			else
				askedQuestions.add(randomQuestion);
		}
		return randomQuestion;
	}
}
