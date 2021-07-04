
package updater;
 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class ReadXMLFile {
    
    int major = 0;
    int minor = 0;
    int patch = 0;

    public void getLatestVersion(File fXmlFile) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("version");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);     
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    major = Integer.parseInt(eElement.getAttribute("major"));
                    minor = Integer.parseInt(eElement.getElementsByTagName("minor").item(0).getTextContent());
                    patch = Integer.parseInt(eElement.getElementsByTagName("patch").item(0).getTextContent());
                   break;           
                }
            }
        //fXmlFile.deleteOnExit();
        } 
        catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
            e.printStackTrace();
        }
    }

}
