package com.groupIR.IR2.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class xmlParser {

	
	public static Map parse(String filename, String[] tagnames) throws SAXException, IOException, ParserConfigurationException {
		
		File folder = new File("outputFolderFT/");
		File[] listOfFiles = folder.listFiles();
		Document doc = null;
		String eleContent = null;
		Map<String, String> m1 = new HashMap(); 
		for (File fileName : listOfFiles) {			
			if (filename.equals(fileName.getName())){				
				System.out.println("reading the file: " + fileName.getName());
				System.out.println("----------------------------------------------------");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				doc = builder.parse(new InputSource("outputFolderFT/" + fileName.getName()));
				NodeList nodes = doc.getElementsByTagName("DOCUMENT");
				Node node = nodes.item(0);
				Element ele = (Element) node;
				for (int i=0; i<tagnames.length; i++) {
					if (ele.getElementsByTagName(tagnames[i]).item(0) != null) {
						
						eleContent = ele.getElementsByTagName(tagnames[i]).item(0).getTextContent();
						m1.put(tagnames[i], eleContent);
						
					}
				}
				
				break;
			}
		
		}
		return m1;
	}
	
}
