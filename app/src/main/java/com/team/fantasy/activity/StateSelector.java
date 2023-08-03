package com.team.fantasy.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class StateSelector {
    private List<String> stateList;

    public StateSelector() {
        // Initialize the stateList as an empty list
        stateList = new ArrayList<>();
    }

    public void loadStateData(Context context) {
        // Load the XML data from the assets folder
        String xmlData = loadXmlDataFromAssets(context, "states.xml");
        stateList = parseStateXml(xmlData);
    }

    private String loadXmlDataFromAssets(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> parseStateXml(String xmlData) {
        List<String> states = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xmlData));
            Document document = documentBuilder.parse(inputSource);
            NodeList tableNodes = document.getElementsByTagName("table");

            for (int i = 0; i < tableNodes.getLength(); i++) {
                Node tableNode = tableNodes.item(i);
                if (tableNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element tableElement = (Element) tableNode;
                    if ("states".equals(tableElement.getAttribute("name"))) {
                        NodeList columnNodes = tableElement.getElementsByTagName("column");
                        String name = "";
                        String countryId = "";
                        for (int j = 0; j < columnNodes.getLength(); j++) {
                            Node columnNode = columnNodes.item(j);
                            if (columnNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element columnElement = (Element) columnNode;
                                String columnName = columnElement.getAttribute("name");
                                String columnValue = columnElement.getTextContent();
                                if ("name".equals(columnName)) {
                                    name = columnValue;
                                } else if ("country_id".equals(columnName)) {
                                    countryId = columnValue;
                                }
                            }
                        }
                        // Add the state name to the list if the country_id is 101
                        if ("101".equals(countryId)) {
                            states.add(name);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return states;
    }

    public List<String> getStatesWithCountryId101() {
        return stateList;
    }
}
