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
    this.grid = new ArrayList<>();
    createGrid();
  }

  /**
   * Initializes an ArrayList of ArrayLists representative of the grid
   **/
  private void createGrid() {
    for(int i = 0; i<rows; i++){
      ArrayList<Cell> row = new ArrayList<>();
      for(int j = 0; j<columns; j++){
        if (i % 2 == 0) {
          System.out.println("full");
          row.add(new Cell(Color.BLACK, "water"));
        }
        else{
          System.out.println("empty");
          row.add(new Cell(Color.WHITE, "empty"));
        }
      }
      grid.add(row);
    }
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
   * @return a grid (2D array of cells) with updated state
   */
  public Cell[][] updateGrid() {
    int rows = grid.size();
    int columns = grid.get(0).size();
    Cell[][] newGrid = new Cell[rows][columns];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        //TODO is a try/catch loop better?
        if (i > 0 && i < rows - 1 && j > 0 && j < columns - 1) {
          updateMiddleCell(i, j);
        } else {
          updateEdgeCell(i, j);
        }
      }
    }
    return newGrid;
  }

  private Cell updateMiddleCell(int x, int y) {
    Cell cell = grid.get(x).get(y);
    String state = "burning";
    //Check cell neighbors
    for (int i = x - 1; i < x + 1; i++) {
      for (int j = y - 1; j < y + 1; j++) {
        if (getCellState(i, j) == state) {
          cell.update(Color.RED, "burning");
        }
      }
    }

    return cell;
  }

  private Cell updateEdgeCell(int x, int y) {
    //TODO
    Cell cell = grid.get(x).get(y);;
    return cell;
  }

  private String getCellState(int i, int j) {
    Cell cell = grid.get(i).get(j);;
    return cell.getState();
  }

}
