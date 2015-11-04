package com.example.roxana.zoo.models.interfaces;

import org.w3c.dom.Element;

/**
 * Created by Roxana on 10/25/2015.
 */
public interface XML_Parsable {

    //public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException;
    public void decodeFromXml(Element element);
}