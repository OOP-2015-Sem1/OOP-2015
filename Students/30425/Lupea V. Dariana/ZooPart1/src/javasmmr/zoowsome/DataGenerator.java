package javasmmr.zoowsome;

import java.util.Random;
import java.math.*;

public class DataGenerator {

	public String nameGenerator() {
		String[] names = { "Anna", "John", "Cristian", "Kevin", "Joey", "Summer", "Carol", "Susan", "Michael",
				"Oliver", "Tudor", "Jamie", "Edith", "Aron", "Bailey", "Tom", "Eathen", "Hanna", "Cristof",
				"Bernard", "Arthur", "Connor", "Tommy", "Harry", "Irene", "Melvin", "Norma", "Jake", "Paul", "Patricia",
				"Willie" };
		Random randomGen = new Random();
		return names[randomGen.nextInt(30)];
	}

	public Long idGen() {
		long minimum = 1_000_000_000_000L;
		Random randomGen = new Random();
		long randomNum = minimum + Math.abs(randomGen.nextInt(999_999_999));
		return randomNum;
	}

	public BigDecimal salaryGen() {
		BigDecimal salary = new BigDecimal(1500);
		return salary;
	}

	public double workingHoursGen() {
		final double hours = 40;
		return hours;
	}
}
