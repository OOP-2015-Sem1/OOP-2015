package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

public abstract class Reptile extends Animal{
	
	private boolean laysEggs;

	
	public Reptile(Double maintenanceCost,double dangerPerc){
		super(maintenanceCost,dangerPerc);
	}
	
	
	public boolean isLaysEggs() {
		return laysEggs;
	}

	public void setLaysEggs(boolean laysEggs) {
		this.laysEggs = laysEggs;
	}
	
	public void decodeFromXml(Element element) {
		setLaysEggs(Boolean.valueOf(element.getElementsByTagName("laysEggs").item(0).getTextContent()));
		
		}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "laysEggs", String.valueOf(getLaysEggs()));
		
		}


	private boolean getLaysEggs() {
		// TODO Auto-generated method stub
		return laysEggs;
	}



}
