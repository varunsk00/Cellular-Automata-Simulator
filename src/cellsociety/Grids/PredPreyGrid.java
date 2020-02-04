package cellsociety.Grids;

import cellsociety.Cell;
import cellsociety.Grids.Grid;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PredPreyGrid extends Grid {

  public static final List<String> DATA_FIELDS = List.of(
      "rows",
      "columns",
      "predatorEnergyPerPrey",
      "preyGenerationRate",
      "predatorGenerationRate",
      "percentPredator",
      "percentPrey"
  );

  private static int predatorEnergyPerPrey;
  private static int preyGenerationRate;
  private static int predatorGenerationRate;
  private static double percentPredator;
  private static double percentPrey;
  private Random r = new Random();

  public PredPreyGrid(int rows, int columns, int predatorEnergy, int preyGenerationRate,
      int predatorGenerationRate, double percentPredator, double percentPrey) {
    super(rows, columns);
    this.predatorEnergyPerPrey = predatorEnergy;
    this.preyGenerationRate = preyGenerationRate;
    this.predatorGenerationRate = predatorGenerationRate;
    this.percentPredator = percentPredator;
    this.percentPrey = percentPrey;
    createGrid();
    setInits();
  }

  public PredPreyGrid(Map<String, String> dataValues) {
    this(Integer.parseInt(dataValues.get(DATA_FIELDS.get(0))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(1))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(2))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(3))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(4))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(5))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(6))));
  }

  private void setInits() {
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getColumns(); j++) {
        if (r.nextFloat() <= percentPredator / 2) {
          resetCellToPredatorState(current(i, j));
        }
        if (r.nextFloat() <= percentPrey / 2) {
          resetCellToPreyState(current(i, j));
        }
      }
    }
  }

  @Override
  public void handleMiddleCell(int x, int y) {
    ArrayList<Cell> neighbors = getNeighbors(x, y);
    Cell currentCell = current(x, y);
    if (current(x, y).getState().contains("prey")) {
      handlePrey(neighbors, currentCell);
    }
    if (current(x, y).getState().contains("predator")) {
      handlePredator(neighbors, currentCell);
    }
  }

  private void handlePrey(ArrayList<Cell> neighbors, Cell currentCell) {
    //first check if there is a prey, then check if there are blank spaces
    Cell neighbor = getRandomNeighborByState(neighbors, "empty");
    if (neighbor != null) {
      moveToRandomEmptyNeighbor(neighbor, currentCell, "prey");
    }
  }

  private void handlePredator(ArrayList<Cell> neighbors, Cell currentCell) {
    if (checkPredatorDeath(currentCell)) {
      resetCellToEmpty(currentCell);
    }
    //move to fish cell
    Cell neighbor = getRandomNeighborByState(neighbors, "prey");
    if (neighbor != null) {
      predatorEatPrey(neighbor,currentCell);
    }
    //move to empty cell
    else {
      neighbor = getRandomNeighborByState(neighbors, "empty");
      if (neighbor != null) {
        moveToRandomEmptyNeighbor(neighbor, currentCell, "predator");
      }
      currentCell.update(currentCell.getColor(),updateStateString(currentCell.getState(),-1));
    }
  }

  private void moveToRandomEmptyNeighbor(Cell neighbor, Cell currentCell, String type) {
    if (neighbor == null) {
      return;
    }
    //move current cell to neighbor
    neighbor.update(currentCell.getColor(), updateStateString(currentCell.getState(), 1));
    handleReproduction(currentCell,type);
  }

  private void predatorEatPrey(Cell preyCell, Cell predatorCell) {
    if (preyCell == null) {
      return;
    }
    //move predator cell to prey cell and add energy
    preyCell.update(predatorCell.getColor(),
        updateStateString(predatorCell.getState(), predatorEnergyPerPrey));
    handleReproduction(predatorCell,"predator");
  }

  private Cell getRandomNeighborByState(ArrayList<Cell> neighbors, String state) {
    ArrayList<Cell> stateNeighbors = new ArrayList<>();
    for (Cell cell : neighbors) {
      if (cell.getState().contains(state)) {
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

  private String updateStateString(String s, int change) {
    if (!s.contains("_")) {
      return s;
    }
    String type = s.split("_")[0];
    String numString = s.split("_")[1];
    int count = Integer.parseInt(numString) + change;
    return type + "_" + count;
  }

  private void handleReproduction(Cell cell, String type) {
    String s = cell.getState();
    if(!s.contains("_")){
      return;
    }
    int count = Integer.parseInt(s.split("_")[1]);
    if (type.equals("prey") && count + 1 > preyGenerationRate) {
      System.out.println("regen prey");
      resetCellToPreyState(cell);
    } else if (type.equals("predator") && count + 1 > predatorEnergyPerPrey) {
      resetCellToPredatorState(cell);
    }
    else {
      resetCellToEmpty(cell);
    }
  }

  private Boolean checkPredatorDeath(Cell cell) {
    String s = cell.getState();
    int count = Integer.parseInt(s.split("_")[1]);
    return count < 0;
  }

  private void resetCellToPreyState(Cell cell) {
    cell.update(Color.GREEN, "prey_0");
  }

  private void resetCellToEmpty(Cell cell) {
    cell.update(Color.WHITE, "empty");
  }

  private void resetCellToPredatorState(Cell cell) {
    cell.update(Color.ORANGE, "predator_0");
  }

}
