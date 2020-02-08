package cellsociety.Models.Grids;

import java.awt.*;
import java.util.List;
import java.util.Map;
import cellsociety.Models.Cell;
import java.util.ArrayList;
import java.util.Random;

public class SegGrid extends Grid {

  private Random r = new Random();
  private double prob;
  private double percentFull;
  private ArrayList<Point> sameCells;
  private final String X = "X";
  private final String O = "O";
  private final String EMPTY = "empty";


  /**
   * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
   * based on given rows and columns
   *
   **/
  public SegGrid(Map<String, String> data) {
    super(data);
    this.prob = parseDoubleFromMap(data,"satisfiedThreshold") * 8;
    this.percentFull = parseDoubleFromMap(data, "percentFull");
    this.sameCells = new ArrayList<>();
    setInits();
  }


  @Override
  public void updateGrid() {
    int x = 0;
    int y = 0;
    storeNeighborState(sameCells, getCell(x,y).getState());
    super.updateGrid();
  }

  @Override
  protected void updateCell(int x, int y, List<Cell> neighbors) {
    int similar_count = 0;
    for (Cell c : neighbors) {
      if (c.getState().equals(current(x, y).getState())) {
        similar_count++;
      }
    }
    if (similar_count >= prob) {
      System.out.println("satisfied: " + (x) + ", " + (y));
    } else {
      System.out.println("unsatisfied: " + (x) + ", " + (y));
      int ran_x = r.nextInt(getColumns());
      int ran_y = r.nextInt(getRows());
      while (current(ran_x, ran_y).getState().equals(EMPTY)) {
        current(ran_x, ran_y).updateState(current(x, y).getState());
        System.out.println("relocated to: " + (ran_x) + ", " + (y));
        ran_x = r.nextInt(getColumns());
        ran_y = r.nextInt(getRows());
      }
      current(x, y).updateState(EMPTY);
    }
  }

  private void setInits() {
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getColumns(); j++) {

        if (r.nextFloat() <= percentFull / 2) {
          this.current(i, j).updateState(X);
        }
        if (r.nextFloat() <= percentFull / 2) {
          this.current(i, j).updateState(O);
        }
      }
    }
  }
}
