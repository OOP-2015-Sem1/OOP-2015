package repositories;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import models.interfaces.XML_Parsable;


public abstract class EntityRepository<T extends XML_Parsable> {
    private final String xmlFilename;
    private final String entityTag;
    Context context;

    public EntityRepository(String xmlFilename, String entityTag, Context context) {
        this.xmlFilename = xmlFilename;
        this.entityTag = entityTag;
        this.context = context;
    }

    /*
    public void save(ArrayList<T> entities) throws FileNotFoundException, XMLStreamException {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(this.xmlFilename));

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");

        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);

        StartElement configStartElement = eventFactory.createStartElement("", "", "content");
        eventWriter.add(configStartElement);
        eventWriter.add(end);

        for (XML_Parsable entitity : entities) {
            StartElement sElement = eventFactory.createStartElement("", "", this.entityTag);
            eventWriter.add(sElement);
            eventWriter.add(end);

            entitity.encodeToXml(eventWriter);

            EndElement eElement = eventFactory.createEndElement("", "", this.entityTag);
            eventWriter.add(eElement);
            eventWriter.add(end);
        }

        eventWriter.add(eventFactory.createEndElement("", "", "content"));
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }
    */
    protected abstract T getEntityFromXmlElement(Element element);

    public ArrayList<T> load() throws ParserConfigurationException, SAXException, IOException {

        ArrayList<T> entities = new ArrayList<T>();

        File fXmlFile = new File(this.xmlFilename);
        //InputStream inputStream = context.getAssets().open(this.xmlFilename);

        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        inputStream = assetManager.open(xmlFilename);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document doc = dBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName(this.entityTag);

        for (int i = 0; i < nodeList.getLength(); i++) {
            org.w3c.dom.Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                entities.add(getEntityFromXmlElement(element));
            }
        }
        return entities;
    }

    /*
    public static void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);

        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }
    */

}
