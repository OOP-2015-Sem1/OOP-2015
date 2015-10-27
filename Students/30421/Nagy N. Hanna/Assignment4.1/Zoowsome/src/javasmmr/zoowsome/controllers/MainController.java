package javasmmr.zoowsome.controllers;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.employees.Caretaker;
import javasmmr.zoowsome.services.factoryForAnimals.AnimalFactory;
import javasmmr.zoowsome.services.factoryForAnimals.Constants;
import javasmmr.zoowsome.services.factoryForAnimals.SpeciesFactory;


public class MainController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AnimalFactory abstractFactory = new AnimalFactory();
		SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
		SpeciesFactory speciesFactory2 = abstractFactory.getSpeciesFactory(Constants.Species.Insects);


	//	HelpMain help = new HelpMain();
		
		Caretaker c1[] = new Caretaker[2];
		Animal animals[]= new Animal[2];
		animals[0]= speciesFactory2.getAnimal(Constants.Animals.Insects.Butterfly);
		animals[1]=speciesFactory1.getAnimal(Constants.Animals.Mammals.Cow);;

		c1[0] = new Caretaker();
		c1[1] = new Caretaker();
		c1[0].setName("Rose Kidman");
		c1[1].setName("Leonardo West");
		c1[0].setWorkingHours(10.0);
		c1[1].setWorkingHours(20.0);
		
	

	for(Caretaker c2: c1){
		for(Animal a1: animals){
			
            if (!c2.getIsAlive() && !a1.isTakenCareOf()) {
				String result = c2.takeCareof(a1);
					  
				if (Constants.Employees.Caretakers.TCO_KILLED.equals(result)) {
					  c2.setIsAlive(false);
					  }
			   else if(Constants.Employees.Caretakers.TCO_NO_TIME.equals(result)) { System.out.println("no time");}
						  // skip to the next animal, try to take care of that } else {
			   else  a1.setTakenCareOf(true); 
				
		      if(Constants.Employees.Caretakers.TCO_SUCCESS.equals(result)){
		    	  System.out.println("would be great");       }
		 }
   }
		System.out.println(c2.getName());
		System.out.println(c2.getWorkingHours());
}
	if(animals[0].isTakenCareOf()) System.out.println("goood"); 
	else  System.out.println("not good");
	
	}
}

/*
 * Animal a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Tiger);
 * 
 * if (!c1.getIsAlive() && !a1.isTakenCareOf()) { String result =
 * c1.takeCareof(a1);
 * 
 * 
 * if (Constants.Employees.Caretakers.TCO_KILLED.equals(result)) {
 * c1.setIsAlive(false); } else if
 * (Constants.Employees.Caretakers.TCO_NO_TIME.equals(result)) { // skip to the
 * next animal, try to take care of that } else { a1.setTakenCareOf(true); } }
 * 
 * 
 * if(a1.isTakenCareOf()) System.out.println("goood"); else
 * System.out.println("no good"); }
 */
