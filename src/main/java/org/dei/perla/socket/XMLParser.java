package org.dei.perla.socket;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

public class XMLParser {

  //public static void main(String argv[]) {

   
	public static void xmlParser(String fileName) throws ParserConfigurationException, SAXException, IOException{
	
	 		File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("entry");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("id : " + eElement.getAttribute("id"));
					System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
					System.out.println("Date : " + eElement.getElementsByTagName("updated").item(0).getTextContent());
					System.out.println("Latitude : " + eElement.getElementsByTagName("geo:lat").item(0).getTextContent());
					System.out.println("Longitude : " + eElement.getElementsByTagName("geo:long").item(0).getTextContent());
					System.out.println("Summary : " + eElement.getElementsByTagName("summary").item(0).getTextContent());
				}
			}
		    
		  }

}