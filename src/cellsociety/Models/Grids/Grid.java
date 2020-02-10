package cellsociety.Models.Grids;

import cellsociety.Controllers.xml.XMLException;
import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import cellsociety.Models.Cell;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class Grid {

  private List<List<Cell>> grid;
  private int rows;
  private int columns;
  private String gridType;
  private Map<String, String> stateMap;
  private Map<String, String> details;
  protected int numIterations;

  private ResourceBundle myResources = ResourceBundle.getBundle("Standard");


  /**
   * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
   * based on given rows and columns
   *
   * @param rows    the number of rows to generate in our grid
   * @param columns the number of columns to generate in our grid
   **/
  public Grid(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    this.grid = createGrid();
    this.numIterations = 0;
  }

  public Grid(Map<String, Double> data, Map<String, String> cellTypes, Map<String, String> details, List<String> states)
      throws XMLException {
    checkValidStates(states, cellTypes);
    this.stateMap = cellTypes;
    this.details = details;
    this.gridType = details.get("gridType");
    this.rows = getIntFromData(data, "rows");
    this.columns = getIntFromData(data, "columns");
    this.grid = createGrid();
    this.numIterations = 0;
  }

  public Map<String, String> getStateMap() {
    return stateMap;
  }

  public Map<String, String> getDetails() {
    return details;
  }

  protected void setInitState(Map<String, Point> layout) throws XMLException{
    for (String state : layout.keySet()){
      Point p = layout.get(state);

      if (p.getX() > columns - 1 || p.getX() < 0 || p.getY() > rows - 1 || p.getY() < 0){
        throw new XMLException(myResources.getString("CellOutOfBounds"));
      }
      setCellState(p.x, p.y, state);
    }
  }
  protected void checkValidStates(List<String> states, Map<String, String> data) {
    for (String state : data.keySet()) {
      if (!states.contains(state)) {
        throw new XMLException(myResources.getString("InvalidState"), state);
      }
    }
    for (String state : states) {
      if (!data.containsKey(state)) {
        throw new XMLException(myResources.getString("MissingState"), state);
      }
    }
  }

  protected int getIntFromData(Map<String, Double> data, String prop) throws XMLException {
    if (!data.containsKey(prop)) {
      throw new XMLException(myResources.getString("NullValue"), prop);
    }
    double d = data.get(prop);
    if (d % 1 != 0) {
      throw new XMLException(myResources.getString("ParseInt"), prop);
    }
    return (int) d;
  }

  protected double getDoubleFromData(Map<String, Double> data, String prop) {
    if (!data.containsKey(prop)) {
      throw new XMLException(myResources.getString("NullValue"), prop);
    }
    return data.get(prop);
  }

  /**
   * @return Cells in an immutable list representing Grid
   */
  protected List<List<Cell>> getGrid() {
    return Collections.unmodifiableList(this.grid);
  }

  /**
   * @return the the number of rows in our grid
   */
  public int getRows() {
    return this.rows;
  }

  /**
   * @return the number of columns in our grid
   */
  public int getColumns() {
    return this.columns;
  }

  public Cell current(int x, int y) {
    return this.grid.get(x).get(y);
  }

  /**
   * Checks every cell in the current grid and updates based on state of neighbors
   *
   * @return a grid (2D array of cells) with updated state
   */
  public void updateGrid() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        List<Cell> neighbors = getAllNeighbors(i, j);
        updateCell(i, j, neighbors);
      }
    }
    numIterations++;
  }

  /**
   * Initializes an ArrayList of ArrayLists representative of the grid
   **/
  protected List<List<Cell>> createGrid() {
    List<List<Cell>> ret = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      ArrayList<Cell> row = new ArrayList<>();
      for (int j = 0; j < columns; j++) {
        row.add(new Cell("empty", j, i));
      }
      ret.add(row);
    }
    return ret;
  }

  protected void updateCell(int x, int y, List<Cell> neighbors) {
  }

  protected Cell getCell(int x, int y) {
    return getGrid().get(x).get(y);
  }

  protected void setCellState(int x, int y, String state) {
    current(x, y).setState(state);
  }

  private List<Cell> getAllRectangleNeighbors(int x, int y) {
    List<Cell> neighbors = new ArrayList<>();
    for (int i = x - 1; i <= x + 1; i++) {
      for (int j = y - 1; j <= y + 1; j++) {
        if (isOutOfBounds(i, j)) {
          continue;
        }
        if (!(i == x && j == y)) {
          neighbors.add(getCell(i, j));
        }
      }
    }
    return neighbors;
  }

  protected List<Cell> getHexNeighbors(int row, int col) {
    List<Cell> neighbors = new ArrayList<>();
    for (int i = row - 1; i <= row + 1; i++) {
      for (int j = col - 1; j <= col + 1; j++) {
        if (isOutOfBounds(i, j)) {
          continue;
        }
        if (!(i == row + 1 && j == col + 1) || !(i == row - 1 && j == col + 1)) {
          neighbors.add(getCell(i, j));
        }
      }
    }
    return neighbors;
  }

  protected List<Cell> getNeighbors(int x, int y) throws XMLException{
    switch (gridType){
      case "rectangle":
        return getRectangleNeighbors(x, y);
      case "hexagon":
        return getHexNeighbors(x, y);
    }
    throw new XMLException(myResources.getString("InvalidGridType"));
  }

  protected List<Cell> getAllNeighbors(int x, int y) throws XMLException{
    switch (gridType){
      case "rectangle":
        return getAllRectangleNeighbors(x, y);
      case "hexagon":
        return getHexNeighbors(x, y);
    }
    throw new XMLException(myResources.getString("InvalidGridType"));
  }

  private List<Cell> getRectangleNeighbors(int x, int y) {
    List<Cell> neighbors = new ArrayList<>();
    for (int i = x - 1; i <= x + 1; i++) {
      for (int j = y - 1; j <= y + 1; j++) {
        if (isOutOfBounds(i, j)) {
          continue;
        }
        if (i == x || j == y) {
          neighbors.add(getCell(i, j));
        }
      }
    }
    return neighbors;
  }

  protected boolean isOutOfBounds(int x, int y) {
    return y < 0 || y >= rows || x < 0 || x >= columns;
  }

  /**
   * Returns a boolean representing if neighbors contains a point that is a neighbor of (x,y)
   *
   * @param x         the x coordinate of the cell to check for neighbors
   * @param y         the y coordinate of the cell to check for neighbors
   * @param neighbors an List of points to check if any contain a neighboring point to (x,y)
   * @return a boolean if the list contains a neighbor or not
   */
  protected boolean checkNeighbors(int x, int y, List<Point> neighbors) {
    if (neighbors.contains(new Point(x + 1, y))) {
      return true;
    }
    if (neighbors.contains(new Point(x - 1, y))) {
      return true;
    }
    if (neighbors.contains(new Point(x, y + 1))) {
      return true;
    }
    if (neighbors.contains(new Point(x, y - 1))) {
      return true;
    }
    return false;
  }

  protected void storeCellsByState(List<Point> neighborCells, String state) {
    neighborCells.clear();
    for (List<Cell> row : getGrid()) {
      for (Cell cell : row) {
        if (cell.getState().equals(state)) {
          neighborCells.add(new Point(getGrid().indexOf(row), row.indexOf(cell)));
        }
      }
    }
  }

  public Map getStats() {
    Map<String, Integer> stats = new HashMap<String, Integer>();
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getColumns(); j++) {

        if (!stats.keySet().contains(current(i, j).getState())) {
          stats.put(current(i, j).getState(), 1);
        } else {
          stats.put(current(i, j).getState(), stats.get(current(i, j).getState()) + 1);
        }
      }
    }
    return stats;
  }

  public int getNumIterations() {
    return numIterations;
  }
}