package cellsociety;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
  private static final int FRAMES_PER_SECOND = 1;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static double SCENE_WIDTH = 400;
  private static double SCENE_HEIGHT = 500;

  public void main(String[] argv)  {
    try{
      InputStream file = this.getClass().getClassLoader().getResourceAsStream("test.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(file);

      doc.getDocumentElement().normalize();
      System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
      System.out.println("----------------------------");
      NodeList nList = doc.getElementsByTagName("staff");

      for (int temp = 0; temp < nList.getLength(); temp++) {

        Node nNode = nList.item(temp);

        System.out.println("\nCurrent Element :" + nNode.getNodeName());

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

          Element eElement = (Element) nNode;

          System.out.println("Staff id : " + eElement.getAttribute("id"));
          System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
          System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
          System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
          System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

        }
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

}
