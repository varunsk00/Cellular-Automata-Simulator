package cellsociety.Models.Grids;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import cellsociety.Models.Cell;
import java.util.HashMap;
import java.util.Map;


public class Grid {

  private List<List<Cell>> grid;
  private int rows;
  private int columns;
  private String title;
  private String author;
  private int countState;

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
    this.countState = 0;
  }

  public Grid(Map<String, String> data) {
    this.rows = parseIntFromMap(data, "rows");
    this.columns = parseIntFromMap(data, "columns");
    this.author = parseStringFromMap(data, "author");
    this.title = parseStringFromMap(data, "title");
    this.grid = createGrid();
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  protected int parseIntFromMap(Map<String, String> data, String key) {
    return Integer.parseInt(data.get(key));
  }

  protected double parseDoubleFromMap(Map<String, String> data, String key) {
    return Double.parseDouble(data.get(key));
  }

  protected String parseStringFromMap(Map<String, String> data, String key) {
    return data.get(key);
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
    this.countState++;
  }

  /**
   * Initializes an ArrayList of ArrayLists representative of the grid
   **/
  protected List<List<Cell>> createGrid() {
    List<List<Cell>> ret = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      ArrayList<Cell> row = new ArrayList<>();
      for (int j = 0; j < columns; j++) {
        row.add(new Cell("empty", i, j));
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
    current(x,y).setState(state);
  }

  protected void replaceGrid(List<List<Cell>> newGrid){
    this.grid = newGrid;
  }

  protected List<Cell> getAllNeighbors(int x, int y) {
    List<Cell> neighbors = new ArrayList<>();
    for (int i = x -1; i <= x +1; i++){
      for (int j = y -1; j <= y +1; j++){
        if (isOutOfBounds(i,j) ){
          continue;
        }
        if (!(i== x && j==y)){
          neighbors.add(getCell(i,j));
        }
      }
    }
    return neighbors;
  }

  protected List<Cell> getHexNeighbors(int col, int row) {
    List<Cell> neighbors = new ArrayList<>();
    for (int i = col -1; i <= col +1; i++){
      for (int j = row -1; j <= row +1; j++){
        if (isOutOfBounds(i,j) ){
          System.out.println("Out of bounds:" + col+row);
          continue;
        }
        if (!(i==col+1 && j==row+1) || !(i==col+1 && j==row-1)){
          neighbors.add(getCell(i,j));
        }
      }
    }
    return neighbors;
  }

  protected List<Cell> getNeighbors(int x, int y) {
    List<Cell> neighbors = new ArrayList<>();
    for (int i = x -1; i <= x +1; i++){
      for (int j = y -1; j <= y +1; j++){
        if (isOutOfBounds(i,j) ){
          System.out.println("Out of bounds:" + x+y);
          continue;
        }
        if (i==x || j==y){
          neighbors.add(getCell(i,j));
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
}