package javasmmr.zoowsome.controllers;

import javasmmr.zoowsome.models.animals.*;
import javasmmr.zoowsome.models.employees.*;
import javasmmr.zoowsome.services.factories.Constants;
import javasmmr.zoowsome.services.factories.animals.*;
import javasmmr.zoowsome.services.factories.employees.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainController 
{
	public static void main(String[] args) throws Exception 
	{
		AnimalFactory animalfactory = new AnimalFactory();
		SpeciesFactory speciesFactory = animalfactory.getSpeciesFactory(Constants.Species.RandomAnimal);
		CaretakerFactory caretakerFactory = new CaretakerFactory();

		ArrayList<Animal> animalsList = new ArrayList<Animal>();
		ArrayList<Caretaker> employeesList = new ArrayList<Caretaker>();


		for(int i = 0; i < 50; i++)
		{
			animalsList.add(speciesFactory.getAnimal(Constants.Species.RandomAnimal));
			
		}

		for(int i = 0; i < 20; i++)
		{
			employeesList.add(caretakerFactory.getEmployee(Constants.Employee.Caretaker));
		}
		Caretaker careTaker ;
		Animal animal;
		skipEmployee:
			for(int i = 0; i < 20; i++)
			{
				careTaker = employeesList.get(i);
				for(int j = 0; j < 50; j++)
				{
					animal = animalsList.get(j);
					if(animal.getPredisposition()>0)
					{
						String animalName = animal.getClass().toString();
						animalName = animalName.substring(animalName.lastIndexOf(".")+1);
						//System.out.println(animalName);
						System.out.println("Animal type "+animalName+" got predisposed "+animal.getPredisposition());
						
					}
					if(careTaker.getIsDead() == false && animal.getTakenCareOf() == false)
					{
						String jobResult = careTaker.takeCareOf(animal);
						if(jobResult.equals(Constants.Employee.Caretakers.TCO_KILLED))
						{
							System.out.println(careTaker.getName()+" is dead");
							careTaker.setIsDead(true);
						}
						else if (jobResult.equals(Constants.Employee.Caretakers.TCO_NO_TIME))
						{
							continue skipEmployee;
						}
						else
						{
							animal.setTakenCareOf(true);
						}
					}
				}
			}
		boolean flag = true;
		for(int j = 0; j < 50; j++)
		{
			animal = animalsList.get(j);
			if(animal.getTakenCareOf()==false)
			{
				String animalName = animal.getClass().toString();
				animalName = animalName.substring(animalName.lastIndexOf(".")+1);
				//System.out.println(animalName);
				System.out.println("Animal type "+animalName+" isn't taken care of");
				flag=false;
			}
		}
		
		if(flag == true)
		{
			System.out.println("All animals have a caretaker");
		}
		
		

	}
}
