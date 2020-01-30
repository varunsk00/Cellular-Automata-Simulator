package cellsociety;

import javafx.scene.paint.Color;
import java.util.ArrayList;


public class Grid {

  /**
   * TODO Make abstract, updateGrid
   **/
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
    ArrayList<ArrayList<Cell>> newGrid = grid;
    for (ArrayList<Cell> row : newGrid) {
      for (Cell cell : row) {
        int x = grid.indexOf(row);
        int y = row.indexOf(cell);
        if (x % 2 == 0 && y % 2 != 0) {
          cell.update(Color.BLACK, "full");
        }
        if (x % 2 != 0 && y % 2 == 0) {
          cell.update(Color.BLACK, "full");
        }
      }
    }
    this.grid = newGrid;
  }
}
//  private Cell updateMiddleCell(int x, int y) {
//    Cell cell = grid.get(x).get(y);
//    String state = "burning";
//    //Check cell neighbors
//    for (int i = x - 1; i < x + 1; i++) {
//      for (int j = y - 1; j < y + 1; j++) {
//        if (getCellState(i, j) == state) {
//          cell.update(Color.RED, "burning");
//        }
//      }
//    }
//
//    return cell;
//  }

//  private Cell updateEdgeCell(int x, int y) {
//    //TODO
//    Cell cell = grid.get(x).get(y);;
//    return cell;
//  }
//
//  private String getCellState(int i, int j) {
//    Cell cell = grid.get(i).get(j);;
//    return cell.getState();
//  }