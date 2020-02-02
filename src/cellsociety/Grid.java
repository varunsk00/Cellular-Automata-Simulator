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
  public void updateGrid() throws InterruptedException {
    for (ArrayList<Cell> row : getGrid()) {
      for (Cell cell : row) {
        int x = grid.indexOf(row);
        int y = row.indexOf(cell);
        if (x >0 && y > 0 && x< getColumns() && y<getRows()) {
          handleMiddleCell(x,y);
        }
        else {
          handleEdgeCell(x,y);
        }
      }
    }
    this.grid = getGrid();
  }

  public void handleMiddleCell(int x, int y) {
  }

  public void handleEdgeCell(int x, int y) {
  }

}