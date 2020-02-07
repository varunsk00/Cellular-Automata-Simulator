package cellsociety.Grids;

import java.awt.Point;
import cellsociety.Cell;
import java.util.List;
import java.util.ArrayList;


public class Grid {

  private List<List<Cell>> grid;
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
   * @return ArrayList of ArrayLists of Cells representing Grid
   */
  public List<List<Cell>> getGrid() {
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
    return this.columns;
  }

  public Cell current(int x, int y){
      return this.grid.get(x).get(y);
  }

  /**
   * Checks every cell in the current grid and updates based on state of neighbors
   *
   * @return a grid (2D array of cells) with updated state
   */
  public void updateGrid() {
        for (List<Cell> row : getGrid()) {
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

  /**
   * Initializes an ArrayList of ArrayLists representative of the grid
   **/
  protected List<List<Cell>> createGrid() {
        List<List<Cell>> ret = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                row.add(new Cell( "empty",i,j));
            }
            ret.add(row);
        }
        return ret;
    }

  /**
   * Empty method to handle an middle cell in the grid
   * @param x the x coordinate of the cell to handle
   * @param y the y coordinate of the cell to handle
   */
  protected void handleMiddleCell(int x, int y){
      List<Cell> neighbors = getAllNeighbors(x,y);
      updateCells(x,y,neighbors);
  }

  /**
   * Empty method to handle an edge cell in the grid
   * @param x the x coordinate of the cell to handle
   * @param y the y coordinate of the cell to handle
   */
  protected void handleEdgeCell(int x, int y){
      List<Cell> neighbors = handleEdgeCases(x,y);
      updateCells(x,y,neighbors);
  }

  protected void updateCells(int x, int y, List<Cell> neighbors){
  }

  /**
   * Returns the neighboring cells of a given index that represent the cell on top, bottom, left, right
   * @param x the x coordinate of the cell
   * @param y the y coordinate of the cell
   * @return
   */
  protected List<Cell> getNeighbors(int x, int y){
    List<Cell> ret = new ArrayList<>();
    ret.add(getGrid().get(x-1).get(y));
    ret.add(getGrid().get(x+1).get(y));
    ret.add(getGrid().get(x).get(y+1));
    ret.add(getGrid().get(x).get(y-1));
    return ret;
  }

  /**
   * Returns the neighboring cells of a given index that represent the cell on top, bottom, left, right and diagonals
   * @param x the x coordinate of the cell
   * @param y the y coordinate of the cell
   * @return
   */
  protected List<Cell> getAllNeighbors(int x, int y){
    List<Cell> ret = getNeighbors(x,y);
    ret.add(getGrid().get(x-1).get(y-1));
    ret.add(getGrid().get(x-1).get(y+1));
    ret.add(getGrid().get(x+1).get(y-1));
    ret.add(getGrid().get(x+1).get(y+1));
    return ret;
  }

  /**
   * Returns a boolean representing if neighbors contains a point that is a neighbor of (x,y)
   * @param x the x coordinate of the cell to check for neighbors
   * @param y the y coordinate of the cell to check for neighbors
   * @param neighbors an List of points to check if any contain a neighboring point to (x,y)
   * @return a boolean if the list contains a neighbor or not
   */
  protected boolean checkNeighbors(int x, int y, List<Point> neighbors) {
    if (neighbors.contains(new Point(x + 1, y))) return true;
    if (neighbors.contains(new Point(x - 1, y))) return true;
    if (neighbors.contains(new Point(x, y + 1))) return true;
    if (neighbors.contains(new Point(x, y - 1))) return true;
    return false;
  }

  protected void storeNeighborState(List<Point> neighborCells, String state){
    neighborCells.clear();
    for (List<Cell> row : getGrid()) {
      for (Cell cell : row) {
        if (cell.getState().equals(state)) {
          neighborCells.add(new Point(getGrid().indexOf(row), row.indexOf(cell)));
        }
      }
    }
  }

  /**
   * Returns true if the cell at x, y is a middle cell in the grid (not on the border)
   * @param x
   * @param y
   * @return
   */
  private boolean isMiddleCell(int x, int y) {
        return x > 0 && y > 0 && x < getColumns() - 1 && y < getRows() - 1;
    }

  private List<Cell> handleEdgeCases(int x, int y){
      List<Cell> neighbors = new ArrayList<>();
        if(y==0){
            handleTopRow(x,y, neighbors);
        }
        if(y==getRows()-1){
            handleBottomRow(x,y, neighbors);
        }
        if(x==0){
            subtractLeftCol(x,y, neighbors);
        }
        if(x==getColumns()-1){
            subtractRightCol(x,y, neighbors);
        }
        return neighbors;
    }

  private void handleTopRow(int x, int y, List<Cell> neighbors){
        if(x==0){
            subtractTopLeftCorner(x,y,neighbors);
        }
        else if (x==getColumns()-1){
            subtractTopRightCorner(x,y,neighbors);
        }
        else{
            subtractTopRow(x,y,neighbors);
        }
    }

  private void subtractTopLeftCorner(int x, int y, List<Cell> neighbors){
        neighbors.add(current(x+1, y+1));
        neighbors.add(current(x, y+1));
        neighbors.add(current(x+1, y));
    }

  private void subtractTopRightCorner(int x, int y, List<Cell> neighbors){
        neighbors.add(current(x-1, y));
        neighbors.add(current(x-1, y+1));
        neighbors.add(current(x, y+1));
    }

  private void subtractTopRow(int x, int y, List<Cell> neighbors){
        neighbors.add(current(x-1, y));
        neighbors.add(current(x-1, y+1));
        neighbors.add(current(x, y+1));
        neighbors.add(current(x+1, y+1));
        neighbors.add(current(x, y+1));
        neighbors.add(current(x+1, y));
    }

  private void handleBottomRow(int x, int y, List<Cell> neighbors){
        if(x==0){
            subtractBottomLeftCorner(x,y,neighbors);
        }
        else if (x==getColumns()-1){
            subtractBottomRightCorner(x,y,neighbors);
        }
        else{
            subtractBottomRow(x,y,neighbors);
        }
    }

  private void subtractBottomLeftCorner(int x, int y, List<Cell> neighbors){
        neighbors.add(current(x,y-1));
        neighbors.add(current(x+1,y-1));
        neighbors.add(current(x+1,y));
    }

  private void subtractBottomRightCorner(int x, int y, List<Cell> neighbors){
        neighbors.add(current(x-1,y));
        neighbors.add(current(x-1,y-1));
        neighbors.add(current(x,y-1));
    }

  private void subtractBottomRow(int x, int y, List<Cell> neighbors){
        neighbors.add(current(x,y-1));
        neighbors.add(current(x+1,y-1));
        neighbors.add(current(x+1,y));
        neighbors.add(current(x-1,y));
        neighbors.add(current(x-1,y-1));
        neighbors.add(current(x,y-1));
    }

  private void subtractLeftCol(int x, int y, List<Cell> neighbors){
        if(y!=0 && y!= getRows()-1){
            neighbors.add(current(x,y-1));
            neighbors.add(current(x+1,y-1));
            neighbors.add(current(x+1,y));
            neighbors.add(current(x+1,y+1));
            neighbors.add(current(x,y+1));
        }
    }

  private void subtractRightCol(int x, int y, List<Cell> neighbors){
        if(y!=0 && y!= getRows()-1){
            neighbors.add(current(x,y-1));
            neighbors.add(current(x-1,y-1));
            neighbors.add(current(x-1,y));
            neighbors.add(current(x-1,y+1));
            neighbors.add(current(x,y+1));
        }
    }
}