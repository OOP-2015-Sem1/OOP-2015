package repositories;

import android.content.Context;
import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.employees.Employee;
import models.interfaces.XML_Parsable;

public abstract class EntityRepository<T extends XML_Parsable> {
    private final String xmlFilename;
    private final String entityTag;
    private static Context context;
    public EntityRepository(String xmlFilename, String entityTag, Context context) {
        this.xmlFilename = xmlFilename;
        this.entityTag = entityTag;
        this.context = context;
    }

    public static void setContext(Context context) {
        EntityRepository.context = context;
    }

    protected abstract T getEntityFromXmlElement(Element element);

/*	public static void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");

		// Create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);

		// Create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);

		// Create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);

	}

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

		for (XML_Parsable entity : entities) {
			StartElement sElement = eventFactory.createStartElement("", "", this.entityTag);
			eventWriter.add(sElement);
			eventWriter.add(end);

			entity.encodeToXml(eventWriter);

			EndElement eElement = eventFactory.createEndElement("", "", this.entityTag);
			eventWriter.add(eElement);
			eventWriter.add(end);
		}

		eventWriter.add(eventFactory.createEndElement("", "", "content"));
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}*/

    	public ArrayList<T> load() throws ParserConfigurationException, SAXException, IOException {

            ArrayList<T> entities = new ArrayList<T>();

            InputStream inputStream = context.getAssets().open(xmlFilename);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName(this.entityTag);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    entities.add(getEntityFromXmlElement(element));

                }
            }
            return entities;
        }
/*    public ArrayList<T> load() throws XmlPullParserException, FileNotFoundException {
        ArrayList<T> entities = new ArrayList<T>();

*//*
        FileInputStream xmlFile = new FileInputStream(new File(this.xmlFilename));

        XmlPullParserFactory factory;
        XmlPullParser parser;

        factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);

        parser = factory.newPullParser();
        parser.setInput(xmlFile, null);

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {

        }
*//*
        AssetManager assetManager=context.getAssets();
        InputStream input;


        return entities;
    }*/

}