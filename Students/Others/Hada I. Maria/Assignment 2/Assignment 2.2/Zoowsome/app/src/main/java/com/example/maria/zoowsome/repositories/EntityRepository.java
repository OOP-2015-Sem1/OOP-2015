package com.example.maria.zoowsome.repositories;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.maria.zoowsome.models.interfaces.XML_Parsable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Maria on 10/31/2015.
 */
public abstract class EntityRepository<T extends XML_Parsable> {

    private static Context context;
    private final String xmlFilename;
    private final String entityTag;

    public EntityRepository(String xmlFilename, String entityTag, Context context) {
        this.xmlFilename = xmlFilename;
        this.entityTag = entityTag;
        this.context = context;
    }

    protected abstract T getEntityFromXmlElement(Element element);

    public ArrayList<T> load() throws ParserConfigurationException, SAXException, IOException {

        ArrayList<T> entities = new ArrayList<T>();

        AssetManager assetManager = context.getAssets();
        File fXmlFile = new File(this.xmlFilename);

        InputStream inputStr = null;
        inputStr = assetManager.open(xmlFilename);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputStr);
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
}
