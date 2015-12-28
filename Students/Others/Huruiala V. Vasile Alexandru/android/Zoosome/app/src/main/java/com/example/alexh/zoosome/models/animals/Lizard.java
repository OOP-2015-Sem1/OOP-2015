package com.example.alexh.zoosome.models.animals;
/*
import static com.example.alexh.zoosome.repositories.EntityRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
*/
import com.example.alexh.zoosome.services.factories.Constants;

import java.util.ArrayList;

public class Lizard extends Reptile {
	private static final String DEFAULT_NAME = "Tautara";
	private static final int DEFAULT_NUMBER_OF_LEGS = 4;
	private static final double DEFAULT_MAINTENANCE_COST = 0.2D;
	private static final double DEFAULT_DANGER_PERCENTAGE = 0.0D;
	private static final boolean DEFAULT_LAYS_EGGS = true;

	public Lizard() {
		super(DEFAULT_NAME, DEFAULT_NUMBER_OF_LEGS, DEFAULT_MAINTENANCE_COST, DEFAULT_DANGER_PERCENTAGE,
				DEFAULT_LAYS_EGGS);
	}

	public Lizard(final String animalName, final int numberOfLegs, final double maintenanceCost,
			final double dangerPerc, final boolean hasEggs) {
		super(animalName, numberOfLegs, maintenanceCost, dangerPerc, hasEggs);
	}

	public Lizard(ArrayList<String> parameters) {
		super(parameters);
	}
/*
	public void encodeToXML(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXML(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, String.valueOf(Constants.Animals.Reptiles.LIZARD));
	}*/
}
