package cellsociety.Controllers.xml;

import cellsociety.Models.Grids.*;

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
 * This class handles parsing XML files and returning a completed object. Modified from
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
  private ResourceBundle myResources = ResourceBundle.getBundle("XMLErrors");
  ;

  /**
   * Create parser for XML files of given type.
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
   * Get data contained in this XML file as an object
   *
   * @return Grid object based on grid type in cellsociety.Controllers.xml file
   */
  public Grid getGrid() throws XMLException {
    simulationType = root.getAttribute("simulationType");

    // read data associated with the fields given by the object
    Map<String, Double> results = getSimulationProperties();
    Map<String, String> cellTypes = getMapBySection("cellTypes");
    Map<String, String> details = getMapBySection("details");
    validateDetailsMap(details);

    return returnGridByType(results, cellTypes, details);
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

  public Map<String, String> getMapBySection(String section) throws XMLException {
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

  public Map<String, Double> getSimulationProperties() throws XMLException {
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

  private double parseDoubleFromString(Node temp) {
    try {
      return Double.parseDouble(temp.getTextContent());
    } catch (NumberFormatException e) {
      throw new XMLException(myResources.getString("ParseDouble"), temp.getNodeName());
    }
  }

  private void validateDetailsMap(Map<String, String> map) {
    for (String key : List.of("author","title", "gridType")) {
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

  private Grid returnGridByType(Map<String, Double> results, Map<String, String> cellTypes, Map<String, String> details)
      throws XMLException {
    switch (simulationType) {
      case "Fire":
        return new FireGrid(results, cellTypes, details);
      case "Percolation":
        return new PercGrid(results, cellTypes, details);
      case "Life":
        return new LifeGrid(results, cellTypes, details);
      case "Segregation":
        return new SegGrid(results, cellTypes, details);
      case "PredPrey":
        return new PredPreyGrid(results, cellTypes, details);
      case "RockPaperScissors":
        return new RPSGrid(results, cellTypes, details);
      case "Foraging":
        return new ForageGrid(results, cellTypes, details);
    }
    throw new XMLException(myResources.getString("InvalidSimulationType"));
  }

}
