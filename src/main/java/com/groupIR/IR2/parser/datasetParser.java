package com.groupIR.IR2.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class datasetParser {

	public static void LATimeParser() throws SAXException, IOException, ParserConfigurationException {

		PrintWriter writer = null;
		if (Files.notExists(Paths.get("outputFolder"))){
			Boolean success = (new File("outputFolder")).mkdirs();
			if (!success) {
			    System.out.println("Directory creation failed");
			    System.exit(1);
			}
		}
		
		String path = "/home/rahul/Intelligent System/Information Retrieval/Assignment Two/Assignment Two/latimes/";
		File folder = new File(path);
		File fileList[] = folder.listFiles();
		int k = 0;
		for (File fileName : fileList) {
			System.out.println("reading the file: " + fileName.getName());
			System.out.println("----------------------------------------------------");
			String xml = getXML(path + fileName.getName());

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xml)));
			Element rootElement = doc.getDocumentElement();
			System.out.println("Parent Node: " + rootElement.getNodeName());
			NodeList nodes = doc.getElementsByTagName("DOC");
			for (int i = 0; i < nodes.getLength(); i++) {
				File file = new File("outputFolder/" +""+ k+1 + "-" +i+1);
				writer = new PrintWriter(new FileWriter(file));
				writer.println("<DOCUMENT>");
				Node node = nodes.item(i);
				Element ele = (Element) node;
				if (ele.getElementsByTagName("DOCNO").item(0) != null) {
					
					System.out.println(ele.getElementsByTagName("DOCNO").item(0).getTextContent());
					writer.println("<DOCNO>"+ele.getElementsByTagName("DOCNO").item(0).getTextContent()+"</DOCNO>");
				}
				
				else {
					writer.println("<DOCNO> </DOCNO>");
				}

				if (ele.getElementsByTagName("DOCID").item(0) != null) {
					System.out.println(ele.getElementsByTagName("DOCID").item(0).getTextContent());
					writer.println("<DOCID>"+ele.getElementsByTagName("DOCID").item(0).getTextContent()+"</DOCID>");
				}
				else {
					writer.println("<DOCID></DOCID>");
				}

				if (ele.getElementsByTagName("DATE").item(0) != null) {
					System.out.println(ele.getElementsByTagName("DATE").item(0).getTextContent());

					writer.println("<DATE>"+ele.getElementsByTagName("DATE").item(0).getTextContent()+"</DATE>");
				}
				else {
					writer.println("<DATE></DATE>");
				}

//				if (ele.getElementsByTagName("SECTION").item(0) != null) {
//					System.out.println(ele.getElementsByTagName("SECTION").item(0).getTextContent());
//				}

				if (ele.getElementsByTagName("GRAPHIC").item(0) != null ) {
					System.out.println(ele.getElementsByTagName("GRAPHIC").item(0).getTextContent());
					
					if (ele.getElementsByTagName("TYPE").item(0) != null) {
						System.out.println(ele.getElementsByTagName("TYPE").item(0).getTextContent());
						writer.println("<OTHER>");
						writer.println(ele.getElementsByTagName("GRAPHIC").item(0).getTextContent());
						writer.println(ele.getElementsByTagName("TYPE").item(0).getTextContent());
						writer.println("</OTHER>");
					}
					
					else {
						writer.println("<OTHER>");
						writer.println(ele.getElementsByTagName("GRAPHIC").item(0).getTextContent());
						writer.println("</OTHER>");
					}
				}
				else {
					writer.println("<OTHER></OTHER>");
				}

				
				
				if (ele.getElementsByTagName("HEADLINE").item(0) != null) {
					System.out.println(ele.getElementsByTagName("HEADLINE").item(0).getTextContent());
					writer.println("<TITLE>"+ele.getElementsByTagName("HEADLINE").item(0).getTextContent()+"</TITLE>");
				}
				else {
					writer.println("<TITLE></TITLE>");
				}
				
				if (ele.getElementsByTagName("TEXT").item(0) != null) {
					System.out.println(ele.getElementsByTagName("TEXT").item(0).getTextContent());
					writer.println("<BODY>"+ele.getElementsByTagName("TEXT").item(0).getTextContent()+"</BODY>");
				}
				else {
					writer.println("<BODY></BODY>");
				}
				
				if (ele.getElementsByTagName("BYLINE").item(0) != null) {
					System.out.println(ele.getElementsByTagName("BYLINE").item(0).getTextContent());
					writer.println("<AUTHOR>"+ele.getElementsByTagName("BYLINE").item(0).getTextContent()+"</AUTHOR>");
				}
				else {
					writer.println("<AUTHOR></AUTHOR>");
				}
				writer.println("</DOCUMENT>");
				writer.close();
				
			}
			k++;
		}

	}

	public static String getXML(String fileName) throws IOException {
		File file = new File(fileName);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));
		sb.append("<LACONTENT>");
		String line = br.readLine();
		while (line != null) {
			if (line.contains("&")) {
				String s = line.replace("&", "&amp;");
				sb.append(s);
			}
			else {
			sb.append(line);}
			line = br.readLine();
		}
		sb.append("</LACONTENT>");
		br.close();
		return sb.toString();

	}
	
	public static void queryparser() throws IOException, ParserConfigurationException, SAXException {
		String path = "query/topics.txt";
		File file = new File(path);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		
		br.close();
		String xml = sb.toString();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xml)));
		Element rootElement = doc.getDocumentElement();
		System.out.println("Parent Node: " + rootElement.getNodeName());
		NodeList nodes = doc.getElementsByTagName("top");
//		
//		** this is to be replaced by one if we want to loop to all the top element**
//		nodes.getLength()
//		
		for (int i = 0; i < 1; i++) {
			Node node = nodes.item(i);
			Element ele = (Element) node;
			if (ele.getElementsByTagName("num").item(0) != null) {
				
				System.out.println(i+": --"+ele.getElementsByTagName("num").item(0).getTextContent());
				System.out.println("------------------");
				
			}
			
			if (ele.getElementsByTagName("title").item(0) != null) {
				
				System.out.println(i+": --"+ele.getElementsByTagName("title").item(0).getTextContent());
				System.out.println("------------------");
				
			}

			if (ele.getElementsByTagName("desc").item(0) != null) {
	
				System.out.println(i+": --"+ele.getElementsByTagName("desc").item(0).getTextContent());
				System.out.println("------------------");
	
			}
			
			if (ele.getElementsByTagName("narr").item(0) != null) {
				
				System.out.println(i+": --"+ele.getElementsByTagName("narr").item(0).getTextContent());
				System.out.println("------------------");
	
			}
		}
		
	}
	
	public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException {
		LATimeParser();
		
	}

}
