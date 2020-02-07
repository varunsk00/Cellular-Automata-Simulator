package cellsociety.Models.Grids;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import cellsociety.Models.Cell;
import java.util.ArrayList;
import java.util.Random;


public class FireGrid extends Grid {

  private List<Point> burnedCells;
  private double probability;
  private Random r = new Random();
  private final String BURNING = "burning";
  private final String TREE = "tree";
  private final String EMPTY = "empty";

  /**
   * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
   * based on given rows and columns
   **/
  public FireGrid(Map<String, String> data) {
    super(data);
    this.probability = parseDoubleFromMap(data, "probCatch");
    burnedCells = new ArrayList<>();
    setBurningCell();
  }

  @Override
  public void updateGrid() {
    storeNeighborState(burnedCells, BURNING);
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
  protected void updateCells(int x, int y, List<Cell> neighbors) {
    if (current(x, y).getState().equals(BURNING)) {
      extinguishCell(x, y);
    }
    if (checkNeighbors(x, y, burnedCells) && current(x, y).getState().equals(TREE)
        && r.nextFloat() <= probability) {
      burnCell(x, y);
    }
  }

  private void setBurningCell() {
    this.getCell(this.getRows() / 2, this.getColumns() / 2).updateState(BURNING);
  }

  private void burnCell(int x, int y) {
    current(x, y).updateState(BURNING);
    System.out.println("caught fire: " + (x) + ", " + (y));
  }

  private void extinguishCell(int x, int y) {
    current(x, y).updateState(EMPTY);
    System.out.println("extinguished: " + (x) + ", " + (y));
  }
}
