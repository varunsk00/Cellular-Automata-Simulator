package cellsociety.Grids;

import cellsociety.Cell;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import jdk.swing.interop.SwingInterOpUtils;

public class PredPreyGrid extends Grid {

  private static final List<String> DATA_FIELDS = List.of(
      "rows",
      "columns",
      "predatorStartingEnergy",
      "predatorEnergyPerPrey",
      "preyGenerationRate",
      "predatorGenerationRate",
      "percentPredator",
      "percentPrey"
  );
  private static int predatorStartingEnergy;
  private static int predatorEnergyPerPrey;
  private static int preyGenerationRate;
  private static int predatorGenerationRate;
  private static double percentPredator;
  private static double percentPrey;
  private Random r = new Random();
  private List<Point> emptyCells;
  private List<Point> preyCells;
  private final String PREDATOR = "predator";
  private final String PREY = "prey";
  private final String EMPTY = "empty";

  public PredPreyGrid(int rows, int columns, int predatorStartingEnergy, int predatorEnergyPerPrey,
      int preyGenerationRate,
      int predatorGenerationRate, double percentPredator, double percentPrey) {
    super(rows, columns);
    this.predatorStartingEnergy = predatorStartingEnergy;
    this.predatorEnergyPerPrey = predatorEnergyPerPrey;
    this.preyGenerationRate = preyGenerationRate;
    this.predatorGenerationRate = predatorGenerationRate;
    this.percentPredator = percentPredator;
    this.percentPrey = percentPrey;
    this.emptyCells = new ArrayList<>();
    this.preyCells = new ArrayList<>();
    createGrid();
    setInits();
  }

  /**
   * @return the instance variables in our simulation
   */
  public static List<String> getDataFields() {
    return DATA_FIELDS;
  }

  @Override
  public void updateGrid() {
    storeNeighborState(emptyCells, EMPTY);
    storeNeighborState(preyCells, PREY);
//    System.out.println(predatorCells.size());
    super.updateGrid();
  }

  public PredPreyGrid(Map<String, String> dataValues) {
    this(Integer.parseInt(dataValues.get(DATA_FIELDS.get(0))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(1))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(2))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(3))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(4))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(5))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(6))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(7))));
  }

  private void setInits() {
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getColumns(); j++) {
        if (r.nextFloat() <= percentPredator) {
          resetCellToPredatorState(current(i, j));
        }
        if (r.nextFloat() <= percentPrey) {
          resetCellToPreyState(current(i, j));
        }
      }
    }
  }

  @Override
  public void updateCells(int x, int y, List<Cell> neighbors) {
    Cell currentCell = current(x, y);
    //prey can move

    if (currentCell.getState().equals(PREY) && checkNeighbors(x, y, emptyCells)) {
      handlePrey(neighbors, currentCell);
    }
  }

  @Override
  protected void handleMiddleCell(int x, int y) {
    List<Cell> neighbors = getNeighbors(x, y);
    updateCells(x, y, neighbors);
  }

  private void handlePrey(List<Cell> neighbors, Cell currentCell) {

    List<Cell> emptyNeighbors = new ArrayList<>();
    for (Cell c : neighbors) {
      if (c.getState().equals(EMPTY)) {
        emptyNeighbors.add(c);
      }
    }


    Cell newCell = emptyNeighbors.get(r.nextInt(emptyNeighbors.size()));
    moveCell(currentCell, newCell);
    newCell.updateLives(1);
    System.out.println("current lives: " + currentCell.getLives() + ", new lives: "+ newCell.getLives());
  }

  private void moveCell(Cell currentCell, Cell newCell) {
    copyCellToCell(currentCell, newCell);
    updateCellsState(currentCell);
    updateCellsState(newCell);
    if (newCell.getLives() > preyGenerationRate) {
      //System.out.println("generating a new prey");
      resetCellToPreyState(currentCell);
    } else {
      resetCellToEmpty(currentCell);
    }
  }

  private void copyCellToCell(Cell currentCell, Cell newCell) {
    newCell.updateState(currentCell.getState());
    newCell.setLives(currentCell.getLives());
  }

  private void updateCellsState(Cell cell) {
    Point coordinate = cell.getCoordinate();
    if (cell.getState().equals(EMPTY)) {
      preyCells.remove(coordinate);
      emptyCells.add(coordinate);
    }
    if (cell.getState().equals(PREY)) {
      emptyCells.remove(coordinate);
      preyCells.add(coordinate);
    }
    if (cell.getState().equals(PREDATOR)) {
      emptyCells.remove(coordinate);
    }
  }

  private void resetCellToPreyState(Cell cell) {
    cell.updateState(PREY);
    cell.setLives(1);
    updateCellsState(cell);
  }

  private void resetCellToEmpty(Cell cell) {
    cell.updateState(EMPTY);
    cell.setLives(0);
    updateCellsState(cell);
  }



  private void resetCellToPredatorState(Cell cell) {
    cell.updateState(PREDATOR);
    cell.setLives(predatorStartingEnergy);
    updateCellsState(cell);
  }
}
