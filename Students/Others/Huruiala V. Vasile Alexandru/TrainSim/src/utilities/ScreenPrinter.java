package utilities;

import java.util.Collection;

public class ScreenPrinter {
	public static void printStrings(Collection<String> strings) {
		for (String s : strings) {
			System.out.println(s);
		}
	}
}
