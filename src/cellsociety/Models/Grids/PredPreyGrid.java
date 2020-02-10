package cellsociety.Models.Grids;

import cellsociety.Models.Cells.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class simulates the interaction between Predators and Prey
 *
 * @author Jaidha Rosenblatt
 */
public class PredPreyGrid extends Grid {
    private static int predatorStartingEnergy;
    private static int predatorEnergyPerPrey;
    private static int preyGenerationRate;
    private static int predatorGenerationRate;
    private static double percentPredator;
    private static double percentPrey;
    private static final List<String> states = List.of("predator", "prey", "empty");
    private static final String PREDATOR = states.get(0);
    private static final String PREY = states.get(1);
    private static final String EMPTY = states.get(2);
    private Random r = new Random();

  /**
   * Constructs a new PredPrey Simulation
   * @param data map for this simulation's specific variables
   * @param cellTypes map from state to colors
   * @param details miscellaneous grid information, such as authors, titles, gridtype, etc.
   * @param layout map from Cell states to points, if null -> random generated initial state
   */
    public PredPreyGrid(Map<String, Double> data, Map<String, String> cellTypes, Map<String, String> details, Map<String, Point> layout) {
      super(data, cellTypes, details, states);
      this.predatorStartingEnergy = getIntFromData(data, "predatorStartingEnergy");
      this.predatorEnergyPerPrey = getIntFromData(data, "predatorEnergyPerPrey");
      this.preyGenerationRate = getIntFromData(data, "preyGenerationRate");
      this.predatorGenerationRate = getIntFromData(data, "predatorGenerationRate");
      this.percentPredator = getDoubleFromData(data, "percentPredator");
      this.percentPrey = getDoubleFromData(data,"percentPrey");
      setLayout(layout);
    }

    /**
     * Overrides updateGrid() method to specify four neighbors
     */
    @Override
    public void updateGrid() {
      for (int i = 0; i < getRows(); i++) {
        for (int j = 0; j < getColumns(); j++) {
          List<Cell> neighbors = getNeighbors(i, j);
          updateCell(i, j, neighbors);
        }
      }
      setCellsToFutureStates();
      numIterations++;
    }

    @Override
    protected List<List<Cell>> createGrid() {
      List<List<Cell>> ret = new ArrayList<>();
      for (int i = 0; i < getRows(); i++) {
        ArrayList<Cell> row = new ArrayList<>();
        for (int j = 0; j < getColumns(); j++) {
          row.add(new PredPreyCell("empty", j, i));
        }
        ret.add(row);
      }
      return ret;
    }

    @Override
    protected void updateCell(int x, int y, List<Cell> neighbors) {
      PredPreyCell currentCell = (PredPreyCell) current(x, y);
      //prey can move
      if (currentCell.getState().equals(PREDATOR) && currentCell.getNextState().equals(PREDATOR)) {
        handlePredator(neighbors, currentCell);
      }
      if (currentCell.getState().equals(PREY) && currentCell.getNextState().equals(PREY)) {
        handlePrey(neighbors, currentCell);
      }
    }

    private void setLayout(Map<String, Point> layout) {
      if (layout == null){
        setLocalInitsState();
      }
      else{
        setInitState(layout);
      }
    }

    private void setLocalInitsState() {
      for (int i = 0; i < getRows(); i++) {
        for (int j = 0; j < getColumns(); j++) {
          PredPreyCell current = (PredPreyCell) current(i,j);
          if (r.nextFloat() <= percentPredator) {
            setCellState(i, j, PREDATOR);
            current.setNextState(PREDATOR);
            current.updateLives(predatorStartingEnergy);
          }
          if (r.nextFloat() <= percentPrey) {
            setCellState(i, j, PREY);
            current(i, j).setNextState(PREY);
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

    private void handlePrey(List<Cell> neighbors, PredPreyCell currentCell) {
      PredPreyCell newCell = returnRandomNeighborByState(neighbors, EMPTY);
      if (newCell == null) {
        return;
      }
      movePrey(currentCell, newCell);
    }

    private void handlePredator(List<Cell> neighbors, PredPreyCell currentCell) {
      PredPreyCell newCell = returnRandomNeighborByState(neighbors, PREY);
      //if there is a prey in neighbors
      if (newCell != null) {
        predatorEatPrey(currentCell, newCell);
        return;
      }
      //if there are empty neighbors
      newCell = returnRandomNeighborByState(neighbors, EMPTY);
      if (newCell != null) {
        movePredator(currentCell, newCell);
      }
      //there are no empty or prey neighbors, stay in same position and lose life
      else {
        currentCell.updateLives(-1);
        if (currentCell.getLives() < 0) {
          resetToEmptyCell(currentCell);
        }
      }
    }

    private PredPreyCell returnRandomNeighborByState(List<Cell> neighbors, String state) {
      List<Cell> emptyNeighbors = new ArrayList<>();
      for (Cell c : neighbors) {
        if (c.getState().equals(state) && c.getNextState().equals(state)) {
          emptyNeighbors.add(c);
        }
      }
      if (emptyNeighbors.size() == 0) {
        return null;
      }
      return (PredPreyCell) emptyNeighbors.get(r.nextInt(emptyNeighbors.size()));
    }

    private void movePredator(PredPreyCell currentCell, PredPreyCell newCell) {
      newCell.setNextState(currentCell.getState());
      newCell.setLives(currentCell.getLives() - 1);
      resetToEmptyCell(currentCell);
      if (newCell.getLives() < 0) {
        resetToEmptyCell(newCell);
      }
    }

    private void predatorEatPrey(PredPreyCell currentCell, PredPreyCell newCell) {
      newCell.setNextState(PREDATOR);
      newCell.setLives(currentCell.getLives() + predatorEnergyPerPrey);

      if (newCell.getLives() > predatorGenerationRate) {
        resetToPredatorCell(currentCell);
      }
      else
      {
        resetToEmptyCell(currentCell);
      }
    }

    private void movePrey(PredPreyCell currentCell, PredPreyCell newCell) {
      newCell.setNextState(currentCell.getState());
      newCell.setLives(currentCell.getLives() + 1);
      if (newCell.getLives() > preyGenerationRate) {
        resetToPreyCell(currentCell);
      }
      else
      {
        resetToEmptyCell(currentCell);
      }
    }

    private void resetToPreyCell(PredPreyCell cell) {
      cell.setState(PREY);
      cell.setNextState(PREY);
      cell.setLives(1);
    }

    private void resetToEmptyCell(PredPreyCell cell) {
      cell.setState(EMPTY);
      cell.setNextState(EMPTY);
      cell.setLives(0);
    }

    private void resetToPredatorCell(PredPreyCell cell) {
      cell.setState(PREDATOR);
      cell.setNextState(PREDATOR);
      cell.setLives(predatorStartingEnergy);
    }
}
