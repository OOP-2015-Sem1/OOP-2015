package javasmmr.zoowsome.controllers;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import javasmmr.zoowsome.models.animals.Alligator;
import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.ButterFly;
import javasmmr.zoowsome.models.animals.Cockroach;
import javasmmr.zoowsome.models.animals.Frog;
import javasmmr.zoowsome.models.animals.Stork;
import javasmmr.zoowsome.models.animals.Tiger;
import javasmmr.zoowsome.models.employees.Caretaker;
import javasmmr.zoowsome.models.employees.Employee;
//import javasmmr.zoowsome.models.animals.Animal;
//import javasmmr.zoowsome.models.animals.ButterFly;
//import javasmmr.zoowsome.models.employees.Caretaker;
import javasmmr.zoowsome.repositories.AnimalRepository;
import javasmmr.zoowsome.repositories.EmployeeRepository;
import javasmmr.zoowsome.services.factoryForAnimals.AnimalFactory;
import javasmmr.zoowsome.services.factoryForAnimals.Constants;
import javasmmr.zoowsome.services.factoryForAnimals.SpeciesFactory;

public class MainController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AnimalFactory abstractFactory = new AnimalFactory();
		Animal a1 = null;
		SpeciesFactory speciesFactory1 = null;

		HelpMain help = new HelpMain(abstractFactory, a1, speciesFactory1);

		/**
		 * Building an Array with randomized animals;
		 */
		final int MAX = 15;Animal animalArray[] = new Animal[MAX];
		
		for (int i = 0; i < MAX; i++) {
			int randomSpecies = (int) (1 + Math.random() * 4); //
			int randomSubType = (int) (1 + Math.random() * 2); //
			
			animalArray[i] = help.getAnAnimal(randomSpecies, randomSubType);
			System.out.println("newbie animal in our nest: " + animalArray[i].getName());
		}
		/**
		 *  Building an array for Caretakers
		 */
		Caretaker caretakerArray[] = new Caretaker[MAX];
		for (int i = 0; i < MAX; i++) {
			caretakerArray[i] = new Caretaker();
			caretakerArray[i].setWorkingHours(5);
			caretakerArray[i].setName("AnimalLover-" + i);
		}

		/**
		 * Caretakers would like to take care of animals
		 * Unfortunately, this is a very dangerous job
		 * Often it happens that the animal is stronger than 
		 * its Caretaker... 
		 */
		for (Caretaker c1 : caretakerArray) {
			L: for (Animal a : animalArray) {
				// System.out.println("the animal is: "+a.getName());
				if (c1.getIsAlive() && !a.isTakenCareOf()) {
					String result = c1.takeCareof(a);

					if (Constants.Employees.Caretakers.TCO_KILLED.equals(result)) {
						c1.setIsAlive(false);
						System.out.println(a.getName() + " killed " + c1.getName());
						break L;
					}

					else if (Constants.Employees.Caretakers.TCO_NO_TIME.equals(result)) {
						// continuing to search for an animal
						System.out.println(c1.getName() + " has no time to take care of " + a.getName());
						break L;
					}

					else if (Constants.Employees.Caretakers.TCO_SUCCESS.equals(result)) {
						a.setTakenCareOf(true);
						System.out.println("Animal " + a.getName() + " is taken care of by caretaker: " + c1.getName());
					}
				}

			}
			if (c1.getIsAlive())
				System.out.println("Caretaker: " + c1.getName() + " still have free workingHours: "
			    + c1.getWorkingHours());
			else
				System.out.println("RIP Caretaker: " + c1.getName() + " still HAD workingHours: " 
			   + c1.getWorkingHours());
		}
		
		/**
		 * Testing Zoowsome part 3;
		 * 
		 * */
		
		/**
		 * Saving in xml file
		 * */
		AnimalRepository  animalRep = new AnimalRepository();
		ArrayList<Animal> animalList= new ArrayList<Animal>();
		
		animalList.add(new ButterFly(1.0,2.0));
		animalList.add(new Stork(3.0,4.0));
		animalList.add(new Alligator(5.0,6.0));
		animalList.add(new Cockroach(5.0,6.0));
		animalList.add(new Tiger(1.0,1.0));
		animalList.add(new Frog(1.0,1.0));
		
		try {
			animalRep.save(animalList);
			} catch (FileNotFoundException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * From xml file to console
		 * */
		
		ArrayList<Animal> animalList1= new ArrayList();
		
		try {
			animalList1=animalRep.load();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		System.out.println("/* * * * * * * /");
		for(Animal a:animalList1){
			System.out.println(a.getName()+": "+a.getNrOfLegs()+" "+ a.getMaintenanceCost());
		}
		
		/**
		 * Testing the same for employees
		 * Saving to xml file
		 */
		EmployeeRepository employeeRep = new EmployeeRepository();
		ArrayList<Employee> employeeList= new ArrayList<Employee>();
		
		Caretaker c1= new Caretaker();
		Caretaker c2= new Caretaker();
		c1.setName("Care 1");
		c1.setID(1234l);
		c2.setName("Care 2");
		c2.setID(5678l);
		c1.setWorkingHours(1);
		c2.setWorkingHours(2);
		employeeList.add(c1);
		employeeList.add(c2);
		
		
		try {
			employeeRep.save(employeeList);
			} catch (FileNotFoundException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * Loading from xml file to the console
		 * */
		ArrayList employeeList1= new ArrayList();
		
		try {
			employeeList1=employeeRep.load();
		} catch (ParserConfigurationException | SAXException | IOException  | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("/* * * * * * * /");
	    System.out.println(employeeList1);
	    
	   }
	 
	
}
