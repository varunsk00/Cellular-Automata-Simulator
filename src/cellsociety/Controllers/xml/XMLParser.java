package cellsociety.Controllers.xml;

import cellsociety.Models.Grids.*;

import java.awt.Point;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class handles parsing XML files and converting it into a Grid of specified type. It is
 * dependent on all of the Grid subclasses. This class uses XML start code from
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_simulation
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author Jaidha Rosenblatt
 */
public class XMLParser {

  private final DocumentBuilder DOCUMENT_BUILDER;
  private File myFile;
  private Element root;
  private String simulationType;
  private static final String bundleName = "Standard";
  private ResourceBundle myResources = ResourceBundle.getBundle(bundleName);

  /**
   * Create parser for XML files of given type. Also catches null file and replaces the error
   * message.
   */
  public XMLParser(File dataFile) throws NullPointerException {
    if (dataFile == null) {
      throw new NullPointerException(myResources.getString("NullFile"));
    }
    DOCUMENT_BUILDER = getDocumentBuilder();
    myFile = dataFile;
    root = getRootElement();
  }

  /**
   * Get data contained in this XML file as a grid object by scanning in all tags from XML file,
   * adding them into maps, and returning a grid with resulting data.
   *
   * @return a grid of specified type with full properties
   * @throws XMLException custom exception that handles XML related errors
   */
  public Grid getGrid() throws XMLException {
    simulationType = root.getAttribute("simulationType");

    Map<String, Double> results = getSimulationProperties();
    Map<String, String> cellTypes = getMapBySection("cellTypes");
    Map<String, String> details = getMapBySection("details");
    Map<String, Point> layout = createLayout();
    validateDetailsMap(details);

    return returnGridByType(results, cellTypes, details, layout);
  }

  private Element getRootElement() {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(this.myFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  private DocumentBuilder getDocumentBuilder() {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }

  private NodeList getNodeListFromSectionName(String name) {
    NodeList allVariables = root.getElementsByTagName(name);
    if (allVariables.getLength() == 0) {
      throw new XMLException(myResources.getString("SectionNotFound"), name);
    }
    Element attributes = (Element) allVariables.item(0);
    return attributes.getElementsByTagName("*");
  }

  private Map<String, String> getMapBySection(String section) throws XMLException {
    NodeList variables = getNodeListFromSectionName(section);
    if (variables.getLength() == 0) {
      throw new XMLException(myResources.getString("SectionNotFound"), section);
    }
    Map<String, String> results = new HashMap<>();
    for (int i = 0; i < variables.getLength(); i++) {
      Node temp = variables.item(i);
      if (temp.getNodeType() == Node.ELEMENT_NODE) {
        results.put(temp.getNodeName(), temp.getTextContent());
      }
    }
    return results;
  }

  private Map<String, Double> getSimulationProperties() throws XMLException {
    NodeList variables = getNodeListFromSectionName("simulationProps");
    Map<String, Double> results = new HashMap<>();

    for (int i = 0; i < variables.getLength(); i++) {
      Node temp = variables.item(i);
      if (temp.getNodeType() == Node.ELEMENT_NODE) {
        results.put(temp.getNodeName(), parseDoubleFromString(temp));
      }
    }
    return results;
  }

  private Map<String, Point> createLayout() throws XMLException {
    NodeList allVariables = root.getElementsByTagName("layout");
    if (allVariables.getLength() == 0) {
      return null;
    }
    Element attributes = (Element) allVariables.item(0);
    NodeList cells = attributes.getElementsByTagName("*");

    Map<String, Point> results = new HashMap<>();

    for (int i = 0; i < cells.getLength(); i++) {
      NodeList cell = cells.item(i).getChildNodes();
      addCellToMap(results, cell);
    }
    return results;
  }

  private void addCellToMap(Map<String, Point> results, NodeList cell) {
    Point p = new Point();
    String state = null;

    for (int j = 0; j < cell.getLength(); j++) {
      Node prop = cell.item(j);
      if (prop.getNodeType() == Node.ELEMENT_NODE) {
        if (prop.getNodeName().equals("x")) {
          p.x = parseIntFromString(prop);
        }
        if (prop.getNodeName().equals("y")) {
          p.y = parseIntFromString(prop);
        }
        if (prop.getNodeName().equals("state")) {
          state = prop.getTextContent();
        }
      }
    }
    // Skip initial null cell
    if (state != null) {
      results.put(state, p);
    }
  }

  private double parseDoubleFromString(Node temp) {
    try {
      return Double.parseDouble(temp.getTextContent());
    } catch (NumberFormatException e) {
      throw new XMLException(myResources.getString("ParseDouble"), temp.getNodeName());
    }
  }

  private int parseIntFromString(Node temp) {
    try {
      return Integer.parseInt(temp.getTextContent());
    } catch (NumberFormatException e) {
      throw new XMLException(myResources.getString("ParseInt"), temp.getNodeName());
    }
  }

  private void validateDetailsMap(Map<String, String> map) {
    for (String key : List.of("author", "title", "gridType")) {
      if (!map.containsKey(key)) {
        System.out.println(key);
        System.out.println(map.get(key));
        throw new XMLException(myResources.getString("NullValue"), key);
      }
    }

    String gridType = map.get("gridType");
    if (!gridType.equals("rectangle") & !gridType.equals("hexagon")) {
      throw new XMLException(myResources.getString("InvalidGridType"));
    }
  }

  private Grid returnGridByType(Map<String, Double> results, Map<String, String> cellTypes,
      Map<String, String> details, Map<String, Point> layout)
      throws XMLException {
    switch (simulationType) {
      case "Fire":
        return new FireGrid(results, cellTypes, details, layout);
      case "Percolation":
        return new PercGrid(results, cellTypes, details, layout);
      case "Life":
        return new LifeGrid(results, cellTypes, details, layout);
      case "Segregation":
        return new SegGrid(results, cellTypes, details, layout);
      case "PredPrey":
        return new PredPreyGrid(results, cellTypes, details, layout);
      case "RockPaperScissors":
        return new RPSGrid(results, cellTypes, details, layout);
      case "Foraging":
        return new ForageGrid(results, cellTypes, details, layout);
    }
    throw new XMLException(myResources.getString("InvalidSimulationType"));
  }

}
