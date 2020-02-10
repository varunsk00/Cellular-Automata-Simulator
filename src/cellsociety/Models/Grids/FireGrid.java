package cellsociety.Models.Grids;

import cellsociety.Controllers.xml.XMLException;
import java.awt.Point;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import cellsociety.Models.Cell;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public class FireGrid extends Grid {

  private List<Point> burnedCells;
  private double probability;
  private Random r = new Random();
  private static final List<String> states = List.of("burning","tree","empty");
//  private final String BURNING = states.get(0);
//  private final String TREE = states.get(1);
//  private final String EMPTY = states.get(2);
  private final String BURNING = "burning";
  private final String TREE = "tree";
  private final String EMPTY = "empty";


  /**
   * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
   * based on given rows and columns
   **/
  public FireGrid(Map<String, Double> data, Map<String, String> cellTypes) {
    super(data, cellTypes, states);
    this.probability = getDoubleFromData(data, "probCatch");
    burnedCells = new ArrayList<>();
    setBurningCell();
  }

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
          row.add(new Cell(EMPTY, i, j));
        } else {
          row.add(new Cell(TREE, i, j));
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

  private void setBurningCell() {
    this.getCell(this.getRows() / 2, this.getColumns() / 2).setState(BURNING);
  }

  private void burnCell(int x, int y) {
    current(x, y).setState(BURNING);
    System.out.println("caught fire: " + (x) + ", " + (y));
  }

  private void extinguishCell(int x, int y) {
    current(x, y).setState(EMPTY);
    System.out.println("extinguished: " + (x) + ", " + (y));
  }
}
