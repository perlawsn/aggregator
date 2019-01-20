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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class XMLParser {

  //public static void main(String argv[]) {

	/**
	 * Questa classe parsa il file XML scaricato dal feed RSS e inserisce i dati nel Database
	 * @param fileName
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
   
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
			//Connection to database	
			Connection con = null;
			Statement cmd = null;
			
			MySqlPerLa myPerla = new MySqlPerLa();
			
			try {
				con = myPerla.connect("jdbc:mysql://localhost/perla_database");
				cmd = con.createStatement();
				
				
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					//System.out.println("id : " + eElement.getAttribute("id"));
					//System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
					//System.out.println("Date : " + eElement.getElementsByTagName("updated").item(0).getTextContent());
					//System.out.println("Latitude : " + eElement.getElementsByTagName("geo:lat").item(0).getTextContent());
					//System.out.println("Longitude : " + eElement.getElementsByTagName("geo:long").item(0).getTextContent());
					//System.out.println("Summary : " + eElement.getElementsByTagName("summary").item(0).getTextContent());
					
					String insertingQuery = "INSERT INTO ingv (identification, title, updated, latitude, longitude, summary)"
							+ "VALUES ("
							+ "'nicetry'," 
							+ "'"+eElement.getElementsByTagName("title").item(0).getTextContent().replace("'", "-")+"', " 
							+ "'"+eElement.getElementsByTagName("updated").item(0).getTextContent().replace("'", "-")+"', "
							+ "'"+eElement.getElementsByTagName("geo:lat").item(0).getTextContent().replace("'", "-")+"', "
							+ "'"+eElement.getElementsByTagName("geo:long").item(0).getTextContent().replace("'", "-")+"', "
							+ "'"+eElement.getElementsByTagName("summary").item(0).getTextContent().replace("'", "-")+"');";
					
					String insertingQuery2 = "INSERT INTO ingv (identification, title, updated, latitude, longitude, summary)"
							+ "VALUES (2, 3, 4, 5, 6, 7);";
					
					System.out.println(insertingQuery);
					
					try {
						cmd.executeUpdate(insertingQuery);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		    
		  }
	
	/**
	 * CREATE TABLE ingv (
      id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
      identification VARCHAR(30) NOT NULL,
    title VARCHAR(30) NOT NULL,
    updated VARCHAR(50),
    latitude VARCHAR(50),
    longitude VARCHAR(50),
     summary VARCHAR(50)
)
	**/

}