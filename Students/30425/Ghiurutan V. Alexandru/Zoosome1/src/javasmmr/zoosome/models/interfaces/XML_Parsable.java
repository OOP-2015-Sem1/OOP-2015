package javasmmr.zoosome.models.interfaces;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.Element;

/**
 * 
 * @author Alexandru This interface is used to create a database file by storing
 *         to a xml file with method encodeToXml all the animals.Also the other
 *         method decodeFromXml will do the opposite operation.
 */
public interface XML_Parsable {
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException;

	public void decodeFromXml(Element element);
}