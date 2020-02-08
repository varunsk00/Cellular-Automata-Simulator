package cellsociety.Models.Grids;

import cellsociety.Models.Cell;
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
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getColumns(); j++) {
        List<Cell> neighbors = getNeighbors(i, j);
        updateCell(i, j, neighbors);
      }
    }
    setCellsToFutureStates();
  }

  private void setInits() {
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getColumns(); j++) {
        if (r.nextFloat() <= percentPredator) {
          setCellState(i, j, PREDATOR);
          current(i,j).setNextState(PREDATOR);
          current(i, j).updateLives(predatorStartingEnergy);
        }
        if (r.nextFloat() <= percentPrey) {
          setCellState(i, j, PREY);
          current(i,j).setNextState(PREY);
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
    if (currentCell.getState().equals(PREDATOR) && currentCell.getNextState().equals(PREDATOR)) {
      handlePredator(neighbors, currentCell);
    }
    if (currentCell.getState().equals(PREY) && currentCell.getNextState().equals(PREY)) {
      handlePrey(neighbors, currentCell);
    }

  }

  private void handlePrey(List<Cell> neighbors, Cell currentCell) {
    Cell newCell = returnRandomNeighborByState(neighbors, EMPTY);
    if (newCell == null) {
      return;
    }
    movePrey(currentCell, newCell);
  }

  private void handlePredator(List<Cell> neighbors, Cell currentCell) {
    System.out.println("lives: " + currentCell.getLives());

    Cell newCell = returnRandomNeighborByState(neighbors, PREY);
    //if there is a prey in neighbors
    if (newCell != null) {
      predatorEatPrey(currentCell, newCell);
      return;
    }
    newCell = returnRandomNeighborByState(neighbors, EMPTY);
    if (newCell != null) {
      newCell.setNextState(currentCell.getState());
      newCell.setLives(currentCell.getLives() - 1);
      resetToEmptyCell(currentCell);
      if (newCell.getLives()<0){
        System.out.println("Death by moving");
        resetToEmptyCell(newCell);
      }
    }
    //if move or stand still, decrease lives by 1
    else {
      currentCell.updateLives(- 1);
      if (currentCell.getLives()<0){
        System.out.println("Death by still");
        resetToEmptyCell(currentCell);
      }
    }
  }

  private Cell returnRandomNeighborByState(List<Cell> neighbors, String state) {
    List<Cell> emptyNeighbors = new ArrayList<>();
    for (Cell c : neighbors) {
      if (c.getState().equals(state) && c.getNextState().equals(state)) {
        emptyNeighbors.add(c);
      }
    }
    if (emptyNeighbors.size() == 0) {
      return null;
    }
    return emptyNeighbors.get(r.nextInt(emptyNeighbors.size()));
  }

  private void predatorEatPrey(Cell currentCell, Cell newCell) {
    newCell.setNextState(PREDATOR);
    newCell.setLives(currentCell.getLives() + predatorEnergyPerPrey);

    if (newCell.getLives() > predatorGenerationRate) {
      //spawns
      resetToPredatorCell(currentCell);
    } else if (newCell.getLives() <= predatorGenerationRate) {
      //resets
      resetToEmptyCell(currentCell);
    }


  }

  private void movePrey(Cell currentCell, Cell newCell) {
    newCell.setNextState(currentCell.getState());
    newCell.setLives(currentCell.getLives() + 1);

    //Copy current cell into new position in localGrid
    if (newCell.getLives() > preyGenerationRate) {
      //spawns
      resetToPreyCell(currentCell);

    } else if (newCell.getLives() <= preyGenerationRate) {
      //resets
      resetToEmptyCell(currentCell);
    }
  }

  private void resetToPreyCell(Cell cell) {
    cell.setState(PREY);
    cell.setNextState(PREY);
    cell.setLives(1);
  }

  private void resetToEmptyCell(Cell cell) {
    cell.setState(EMPTY);
    cell.setNextState(EMPTY);
    cell.setLives(0);
  }

  private void resetToPredatorCell(Cell cell) {
    cell.setState(PREDATOR);
    cell.setNextState(PREDATOR);
    cell.setLives(predatorStartingEnergy);
  }


}
