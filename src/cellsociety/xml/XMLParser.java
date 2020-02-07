package cellsociety.xml;

import cellsociety.Grids.FireGrid;
import cellsociety.Grids.Grid;
import cellsociety.Grids.LifeGrid;
import cellsociety.Grids.PercGrid;
import cellsociety.Grids.PredPreyGrid;
import cellsociety.Grids.SegGrid;

import java.util.List;
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

  private final String TYPE_ATTRIBUTE;
  private final DocumentBuilder DOCUMENT_BUILDER;
  private File myFile;
  private Element root;

  /**
   * Create parser for XML files of given type.
   */
  public XMLParser(String type, File dataFile) {
    DOCUMENT_BUILDER = getDocumentBuilder();
    TYPE_ATTRIBUTE = type;
    myFile = dataFile;
    root = getRootElement();
  }

  /**
   * Get data contained in this XML file as an object
   *
   * @return Grid object based on grid type in cellsociety.xml file
   */
  public Grid getGrid() {
    String type = getAttribute(root, TYPE_ATTRIBUTE);

    // read data associated with the fields given by the object
    Map<String, String> results = createDataFieldMap(root);
    return returnGridByType(type, results);
  }


  public String getGridType() {
    return getAttribute(root, TYPE_ATTRIBUTE);
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

  private String getAttribute(Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }


  private DocumentBuilder getDocumentBuilder() {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }

  private Grid returnGridByType(String type, Map<String, String> results) {
    switch (type) {
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
    }
    return null;
  }
}
