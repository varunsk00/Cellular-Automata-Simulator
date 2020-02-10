package cellsociety.Controllers.xml;

import cellsociety.Models.Grids.*;

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
  private String gridType;
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
    gridType = root.getAttribute("simulationType");

    // read data associated with the fields given by the object
    Map<String, Double> results = getSimulationProperties();
    Map<String, String> cellTypes = getMapBySection("cellTypes");

    return returnGridByType(results, cellTypes);
  }


  public String getGridType() {
//    if (!gridType.equals("rectangle") & !gridType.equals("rectangle")){
//      throw new XMLException(myResources.getString("InvalidGridType"));
//    }
    return this.gridType;
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

  private Grid returnGridByType(Map<String, Double> results, Map<String, String> cellTypes)
      throws XMLException {
    switch (gridType) {
      case "Fire":
        return new FireGrid(results, cellTypes);
      case "Percolation":
        return new PercGrid(results, cellTypes);
      case "Life":
        return new LifeGrid(results, cellTypes);
      case "Segregation":
        return new SegGrid(results, cellTypes);
      case "PredPrey":
        return new PredPreyGrid(results, cellTypes);
      case "RockPaperScissors":
        return new RPSGrid(results, cellTypes);
    }
    throw new XMLException(myResources.getString("InvalidSimulationType"));
  }

}
