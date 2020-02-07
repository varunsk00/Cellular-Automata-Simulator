package cellsociety.Models.Grids;
import java.awt.*;
import java.util.List;
import java.util.Map;
import cellsociety.Models.Cell;
import java.util.ArrayList;
import java.util.Random;

public class PercGrid extends Grid {
  // field names expected to appear in data file holding values for this object
  private ArrayList<Point> fullCells;
  private static double percentBlocked;
  private Random r = new Random();
  private final String FULL = "full";
  private final String BLOCKED = "blocked";
  private final String EMPTY = "empty";

  /**
   * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
   * based on given rows and columns
   *
   **/
  public PercGrid(Map<String, String> data) {
    super(data);
    this.percentBlocked = parseDoubleFromMap(data, "percentBlocked");
    this.fullCells = new ArrayList<>();
    setFullCells();
    setBlockedCells();
  }

  @Override
  public void updateGrid(){
    storeNeighborState(fullCells, FULL);
    super.updateGrid();
  }

  @Override
  protected void updateCells(int x, int y, List<Cell> neighbors){
    if (isFillable(x,y)) {
      leakCell(x,y);
    }
  }

  private void setFullCells(){
    for (int i = 0; i < this.getColumns(); i++) {
      if (this.current(0, i).getState() != BLOCKED) {
        this.current(0, i).updateState(FULL);
      }
    }
  }

  private void setBlockedCells(){
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getColumns(); j++) {
        if (r.nextFloat() <= percentBlocked){
          this.getCell(i,j).updateState(BLOCKED);
        }
      }
    }
  }

  private boolean isFillable(int x, int y){
    return current(x,y).getState().equals(EMPTY) && checkNeighbors(x, y, fullCells);
  }

  private void leakCell(int x, int y){
    current(x,y).updateState(FULL);
    System.out.println("leaked: " + (x) + ", " + (y));
  }
}

