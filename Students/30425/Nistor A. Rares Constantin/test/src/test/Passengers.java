package test;

import java.util.*;

public class Passengers implements Carriable {
	final String name = UUID.randomUUID().toString();

	public String getName() {
		return name;
	}

}
