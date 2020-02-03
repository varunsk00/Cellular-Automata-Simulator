package xml;

import cellsociety.FireGrid;
import cellsociety.Grid;
import cellsociety.LifeGrid;
import cellsociety.PercGrid;
import java.util.ArrayList;
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
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class XMLParser {

  // Readable error message that can be displayed by the GUI
  public static final String ERROR_MESSAGE = "XML file does not represent %s";
  // name of root attribute that notes the type of file expecting to parse
  private final String TYPE_ATTRIBUTE;
  // keep only one documentBuilder because it is expensive to make and can reset it before parsing
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
   */
  public Grid getGrid(File dataFile) {
    Element root = getRootElement(dataFile);

    String type = getAttribute(root, TYPE_ATTRIBUTE);

    List<String> dataFields = new ArrayList<>();

    switch (type){
      case "Fire":
        dataFields = FireGrid.DATA_FIELDS;
        break;
      case "Percolation":
        dataFields = PercGrid.DATA_FIELDS;
        break;
      case "Life":
        dataFields = LifeGrid.DATA_FIELDS;
        break;
    }

    System.out.println(dataFields);
    // read data associated with the fields given by the object
    Map<String, String> results = new HashMap<>();
    for (String field : dataFields) {
      results.put(field, getTextValue(root, field));
    }

    switch (type){
      case "Fire":
        return new FireGrid(results);
      case "Percolation":
        return new PercGrid(results);
      case "Life":
        return new LifeGrid(results);
    }

    return new Grid(0, 0);
  }

  // get root element of an XML file
  private Element getRootElement(File xmlFile) {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  // get value of Element's attribute
  private String getAttribute(Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  // get value of Element's text
  private String getTextValue(Element e, String tagName) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    } else {
      // FIXME: empty string or exception? In some cases it may be an error to not find any text
      return "";
    }
  }

  // boilerplate code needed to make a documentBuilder
  private DocumentBuilder getDocumentBuilder() {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }
}
