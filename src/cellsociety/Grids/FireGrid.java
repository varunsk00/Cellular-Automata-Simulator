package cellsociety.Grids;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import cellsociety.Cell;
import java.util.ArrayList;
import java.util.Random;


public class FireGrid extends Grid {

  // field names expected to appear in data file holding values for this object
  private static final List<String> DATA_FIELDS = List.of(
      "rows",
      "columns",
      "probCatch"
  );

  private List<Point> burnedCells;
  private double probability;
  private Random r = new Random();
  private final String BURNING = "burning";
  private final String TREE = "tree";
  private final String EMPTY = "empty";

  /**
   * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
   * based on given rows and columns
   *
   * @param rows    the number of rows to generate in our grid
   * @param columns the number of columns to generate in our grid
   **/
  public FireGrid(int rows, int columns, double probCatch) {
    super(rows, columns);
    this.probability = probCatch;
    burnedCells = new ArrayList<>();
    setBurningCell();
  }

  /**
   *
   * @return the instance variables in our simulation
   */
  public static List<String> getDataFields() {
    return DATA_FIELDS;
  }

  public FireGrid(Map<String, String> dataValues) {
    this(Integer.parseInt(dataValues.get(DATA_FIELDS.get(0))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(1))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(2))));
  }

  @Override
  public void updateGrid() {
    storeNeigborState(burnedCells, BURNING);
    super.updateGrid();
  }

  @Override
  protected List<List<Cell>> createGrid() {
    List<List<Cell>> ret = new ArrayList<>();
    for (int i = 0; i < getRows(); i++) {
      List<Cell> row = new ArrayList<>();
      for (int j = 0; j < getColumns(); j++) {
        if (i == 0 || j == 0 || i == getRows() - 1 || j == getColumns() - 1) {
          row.add(new Cell(EMPTY));
        } else {
          row.add(new Cell(TREE));
        }
      }
      ret.add(row);
    }
    return ret;
  }

  @Override
  protected void updateCells(int x, int y, List<Cell> neighbors){
    if (current(x, y).getState().equals(BURNING)) {
      extinguishCell(x,y);
    }
    if (checkNeighbors(x, y, burnedCells) && current(x, y).getState().equals(TREE)
            && r.nextFloat() <= probability) {
      burnCell(x,y);
    }
  }

  private void setBurningCell() {
    this.getGrid().get(this.getRows() / 2).get(this.getColumns() / 2)
            .updateState(BURNING);
  }

  private void burnCell(int x, int y){
    current(x, y).updateState(BURNING);
    System.out.println("caught fire: " + (x) + ", " + (y));
  }

  private void extinguishCell(int x, int y){
    current(x, y).updateState(EMPTY);
    System.out.println("extinguished: " + (x) + ", " + (y));
  }
}
