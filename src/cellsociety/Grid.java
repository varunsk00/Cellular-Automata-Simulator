package cellsociety;
import javafx.scene.paint.Color;


public class Grid {

  /**
   * TODO Make abstract, updateGrid
   **/
  private Cell[][] grid;
  private int rows;
  private int columns;

  /**
   * @param rows    the number of rows to generate in our grid
   * @param columns the number of columns to generate in our grid Sets rows and columns and instance
   *                variables Calls createGrid to initialize a grid of cells based on given rows and
   *                columns
   **/
  public Grid(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    this.grid = createGrid();
  }

  private Cell[][] createGrid() {
    Cell[][] grid = new Cell[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        // temporary code to show that the grid is working
        if (i % 2 == 0) {
          grid[i][j] = new Cell(Color.BLACK, "water");
        } else {
          grid[i][j] = new Cell(Color.WHITE, "empty");
        }
      }
    }
    return grid;
  }

  /**
   * @returna 2D array Cells
   */
  public Cell[][] getGrid() {
    return this.grid;
  }

  /**
   * @return the the number of rows in our grid
   */
  public int getRows(){
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
   **/
  public Cell[][] updateGrid() {
    int rows = grid.length;
    int columns = grid[0].length;
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
    Cell cell = grid[x][y];
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
    Cell cell = grid[x][y];
    return cell;
  }

  private String getCellState(int i, int j) {
    Cell cell = grid[i][j];
    return cell.getState();
  }

}
