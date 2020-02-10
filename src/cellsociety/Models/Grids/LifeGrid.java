package cellsociety.Models.Grids;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cellsociety.Models.Cells.*;
import java.util.ArrayList;

/**
 * This class simulates the Game of Life
 *
 * @author Varun Kosgi
 * @author Jaidha Rosenblatt
 */
public class LifeGrid extends Grid {

  private Random r = new Random();
  private ArrayList<Point> aliveCells;
  private static double percentAlive;
  private static final List<String> states = List.of("dead", "alive");
  /*
  These two strings are commented out because of an unknown bug related
  to the parsing of the List. The strings are instead hard-coded.
   */
  //  private final String DEAD = states.get(0);
  //  private final String ALIVE = states.get(1);
  private final String DEAD = "dead";
  private final String ALIVE = "alive";

  /**
   * Constructs a new Game of Life Simulation
   *
   * @param data      map for this simulation's specific variables
   * @param cellTypes map from state to colors
   * @param details   miscellaneous grid information, such as authors, titles, gridtype, etc.
   * @param layout    map from Cell states to points, if null -> random generated initial state
   */
  public LifeGrid(Map<String, Double> data, Map<String, String> cellTypes,
      Map<String, String> details, Map<String, Point> layout) {
    super(data, cellTypes, details, states);
    this.percentAlive = getDoubleFromData(data, "percentAlive");
    this.aliveCells = new ArrayList<>();
    setLayout(layout);
  }

  /**
   * Overrides updateGrid() method to store the coordinates of Cells that are alive
   */
  @Override
  public void updateGrid() {
    storeCellsByState(aliveCells, ALIVE);
    super.updateGrid();
  }

  @Override
  protected List<List<Cell>> createGrid() {
    List<List<Cell>> ret = new ArrayList<>();
    for (int i = 0; i < getRows(); i++) {
      List<Cell> row = new ArrayList<>();
      for (int j = 0; j < getColumns(); j++) {
        row.add(new Cell(DEAD, j, i));
      }
      ret.add(row);
    }
    return ret;
  }

  @Override
  protected void updateCell(int x, int y, List<Cell> neighbors) {
    int aliveCount = 0;
    for (Cell c : neighbors) {
      if (c.getState() == ALIVE) {
        aliveCount++;
      }
    }
    if (current(x, y).getState().equals(ALIVE) && checkNeighbors(x, y, aliveCells)) {
      if (aliveCount == 2 || aliveCount == 3) {
        surviveCell(x, y);
      } else {
        killCell(x, y);
      }
    } else {
      if (aliveCount == 3) {
        surviveCell(x, y);
      } else {
        killCell(x, y);
      }
    }
  }

  private void setLayout(Map<String, Point> layout) {
    if (layout == null) {
      setLocalInitState();
    } else {
      setInitState(layout);
    }
  }

  private void setLocalInitState() {
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getColumns(); j++) {
        if (r.nextFloat() <= percentAlive) {
          this.getCell(i, j).setState(ALIVE);
        }
      }
    }
  }

  private void killCell(int x, int y) {
    current(x, y).setState(DEAD);
    System.out.println("died: " + (x) + ", " + (y));
  }

  private void surviveCell(int x, int y) {
    current(x, y).setState(ALIVE);
    System.out.println("survives: " + (x) + ", " + (y));
  }
}
