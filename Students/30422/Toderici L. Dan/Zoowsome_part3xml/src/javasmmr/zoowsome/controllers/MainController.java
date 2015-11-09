package javasmmr.zoowsome.controllers;

import javasmmr.zoowsome.models.animals.*;
import javasmmr.zoowsome.models.employees.*;
import javasmmr.zoowsome.repositories.AnimalRepository;
import javasmmr.zoowsome.repositories.EmployeeRepository;
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
		ArrayList<Employee> employeesList = new ArrayList<Employee>();

		AnimalRepository animalRepository = new AnimalRepository();
		EmployeeRepository employeeRepository = new EmployeeRepository() ;
		animalsList = animalRepository.load();
		employeesList = employeeRepository.load();
		if(animalsList.isEmpty() || employeesList.isEmpty())
		{
			for(int i = 0; i < 50; i++)
			{
				animalsList.add(speciesFactory.getAnimal("RandomAnimalIntiliazed"));

			}

			for(int i = 0; i < 100; i++)
			{
				employeesList.add(caretakerFactory.getEmployee(Constants.Employee.Caretaker));
			}
			Caretaker careTaker ;
			Animal animal;
			skipEmployee:
				for(int i = 0; i < 100; i++)
				{
					careTaker = (Caretaker) employeesList.get(i);
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
						if((!careTaker.getIsDead()) && (!animal.getTakenCareOf()))
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
				if(!animal.getTakenCareOf())
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

			animalRepository.save(animalsList);
			employeeRepository.save(employeesList);
		}
		System.out.println(animalRepository.load());
		System.out.println();
		System.out.println(employeeRepository.load());


	}
}
