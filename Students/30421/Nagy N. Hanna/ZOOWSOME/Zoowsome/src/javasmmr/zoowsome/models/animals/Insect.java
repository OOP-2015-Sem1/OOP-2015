package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

public abstract class Insect extends Animal {

	protected boolean canFly;
	protected boolean isDangerous;

	public Insect(Double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
	}

   public void setCanFly(boolean can){
	   this.canFly=can;
   }

	public void setIsDangerous(boolean b) {
		// TODO Auto-generated method stub
		this.isDangerous=b;
		
	}
	private boolean getIsDangerous() {
		// TODO Auto-generated method stub
		return isDangerous;
	}

	private boolean getCanFly() {
		// TODO Auto-generated method stub
		return canFly;
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "canFly", String.valueOf(getCanFly()));
		createNode(eventWriter, "isDangerous", String.valueOf(getIsDangerous()));
	}
	
	public void decodeFromXml(Element element) {
		//setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("canFly").item(0).getTextContent()));
		//setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("isDangerous").item(0).getTextContent()));
		setCanFly(Boolean.valueOf(element.getElementsByTagName("canFly").item(0).getTextContent()));
		setIsDangerous(Boolean.valueOf(element.getElementsByTagName("isDangerous").item(0).getTextContent()));
		}

}
