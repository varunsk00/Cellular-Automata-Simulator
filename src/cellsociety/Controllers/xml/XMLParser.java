package cellsociety.Controllers.xml;

import cellsociety.Models.Grids.*;

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

  /**
   * Create parser for XML files of given type.
   */
  public XMLParser(File dataFile) throws NullPointerException {
    if (dataFile == null) {
      throw new NullPointerException("Please upload a valid XML file");
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
  public Grid getGrid() {
    gridType = root.getAttribute("simulationType");

    // read data associated with the fields given by the object
    Map<String, String> results = createDataFieldMap(root);
    return returnGridByType(results);
  }


  public String getGridType() {
    return this.gridType;
  }


  private Map<String, String> createDataFieldMap(Element root) {
    Map<String, String> results = new HashMap<>();

    NodeList nList = root.getChildNodes();
    for (int i = 0; i < nList.getLength(); i++) {
      Node temp = nList.item(i);
      if (temp.getNodeType() == Node.ELEMENT_NODE) {
        results.put(temp.getNodeName(), temp.getTextContent());
      }
    }
    return results;
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

  private Grid returnGridByType(Map<String, String> results) {
    switch (gridType) {
      case "Fire":
        return new FireGrid(results);
      case "Percolation":
        return new PercGrid(results);
      case "Life":
        return new LifeGrid(results);
      case "Segregation":
        return new SegGrid(results);
      case "PredPrey":
        return new PredPreyGrid(results);
      case "RockPaperScissors":
        return new RPSGrid(results);
    }
    return null;
  }
}
