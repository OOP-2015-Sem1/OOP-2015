package javasmmr.zoowsome.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.constants.Constants;
import javasmmr.zoowsome.models.employees.CareTaker;
import javasmmr.zoowsome.models.employees.Employee;
import javasmmr.zoowsome.repositories.AnimalRepository;
import javasmmr.zoowsome.repositories.EmployeeRepository;
import javasmmr.zoowsome.services.factories.animals.AnimalFactory;
import javasmmr.zoowsome.services.factories.animals.SpeciesFactory;
import javasmmr.zoowsome.services.factories.employees.EmployeeFactory;
import javasmmr.zoowsome.services.factories.employees.Factory;


public class MainController {

	
	public static void workClassicWay() throws Exception {
		AnimalFactory abstractFactory = new AnimalFactory();
		EmployeeFactory factory = new EmployeeFactory();
		Factory careFactory = factory.getFactory(Constants.Employees.CareTakers);
		SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
		SpeciesFactory speciesFactory2 = abstractFactory.getSpeciesFactory(Constants.Species.Aquatics);
		SpeciesFactory speciesFactory3 = abstractFactory.getSpeciesFactory(Constants.Species.Reptiles);
		SpeciesFactory speciesFactory4 = abstractFactory.getSpeciesFactory(Constants.Species.Birds);
		SpeciesFactory speciesFactory5 = abstractFactory.getSpeciesFactory(Constants.Species.Insects);

		Animal[] a = new Animal[5];

		Animal a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Monkey);
		Animal a2 = speciesFactory2.getAnimal(Constants.Animals.Aquatics.Piranha);
		Animal a3 = speciesFactory3.getAnimal(Constants.Animals.Reptiles.Alligator);
		Animal a4 = speciesFactory4.getAnimal(Constants.Animals.Birds.Parrot);
		Animal a5 = speciesFactory5.getAnimal(Constants.Animals.Insects.Butterfly);

		a[0] = a1;
		a[1] = a2;
		a[2] = a3;
		a[3] = a4;
		a[4] = a5;

		for (Animal i : a) {
			System.out.printf("%s, %d legs, %.2f TCO time, %.2f dangerous! is taken care: %b\n", i.getName(), i.getNrOfLegs(),
					i.getMaintenanceCost(), i.getDamagePerc() * 100, i.isTakenCareOf());
		}

		CareTaker[] c = new CareTaker[2];
		
		for (int i = 0; i < c.length; i++) {
			c[i] = careFactory.getCareTaker("Caretaker");
			c[i].setName("Andrei");
		}
		/*
		
		for (CareTaker e : c) {
			System.out.println(e.getName() + " sallary: " + e.getSalary() + " id: " + e.getId() + " is dead : "
					+ e.isDead() + " wh " + e.getWorkingHours());
		}
	*/

		for (CareTaker em : c) {
			for (Animal an : a) {
				if (!em.isDead() && !an.isTakenCareOf()) {
					String result = em.takeCareOf(an);
					if (result.equals(Constants.Employees.Caretakers.TCO_KILLED)) {
						em.setDead(true);
					} else if (result.equals(Constants.Employees.Caretakers.TCO_NO_TIME)) {
						continue;
					} else {
						an.setTakenCareOf(true);
					}
				}
			}
		}
		
		for (CareTaker e : c) {
			System.out.println(e.getName() + " sallary: " + e.getSalary() + " id: " + e.getId() + " is dead : "
					+ e.isDead() + " wh " + e.getWorkingHours());
		}
		
		for (Animal i : a) {
			System.out.printf("%s, %d legs, %.2f TCO time, %.2f dangerous! is taken care: %b\n", i.getName(), i.getNrOfLegs(),
					i.getMaintenanceCost(), i.getDamagePerc() * 100, i.isTakenCareOf());
		}
		
		/*
		
		ArrayList <String> names = new ArrayList <String>(); // test if I can delete elements while iterating
		names.add("Andrei");
		names.add("Stefan");
		names.add("Pisti");
		names.add("Iancsi");
		
		for(int i = 0; i < names.size(); i++) {
			System.out.println(names.get(i));
			if(i==1) {
				names.remove(2);
				names.remove(2);
			}
		} // yes, the elements can be removed even when we iterate trough the array without an iterator
		
		Iterator <String> i = names.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
		
		*/
		
		/*
		 * SpeciesFactory[] Factories = {speciesFactory1, speciesFactory2,
		 * speciesFactory3, speciesFactory4, speciesFactory5}; String[] category
		 * = {"Mammal", "Aquatic", "Reptile", "Bird", "Insect"}; Animal [] anim
		 * = new Animal[51]; String [][] species = {{"Monkey", "Cow", "Tiger"},
		 * {"Whale", "Piranha", "Shark"}, {"Alligator", "Lizard", "Boa"},
		 * {"Swallow", "Parrot", "Penguin"}, {"Butterfly", "Cockroach",
		 * "Spider"}};
		 * 
		 * 
		 * int factNo, specNo;
		 * 
		 * for(int i = 0; i < 50; i++) { factNo = (int) (Math.random() * 5);
		 * specNo = (int) (Math.random() * 3); anim[i] =
		 * Factories[factNo].getAnimal(species[factNo][specNo]);
		 * System.out.printf("Animal number: %d is a %s, Name: %s has %d legs\n"
		 * , i, category[factNo], anim[i].getName(), anim[i].getNrOfLegs()); }
		 */
	}
	
	
	public static void workWithXml() throws ParserConfigurationException, SAXException, IOException, Exception {
		
		ArrayList <Animal> list = new ArrayList <> ();
		
		AnimalRepository playA = new AnimalRepository();
		
		list = playA.load();
		
		for(Animal i : list) {
			System.out.println(i.getName());
		}
		
		try {
			playA.save(list);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EmployeeFactory factory = new EmployeeFactory();
		Factory careFactory = factory.getFactory(Constants.Employees.CareTakers);
		
		ArrayList<Employee> c = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) {
			c.add(careFactory.getCareTaker("Caretaker"));
		}
		
		EmployeeRepository playE = new EmployeeRepository();
			playE.save(c);
		
		
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		//workClassicWay();
		
		workWithXml();
		
		

	}
}
