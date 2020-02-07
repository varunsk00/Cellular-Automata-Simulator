package cellsociety.Models.Grids;

import cellsociety.Models.Cell;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PredPreyGrid extends Grid {

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

  public PredPreyGrid(Map<String, String> data) {
    super(data);
    this.predatorStartingEnergy = parseIntFromMap(data,"predatorStartingEnergy");
    this.predatorEnergyPerPrey = parseIntFromMap(data,"predatorEnergyPerPrey");
    this.preyGenerationRate = parseIntFromMap(data,"preyGenerationRate");
    this.predatorGenerationRate = parseIntFromMap(data,"predatorGenerationRate");
    this.percentPredator = parseDoubleFromMap(data,"percentPredator");
    this.percentPrey = parseDoubleFromMap(data,"percentPrey");;
    this.emptyCells = new ArrayList<>();
    this.preyCells = new ArrayList<>();
    createGrid();
    setInits();
  }

  @Override
  public void updateGrid() {
    storeNeighborState(emptyCells, EMPTY);
    storeNeighborState(preyCells, PREY);
//    System.out.println(predatorCells.size());
    super.updateGrid();
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
//    newCell.setLives(currentCell.getLives()+1);
//    System.out.println(
//        currentCell.getCoordinate().toString() + "current lives: " + currentCell.getLives() + ", "
//            + currentCell.getCoordinate().toString() + "new lives: " + newCell.getLives());
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
    newCell.setLives(currentCell.getLives() + 1);
    System.out.println(
        currentCell.getCoordinate().toString() + "current lives: " + currentCell.getLives() + ", "
            + newCell.getCoordinate().toString() + "new lives: " + newCell.getLives());
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
