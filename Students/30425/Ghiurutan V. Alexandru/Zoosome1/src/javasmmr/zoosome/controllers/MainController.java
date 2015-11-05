package javasmmr.zoosome.controllers;

import javasmmr.zoosome.services.factories.Constants;
import javasmmr.zoosome.services.factories.SpeciesFactory;
import javasmmr.zoosome.services.factories.AnimalFactory;
import javasmmr.zoosome.models.animals.Animal;
import java.util.Scanner;
import javasmmr.zoosome.repositories.AnimalRepository;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import javasmmr.zoosome.models.employees.*;
import javasmmr.zoosome.repositories.EmployeeRepository;

public class MainController {
	// Twist 1
	static Scanner in;// Used to get the number of random animals we want to
						// create.

	private void readInput() throws Exception {
		in = new Scanner(System.in);
		int nr = 0;
		System.out.println("Give the number of random elements that you want to create.");
		if (in.hasNextInt()) {// Verifies if we have a correct input.
			nr = in.nextInt();
		} else {// If we don't have a correct input we will print an error.
			System.err.println("Incorrect input.");
		}
		createRandomAnimals(nr);
	}

	private void takeCare(int numberOfAnimals, int numberOfCaretakers) throws Exception {
		/*
		 * If i would use an ArrayList I could use an iterator to loop through
		 * all the elements in the list,as with the enhanced for loop.
		 */
		ArrayList<Animal> animal = createRandomAnimals(numberOfAnimals);
		Caretaker[] caretaker = new Caretaker[numberOfCaretakers];
		for (int i = 0; i < numberOfCaretakers; i++) {
			caretaker[i] = new Caretaker();
		}
		String result = "";
		for (Caretaker c : caretaker) {
			for (Animal a : animal) {
				if (!c.isDead() && !a.isTakenCareOf()) {
					result = c.takeCareOf(a);
					if (c.equals(Constants.Employees.Caretakers.TCO_KILLED)) {
						c.setIsDead(true);
					} else if (result.equals(Constants.Employees.Caretakers.TCO_NO_TIME)) {
						break;
					} else {
						a.setTakenCareOf(true);
					}
				}
			}
		}
		int nr = 0;
		for (Animal a : animal) {
			if (a.isTakenCareOf()) {
				nr++;
			}
		}
		double procent = ((double) nr / numberOfAnimals) * 100.0;
		System.out.printf("%s.\n", (procent == 100.0) ? "All animals are taken care of"
				: "There are unkempt animals.The procent of animals that are taken care is:" + procent + "%");

	}

	private void readTakenCare() throws Exception {
		System.out.println("Enter the number of animals and caretakers:");
		int nrOfAnimals = in.nextInt(), nrOfCaretakers = in.nextInt();
		takeCare(nrOfAnimals, nrOfCaretakers);
	}

	// This method will create a number of animals from random species.
	private ArrayList<Animal> createRandomAnimals(int number) throws Exception {
		ArrayList<Animal> animal = new ArrayList<Animal>();
		AnimalFactory abstractFactory = new AnimalFactory();
		// Get the specific factory.
		SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Random);
		Animal a1;
		for (int i = 0; i < number; i++) {
			a1 = speciesFactory1.getAnimal(Constants.Animals.Random.RandomAnimal);
			animal.add(a1);
			System.out.printf("%s has %d legs.\n", a1.getName(), a1.getNrOfLegs());
		}
		return animal;
	}

	// xml version
	private void createXmlFromArrayListOfAnimals(ArrayList<Animal> animals) {
		AnimalRepository animalRepository = new AnimalRepository();
		try {
			animalRepository.save(animals);
		} catch (XMLStreamException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
		}
	}

	private ArrayList<Animal> createArrayListOfAnimalsFromXml() {
		AnimalRepository animalRepository = new AnimalRepository();
		try {
			return animalRepository.load();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {

		}
		return null;
	}

	private void xmlReadNumberOfRandomAnimals() throws Exception {
		System.out.println("Give the number of random animals that you want to place in your xml file.");
		int nr = 0;
		if (in.hasNextInt()) {
			nr = in.nextInt();
		} else {
			System.err.println("Incorrect input.");
		}
		createXmlFromArrayListOfAnimals(createRandomAnimals(nr));
	}

	private void printXmlAnimalsContent() {
		ArrayList<Animal> animals = createArrayListOfAnimalsFromXml();
		for (Animal a : animals) {
			System.out.println(a.getName());
		}
	}

	private ArrayList<Employee> createArrayListOfEmployeesFromXml() {
		EmployeeRepository employeeRepository = new EmployeeRepository();
		try {
			return employeeRepository.load();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {

		}
		return null;
	}

	private void createXmlFromArrayListOfEmployees(ArrayList<Employee> employees) {
		EmployeeRepository employeeRepository = new EmployeeRepository();
		try {
			employeeRepository.save(employees);
		} catch (XMLStreamException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
		}
	}

	private void printXmlEmployeesContent() {
		ArrayList<Employee> employees = createArrayListOfEmployeesFromXml();
		for (Employee e : employees) {
			System.out.println(e.getClass().getName() + ":" + e.getName() + ".");
		}
	}

	/*
	 * Nest two methods are auxiliary.
	 */
	private int readInteger() {
		int nr = 0;
		if (in.hasNextInt()) {
			nr = in.nextInt();
		} else {
			System.err.println("You didn't entered an integer number.");
		}
		return nr;
	}

	private void readNumberOfEmployees() {
		System.out.println("Give the number of caretakers,investors and managers ,in order.");
		ArrayList<Employee> employees = new ArrayList<Employee>();
		int nrOfCaretakers = readInteger(), nrOfInvestors = readInteger(), nrOfManagers = readInteger();
		while (nrOfCaretakers > 0) {
			Employee c = new Caretaker();
			employees.add(c);
			nrOfCaretakers--;
		}
		while (nrOfInvestors > 0) {
			Employee i = new Investor();
			employees.add(i);
			nrOfInvestors--;
		}
		while (nrOfManagers > 0) {
			Employee m = new Manager();
			employees.add(m);
			nrOfManagers--;
		}
		this.createXmlFromArrayListOfEmployees(employees);
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 *             from the method called in this class.
	 */
	public static void main(String[] args) throws Exception {
		AnimalFactory abstractFactory = new AnimalFactory();
		/*
		 * speciesFactory1 becomes a reference to a MammalFactory object.
		 */
		SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
		// a1 becomes a reference to a Tiger object.
		Animal a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Tiger);
		System.out.printf("We have an %s animal with %d legs.\n", a1.getName(), a1.getNrOfLegs());
		// Creates a controller reference to the MainController object.
		// This will allow us to call non-static methods through the controller
		// reference.
		MainController controller = new MainController();
		controller.readInput();
		controller.readTakenCare();
		controller.xmlReadNumberOfRandomAnimals();
		controller.printXmlAnimalsContent();
		controller.readNumberOfEmployees();
		controller.printXmlEmployeesContent();
	}
}
