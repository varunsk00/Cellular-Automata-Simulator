package cellsociety.Models.Grids;

import java.awt.*;
import java.util.List;
import java.util.Map;
import cellsociety.Models.Cells.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class simulates Percolation in a grid
 *
 * @author Varun Kosgi
 * @author Jaidha Rosenblatt
 */
public class PercGrid extends Grid {

  private ArrayList<Point> fullCells;
  private static double percentBlocked;
  private Random r = new Random();
  private static final List<String> states = List.of("full", "blocked", "empty");
  private final String FULL = states.get(0);
  private final String BLOCKED = states.get(1);
  private final String EMPTY = states.get(2);

  /**
   * Constructs a new Percolation Simulation
   *
   * @param data      map for this simulation's specific variables
   * @param cellTypes map from state to colors
   * @param details   miscellaneous grid information, such as authors, titles, gridtype, etc.
   * @param layout    map from Cell states to points, if null -> random generated initial state
   */
  public PercGrid(Map<String, Double> data, Map<String, String> cellTypes,
      Map<String, String> details, Map<String, Point> layout) {
    super(data, cellTypes, details, states);
    this.percentBlocked = getDoubleFromData(data, "percentBlocked");
    this.fullCells = new ArrayList<>();
    setLayout(layout);
  }

  /**
   * Overrides updateGrid() method to store the coordinates of Cells that are full
   */
  @Override
  public void updateGrid() {
    storeCellsByState(fullCells, FULL);
    super.updateGrid();
  }

  private void setLayout(Map<String, Point> layout) {
    if (layout == null) {
      setLocalInitState();
    } else {
      setInitState(layout);
    }
  }

  private void setLocalInitState() {
    setFullCells();
    setBlockedCells();
  }

  @Override
  protected void updateCell(int x, int y, List<Cell> neighbors) {
    if (isFillable(x, y)) {
      leakCell(x, y);
    }
  }

  private void setFullCells() {
    for (int i = 0; i < this.getColumns(); i++) {
      if (this.current(0, i).getState() != BLOCKED) {
        this.setCellState(0, i, FULL);
      }
    }
  }

  private void setBlockedCells() {
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getColumns(); j++) {
        if (r.nextFloat() <= percentBlocked) {
          setCellState(i, j, BLOCKED);
        }
      }
    }
  }

  private boolean isFillable(int x, int y) {
    return current(x, y).getState().equals(EMPTY) && checkNeighbors(x, y, fullCells);
  }

  private void leakCell(int x, int y) {
    setCellState(x, y, FULL);
    System.out.println("leaked: " + (x) + ", " + (y));
  }
}

