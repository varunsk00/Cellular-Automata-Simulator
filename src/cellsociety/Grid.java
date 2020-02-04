package cellsociety;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Grid {

  private ArrayList<ArrayList<Cell>> grid;
  private int rows;
  private int columns;

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
  }

  /**
   * Initializes an ArrayList of ArrayLists representative of the grid
   **/
  public ArrayList<ArrayList<Cell>> createGrid() {
    ArrayList<ArrayList<Cell>> ret = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      ArrayList<Cell> row = new ArrayList<>();
      for (int j = 0; j < columns; j++) {
        row.add(new Cell(Color.WHITE, "empty"));
      }
      ret.add(row);
    }
    return ret;
  }

  /**
   * @return 2D array Cells
   */
  public ArrayList<ArrayList<Cell>> getGrid() {
    return this.grid;
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
    return columns;
  }

  /**
   * Checks every cell in the current grid and updates based on state of neighbors
   *
   * @return a grid (2D array of cells) with updated state
   */
  public void updateGrid() {
    for (ArrayList<Cell> row : getGrid()) {
      for (Cell cell : row) {
        int x = grid.indexOf(row);
        int y = row.indexOf(cell);
        if (isMiddleCell(x, y)) {
          handleMiddleCell(x, y);
        } else {
          handleEdgeCell(x, y);
        }
      }
    }
    this.grid = getGrid();
  }

  public void handleMiddleCell(int x, int y) {
  }

  public void handleEdgeCell(int x, int y) {
  }

  public boolean isMiddleCell(int x, int y) {
    return x > 0 && y > 0 && x < getColumns() - 1 && y < getRows() - 1;
  }

  public boolean checkLeft(int x, int y, String state) {
    return getGrid().get(x - 1).get(y).getState() == state;
  }

  public boolean checkRight(int x, int y, String state) {
    return getGrid().get(x + 1).get(y).getState() == state;
  }

  public boolean checkUp(int x, int y, String state) {
    return getGrid().get(x).get(y - 1).getState() == state;
  }

  public boolean checkDown(int x, int y, String state) {
    return getGrid().get(x).get(y + 1).getState() == state;
  }

  public ArrayList<Cell> getNeighbors(int x, int y){
    ArrayList<Cell> ret = new ArrayList<>();
    ret.add(getGrid().get(x-1).get(y));
    ret.add(getGrid().get(x+1).get(y));
    ret.add(getGrid().get(x).get(y+1));
    ret.add(getGrid().get(x).get(y-1));
    return ret;
  }

  public ArrayList<Cell> getAllNeighbors(int x, int y){
    ArrayList<Cell> ret = new ArrayList<>();
    ret.add(getGrid().get(x-1).get(y));
    ret.add(getGrid().get(x+1).get(y));
    ret.add(getGrid().get(x).get(y+1));
    ret.add(getGrid().get(x).get(y-1));
    ret.add(getGrid().get(x-1).get(y-1));
    ret.add(getGrid().get(x-1).get(y+1));
    ret.add(getGrid().get(x+1).get(y-1));
    ret.add(getGrid().get(x+1).get(y+1));
    return ret;
  }

  public boolean checkNeighbors(int x, int y, ArrayList<Point> neighbors) {
    if (neighbors.contains(new Point(x + 1, y))) return true;
    if (neighbors.contains(new Point(x - 1, y))) return true;
    if (neighbors.contains(new Point(x, y + 1))) return true;
    if (neighbors.contains(new Point(x, y - 1))) return true;
    return false;
  }

  public Cell current(int x, int y){
    return this.grid.get(x).get(y);
  }

  public void handleNeigbors(ArrayList<Point> neighborCells, String state){
    neighborCells.clear();
    for (ArrayList<Cell> row : getGrid()) {
      for (Cell cell : row) {
        if (cell.getState().equals(state)) {
          neighborCells.add(new Point(getGrid().indexOf(row), row.indexOf(cell)));
        }
      }
    }
  }

}