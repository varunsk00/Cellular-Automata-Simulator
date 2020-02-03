package cellsociety;

import java.io.File;
import java.io.InputStream;
import javafx.scene.paint.Color;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

  private static double FRAMES_PER_SECOND;
  private static double SCENE_WIDTH;
  private static double SCENE_HEIGHT;
  private static String TYPE;
  private static String TITLE;
  private static String AUTHOR;
  private static Color FILL_COLOR;
  private static Color EMPTY_COLOR;
  private static Color BLOCKED_COLOR;



  public void read() {
    try {
//      InputStream file = this.getClass().getClassLoader().getResourceAsStream("perc.xml");
      File file = new File(
          "/Users/jaidharosenblatt/Documents/CS308/simulation_team04/src/cellsociety/Resources/perc.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(file);
      doc.getDocumentElement().normalize();

      Element details = getElementFromTag(doc, "details");
      TYPE = getTextFromElement(details, "type");
      TITLE = getTextFromElement(details, "title");
      AUTHOR = getTextFromElement(details, "author");

      Element scene = getElementFromTag(doc, "scene");
      SCENE_HEIGHT = Double.parseDouble(getTextFromElement(scene, "sceneHeight"));
      SCENE_WIDTH = Double.parseDouble(getTextFromElement(scene, "sceneWidth"));
      FRAMES_PER_SECOND = Double.parseDouble(getTextFromElement(scene, "framesPerSecond"));

      Element parameters = getElementFromTag(doc, "parameters");
      if (TYPE == "Percolation") {
        SCENE_WIDTH = Double.parseDouble(getTextFromElement(parameters, "percentBlocked"));
        EMPTY_COLOR = Color.valueOf(getTextFromElement(details, "empty"));
        FILL_COLOR = Color.valueOf(getTextFromElement(details, "filled"));
        BLOCKED_COLOR = Color.valueOf(getTextFromElement(details, "blocked"));
      }
//      SCENE_WIDTH = Double.parseDouble(getTextFromElement(eElement,"sceneHeight"));

      System.out.println(TITLE + TITLE + AUTHOR + SCENE_HEIGHT + SCENE_WIDTH + FRAMES_PER_SECOND);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Element getElementFromTag(Document doc, String tag) {
    NodeList nList = doc.getElementsByTagName(tag);
    Node nNode = nList.item(0);
    return (Element) nNode;
  }

  private String getTextFromElement(Element element, String tag) {
    return element.getElementsByTagName(tag).item(0).getChildNodes().item(0).getNodeValue();
  }

}
