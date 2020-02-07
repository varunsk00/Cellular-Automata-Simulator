package cellsociety.Grids;

import cellsociety.Cell;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
  private List<Point> predatorCells;
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
    this.predatorCells = new ArrayList<>();
    createGrid();
    setInits();
  }

  /**
   *
   * @return the instance variables in our simulation
   */
  public static List<String> getDataFields() {
    return DATA_FIELDS;
  }

  @Override
  public void updateGrid() {
    storeNeigborState(emptyCells, EMPTY);
    storeNeigborState(preyCells, PREY);
    storeNeigborState(predatorCells, PREDATOR);
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
    if (current(x, y).getState().equals(PREY) && checkNeighbors(x, y, emptyCells)) {
      handlePrey(neighbors, currentCell);
    }

    if (current(x, y).getState().equals(PREDATOR)) {
      //handlePredator(x, y, neighbors, currentCell);
    }
  }

//  private void handlePredator(int x, int y, List<Cell> neighbors, Cell currentCell) {
//    System.out.println(currentCell.getLives());
//    if (currentCell.getLives() <= 0) {
//      resetCellToEmpty(currentCell);
//    }
//
//    if (checkNeighbors(x, y, preyCells)) {
//      Cell newCell = getRandomNeighborByState(neighbors, PREY);
//      if (newCell != null) {
//        //predator now in new cell
//        predatorEatPrey(newCell, currentCell);
//        newCell.updateLives(predatorEnergyPerPrey);
//      }
//    } else if (checkNeighbors(x, y, emptyCells)) {
//      Cell newCell = getRandomNeighborByState(neighbors, EMPTY);
//      if (newCell != null) {
//        //predator now in newCell
//        moveToRandomEmptyNeighbor(newCell, currentCell);
//      }
//    } else if (checkNeighbors(x,y, predatorCells)) {
//      // predator surrounded by predators
//      currentCell.updateLives(-1);
//    }
//  }

  private void handlePrey(List<Cell> neighbors, Cell currentCell) {
    List<Cell> emptyNeighbors = new ArrayList<Cell>();
    for(Cell c: neighbors) {
      if (c.getState().equals(EMPTY)) {
        emptyNeighbors.add(c);
      }
    }
    Cell newCell = emptyNeighbors.get(r.nextInt(emptyNeighbors.size()));
    moveCell(currentCell, newCell);
  }

  private void moveCell(Cell currentCell, Cell newCell){
    copyCellToCell(currentCell, newCell);
    resetCellToEmpty(currentCell);
  }


  private void copyCellToCell(Cell currentCell, Cell newCell) {
    newCell.updateState(currentCell.getState());
    newCell.setLives(currentCell.getLives());
  }

  private boolean checkCellReproduction(Cell cell) {
    String state = cell.getState();
    int lives = cell.getLives();
    System.out.println("lives: " + lives);
    return state.equals(PREY) && lives + 1 > preyGenerationRate
        || state.equals(PREDATOR) && lives + 1 > predatorGenerationRate;
  }

  private void resetCellToPreyState(Cell cell) {
    cell.updateState(PREY);
    cell.setLives(1);
  }

  private void resetCellToEmpty(Cell cell) {
    cell.updateState(EMPTY);
    cell.setLives(0);
  }

  private void resetCellToPredatorState(Cell cell) {
    cell.updateState(PREDATOR);
    cell.setLives(predatorStartingEnergy);
  }
}
