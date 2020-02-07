package cellsociety.Grids;

import cellsociety.Cell;
import java.awt.Point;
import javafx.scene.paint.Color;

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
  private final String PREDATOR_NAME = "predator";
  private final String PREY_NAME = "prey";
  private final String EMPTY_NAME = "empty";

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
    storeNeigborState(emptyCells, EMPTY_NAME);
    storeNeigborState(preyCells, PREY_NAME);
    storeNeigborState(predatorCells, PREDATOR_NAME);
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
    if (current(x, y).getState().equals(PREY_NAME) && checkNeighbors(x, y, emptyCells)) {
      handlePrey(neighbors, currentCell);
    }

    if (current(x, y).getState().equals(PREDATOR_NAME)) {
      handlePredator(x, y, neighbors, currentCell);
    }
  }

  private void handlePredator(int x, int y, List<Cell> neighbors, Cell currentCell) {
    System.out.println(currentCell.getLives());
    if (currentCell.getLives() <= 0) {
      resetCellToEmpty(currentCell);
    }

    if (checkNeighbors(x, y, preyCells)) {
      Cell newCell = getRandomNeighborByState(neighbors, PREY_NAME);
      if (newCell != null) {
        //predator now in new cell
        predatorEatPrey(newCell, currentCell);
        newCell.updateLives(predatorEnergyPerPrey);
      }
    } else if (checkNeighbors(x, y, emptyCells)) {
      Cell newCell = getRandomNeighborByState(neighbors, EMPTY_NAME);
      if (newCell != null) {
        //predator now in newCell
        moveToRandomEmptyNeighbor(newCell, currentCell);
      }
    } else if (checkNeighbors(x,y, predatorCells)) {
      // predator surrounded by predators
      currentCell.updateLives(-1);
    }
  }

  private void handlePrey(List<Cell> neighbors, Cell currentCell) {
    //first check if there is a prey, then check if there are blank spaces
    Cell newCell = getRandomNeighborByState(neighbors, EMPTY_NAME);
    if (newCell!=null){
      moveToRandomEmptyNeighbor(newCell, currentCell);
      newCell.updateLives(1);
    }
  }

  private void moveToRandomEmptyNeighbor(Cell newCell, Cell currentCell) {
    //move current cell to newCell
    copyCellToCell(newCell, currentCell);


    if (currentCell.getState().equals(PREDATOR_NAME)) {
      if (checkCellReproduction(currentCell)) {
        // spawn a new cell of that type in prevCEll
        resetCellToPredatorState(currentCell);
      } else {
        resetCellToEmpty(currentCell);
      }
      newCell.updateLives(-1);
    }
    if (currentCell.getState().equals(PREY_NAME)) {
      resetCellToPreyState(currentCell);
      if (checkCellReproduction(currentCell)) {
        // spawn a new cell of that type in prevCEll
        resetCellToPreyState(currentCell);
      } else {
        resetCellToEmpty(currentCell);
      }
      newCell.updateLives(1);
    }
  }

  private void copyCellToCell(Cell newCell, Cell prevCell) {
    newCell.update(prevCell.getColor(), prevCell.getState());
  }

  private void predatorEatPrey(Cell preyCell, Cell predatorCell) {
    copyCellToCell(preyCell, predatorCell);
    preyCell.updateLives(predatorEnergyPerPrey);
    if (checkCellReproduction(predatorCell)) {
      resetCellToPredatorState(predatorCell);
    } else {
      resetCellToEmpty(predatorCell);
    }
  }

  private Cell getRandomNeighborByState(List<Cell> neighbors, String state) {
    List<Cell> stateNeighbors = new ArrayList<>();
    for (Cell cell : neighbors) {
      if (cell.getState().equals(state)) {
        stateNeighbors.add(cell);
      }
    }
    //if no neighbors matching state
    if (stateNeighbors.size() == 0) {
      return null;
    }
    int rng = r.nextInt(stateNeighbors.size());
    return stateNeighbors.get(rng);
  }

  private boolean checkCellReproduction(Cell cell) {
    String state = cell.getState();
    int lives = cell.getLives();

    return state.equals(PREY_NAME) && lives + 1 > preyGenerationRate
        || state.equals(PREDATOR_NAME) && lives + 1 > predatorGenerationRate;
  }

  private void resetCellToPreyState(Cell cell) {
    cell.update(Color.GREEN, PREY_NAME);
    cell.setLives(1);
  }

  private void resetCellToEmpty(Cell cell) {
    cell.update(Color.WHITE, EMPTY_NAME);
    cell.setLives(0);
  }

  private void resetCellToPredatorState(Cell cell) {
    cell.update(Color.ORANGE, PREDATOR_NAME);
    cell.setLives(predatorStartingEnergy);
  }

}
