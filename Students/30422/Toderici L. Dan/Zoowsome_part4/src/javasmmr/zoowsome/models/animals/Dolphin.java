package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Dolphin extends Aquatic
{
	 public Dolphin()
	 {
		 super(2.5,0.1);
		 this.setName("Tim");
		 this.setNrOfLegs(0);
		 this.setAvgSwimDepth(150);
		 this.setTypeOfWater(TypesOfWater.saltWater);
	 }
	 
	 public Dolphin(String name, int nrOfLegs, int avgSwimDepth,TypesOfWater waterType)
	 {
		 super(2.5,0.1);
		 this.setName(name);
		 this.setNrOfLegs(nrOfLegs);
		 this.setAvgSwimDepth(avgSwimDepth);
		 this.setTypeOfWater(waterType);
	 }
	 public Dolphin( int avgSwimDepth,TypesOfWater waterType)
	 {
		 super(2.5,0.1);
		 this.setName("Tim");
		 this.setNrOfLegs(0);
		 this.setAvgSwimDepth(avgSwimDepth);
		 this.setTypeOfWater(waterType);
	 }
	 
	 public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
		{
			super.encodeToXml(eventWriter);
			createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Aquatic.Dolphin);
		}
	
}
