package cellsociety.Models.Grids;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import cellsociety.Models.Cells.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class simulates the spread of wildfire
 *
 * @author Varun Kosgi
 * @author Jaidha Rosenblatt
 */
public class FireGrid extends Grid {

  private List<Point> burnedCells;
  private static double probability;
  private Random r = new Random();
  private static final List<String> states = List.of("burning", "tree", "empty");
  /*
  These three strings are commented out because of an unknown bug related
  to the parsing of the List and the word "tree." The strings are instead hard-coded.
   */
  //  private final String BURNING = states.get(0);
  //  private final String TREE = states.get(1);
  //  private final String EMPTY = states.get(2);
  private final String BURNING = "burning";
  private final String TREE = "tree";
  private final String EMPTY = "empty";

  /**
   * Constructs a new Fire Simulation
   *
   * @param data      map for this simulation's specific variables
   * @param cellTypes map from state to colors
   * @param details   miscellaneous grid information, such as authors, titles, gridtype, etc.
   * @param layout    map from Cell states to points, if null -> random generated initial state
   */
  public FireGrid(Map<String, Double> data, Map<String, String> cellTypes,
      Map<String, String> details, Map<String, Point> layout) {
    super(data, cellTypes, details, states);
    this.probability = getDoubleFromData(data, "probCatch");
    burnedCells = new ArrayList<>();
    setLayout(layout);
  }

  /**
   * Overrides updateGrid() method to store the coordinates of Cells that are burning
   */
  @Override
  public void updateGrid() {
    storeCellsByState(burnedCells, BURNING);
    super.updateGrid();
  }

  @Override
  protected List<List<Cell>> createGrid() {
    List<List<Cell>> ret = new ArrayList<>();
    for (int i = 0; i < getRows(); i++) {
      List<Cell> row = new ArrayList<>();
      for (int j = 0; j < getColumns(); j++) {
        if (i == 0 || j == 0 || i == getRows() - 1 || j == getColumns() - 1) {
          row.add(new Cell(EMPTY, j, i));
        } else {
          row.add(new Cell(TREE, j, i));
        }
      }
      ret.add(row);
    }
    return ret;
  }

  @Override
  protected void updateCell(int x, int y, List<Cell> neighbors) {
    if (current(x, y).getState().equals(BURNING)) {
      extinguishCell(x, y);
    }
    if (checkNeighbors(x, y, burnedCells) && current(x, y).getState().equals(TREE)
        && r.nextFloat() <= probability) {
      burnCell(x, y);
    }
  }

  private void setLayout(Map<String, Point> layout) {
    if (layout == null) {
      setBurningCell();
    } else {
      setInitState(layout);
    }
  }

  private void setBurningCell() {
    this.getCell(this.getRows() / 2, this.getColumns() / 2).setState(BURNING);
  }

  private void burnCell(int x, int y) {
    setCellState(x, y, BURNING);
    System.out.println("caught fire: " + (x) + ", " + (y));
  }

  private void extinguishCell(int x, int y) {
    setCellState(x, y, EMPTY);
    System.out.println("extinguished: " + (x) + ", " + (y));
  }
}
