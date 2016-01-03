package javasmmr.zoowsome.models.interfaces;
import org.w3c.dom.Element;

//import javax.xml.stream.XMLEventWriter;
//import javax.xml.stream.XMLStreamException;

public interface XML_Parsable {
//public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException;
public void decodeFromXml(Element element);
}