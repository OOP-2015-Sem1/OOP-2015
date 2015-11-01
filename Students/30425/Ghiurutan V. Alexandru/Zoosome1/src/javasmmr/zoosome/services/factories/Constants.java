package javasmmr.zoosome.services.factories;

//Holder class for strings.
//There are inner classes declared as final,which means we can't extend them anymore.
public final class Constants {
	/*
	 * Species class contains multiple String constants that describe each
	 * species.
	 */
	public static final class Species {
		public static final String Mammals = "Mammals";
		public static final String Reptiles = "Reptiles";
		public static final String Birds = "Birds";
		public static final String Aquatics = "Aquatics";
		public static final String Insects = "Insects";
		public static final String Random = "Random";
	}

	// The Animals class contains multiple inner classes ,one for each species.
	public static final class Animals {
		// Each class contains String constants ,which represent the specific
		// animals in each species class.
		public static final class Mammals {
			public static final String Cow = "COW";
			public static final String Tiger = "TIGER";
			public static final String Monkey = "MONKEY";
		}

		public static final class Reptiles {
			public static final String Turtle = "TURTLE";
			public static final String Snake = "SNAKE";
			public static final String Crocodile = "CROCODILE";
		}

		public static final class Birds {
			public static final String Vulture = "VULTURE";
			public static final String Sparrow = "SPARROW";
			public static final String Penguin = "PENGUIN";
		}

		public static final class Aquatics {
			public static final String Frog = "FROG";
			public static final String Dolphin = "DOLPHIN";
			public static final String Seal = "SEAL";
		}

		public static final class Insects {
			public static final String Cockroach = "COCKROACH";
			public static final String Spider = "SPIDER";
			public static final String Butterfly = "BUTTERFLY";
		}

		public static final class Random {// Used to generate random animals.
			public static final String RandomAnimal = "RANDOM";
		}
	}

	// This class is related to the Employee package
	public static final class Employees {
		// Class used for the Caretaker_I interface
		public static final class Caretakers {
			public static final String TCO_SUCCESS = "SUCCESS";
			public static final String TCO_KILLED = "KILLED";
			public static final String TCO_NO_TIME = "NO_TIME";
		}

		// For the EmployeeFactory and EmployeeAbstractFactory
		public static final String Caretaker = "CARETAKER";
		public static final String Ivestor = "INVESTOR";
		public static final String Manager = "MANAGER";
	}
}
