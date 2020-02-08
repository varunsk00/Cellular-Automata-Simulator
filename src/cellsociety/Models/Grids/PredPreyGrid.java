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
  private final String PREDATOR = "predator";
  private final String PREY = "prey";
  private final String EMPTY = "empty";



  public PredPreyGrid(Map<String, String> data) {
    super(data);
    this.predatorStartingEnergy = parseIntFromMap(data, "predatorStartingEnergy");
    this.predatorEnergyPerPrey = parseIntFromMap(data, "predatorEnergyPerPrey");
    this.preyGenerationRate = parseIntFromMap(data, "preyGenerationRate");
    this.predatorGenerationRate = parseIntFromMap(data, "predatorGenerationRate");
    this.percentPredator = parseDoubleFromMap(data, "percentPredator");
    this.percentPrey = parseDoubleFromMap(data, "percentPrey");

    createGrid();
    setInits();
  }

  @Override
  public void updateGrid() {
    super.updateGrid();
    setCellsToFutureStates();
  }

  private void setInits() {
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getColumns(); j++) {
        if (r.nextFloat() <= percentPredator) {
          setCellState(i, j, PREDATOR);
        }
        if (r.nextFloat() <= percentPrey) {
          setCellState(i, j, PREY);
        }
      }
    }
  }

  private void setCellsToFutureStates() {
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getColumns(); j++) {
        String state = current(i, j).getNextState();
        setCellState(i, j, state);
      }
    }
  }

  @Override
  public void updateCell(int x, int y, List<Cell> neighbors) {
    Cell currentCell = current(x, y);
    //prey can move

    if (currentCell.getState().equals(PREY)) {
      handlePrey(neighbors, currentCell);
    }
  }

  private void handlePrey(List<Cell> neighbors, Cell currentCell) {
    Cell newCell = returnRandomEmptyNeighbor(neighbors);
    if (newCell == null) {
      return;
    }
    moveCell(currentCell, newCell);
  }

  private Cell returnRandomEmptyNeighbor(List<Cell> neighbors) {

    List<Cell> emptyNeighbors = new ArrayList<>();
    for (Cell c : neighbors) {
      if (c.getState().equals(EMPTY) && c.getNextState().equals(EMPTY)) {
        emptyNeighbors.add(c);
      }
    }
    if (emptyNeighbors.size() == 0) {
      return null;
    }

    return emptyNeighbors.get(r.nextInt(emptyNeighbors.size()));
  }

  private void moveCell(Cell currentCell, Cell newCell) {
    newCell.setNextState(currentCell.getState());
    System.out.println("current lives: " + currentCell.getLives());
    newCell.setLives(currentCell.getLives() + 1);

    System.out.println("new lives: " + newCell.getLives());

    //Copy current cell into new position in localGrid
    if (newCell.getLives() > preyGenerationRate) {
      System.out.println("spawn");
      //spawns
      resetToPreyCell(currentCell);

    } else if (newCell.getLives() <= preyGenerationRate) {
      //resets
      resetToEmptyCell(currentCell);
    }
  }

  private void resetToPreyCell(Cell cell) {
    cell.setState(PREY);
    cell.setLives(1);
    cell.setNextState(PREY);
  }

  private void resetToEmptyCell(Cell cell) {
    cell.setState(EMPTY);
    cell.setLives(0);
    cell.setNextState(EMPTY);

  }


}
