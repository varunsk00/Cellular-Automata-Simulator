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


  /**
   * Create parser for XML files of given type.
   */
  public XMLParser(String type) {
    DOCUMENT_BUILDER = getDocumentBuilder();
    TYPE_ATTRIBUTE = type;
  }

  /**
   * Get data contained in this XML file as an object
   *
   * @param dataFile an cellsociety.xml file to read
   * @return Grid object based on grid type in cellsociety.xml file
   */
  public Grid getGrid(File dataFile) {
    Element root = getRootElement(dataFile);
    String type = getAttribute(root, TYPE_ATTRIBUTE);

    List<String> dataFields = setDataFieldsByGridType(type);

    // read data associated with the fields given by the object
    Map<String, String> results = createDataFieldMap(root, dataFields);
    return returnGridByType(type, results);
  }

  /**
   * Returns the author of the file
   * @param dataFile File to be read
   * @return the String associated with the author tag
   */
  public String getAuthors(File dataFile) {
    Element root = getRootElement(dataFile);
    return getTextValue(root, "author");
  }

  /**
   * Returns the title of the file
   * @param dataFile File to be read
   * @return the String associated with the title tag
   */
  public String getTitle(File dataFile) {
    Element root = getRootElement(dataFile);
    return getTextValue(root, "title");
  }

  private Map<String, String> createDataFieldMap(Element root, List<String> dataFields) {
    Map<String, String> results = new HashMap<>();
    for (String field : dataFields) {
      results.put(field, getTextValue(root, field));
    }
    return results;
  }

  private Element getRootElement(File xmlFile) {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  private String getAttribute(Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  private String getTextValue(Element e, String tagName) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    } else {
      return "";
    }
  }

  private DocumentBuilder getDocumentBuilder() {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }

  private List<String> setDataFieldsByGridType(String type) {
    switch (type) {
      case "Fire":
        return FireGrid.DATA_FIELDS;
      case "Percolation":
        return PercGrid.DATA_FIELDS;
      case "Life":
        return LifeGrid.DATA_FIELDS;
      case "Segregation":
        return SegGrid.DATA_FIELDS;
      case "PredPrey":
        return PredPreyGrid.DATA_FIELDS;
    }
    return null;
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
