package models.interfaces;

import org.w3c.dom.Element;

public interface XML_Parsable {

/*    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException;*/

    void decodeFromXml(Element element);

}
