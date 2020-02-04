package cellsociety.Grids;

import cellsociety.Cell;
import java.awt.Point;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PredPreyGrid extends Grid {

  public static final List<String> DATA_FIELDS = List.of(
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
  private ArrayList<Point> emptyNeighbors;
  private ArrayList<Point> preyNeighbors;


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
    this.emptyNeighbors = new ArrayList<>();
    this.preyNeighbors = new ArrayList<>();

    createGrid();
    setInits();
  }

  @Override
  public void updateGrid() {
    storeNeigborState(emptyNeighbors, "empty");
    storeNeigborState(preyNeighbors, "prey");
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
  public void handleEdgeCell(int x, int y) {
    ArrayList<Cell> neighbors = handleEdgeCases(x, y);
    updateCell(x, y, neighbors);
  }

  @Override
  public void handleMiddleCell(int x, int y) {
    ArrayList<Cell> neighbors = getNeighbors(x, y);
    updateCell(x, y, neighbors);
  }

  private void updateCell(int x, int y, ArrayList<Cell> neighbors) {
    Cell currentCell = current(x, y);

    //prey can move
    if (current(x, y).getState().equals("prey") && checkNeighbors(x, y, emptyNeighbors)) {
      handlePrey(neighbors, currentCell);
    }

    if (current(x, y).getState().equals("predator")) {
      handlePredator(x, y, neighbors, currentCell);
    }
  }

  private void handlePredator(int x, int y, ArrayList<Cell> neighbors, Cell currentCell) {
    if (currentCell.getLives() < 0) {
      resetCellToEmpty(currentCell);
    }
    if (checkNeighbors(x, y, preyNeighbors)) {
//      System.out.println("predator eat");
      Cell neighbor = getRandomNeighborByState(neighbors, "prey");
      if (neighbor != null) {
        predatorEatPrey(neighbor, currentCell);
      }
    } else if (checkNeighbors(x, y, emptyNeighbors)) {
//      System.out.println("predator move");
      Cell neighbor = getRandomNeighborByState(neighbors, "empty");
      if (neighbor != null) {
        moveToRandomEmptyNeighbor(neighbor, currentCell);
        currentCell.updateLives(-1);
      }
    } else {
//      System.out.println("predator lose life");
      currentCell.updateLives(-1);
    }
  }

  private void handlePrey(ArrayList<Cell> neighbors, Cell currentCell) {
    //first check if there is a prey, then check if there are blank spaces
    Cell neighbor = getRandomNeighborByState(neighbors, "empty");
    if (neighbor != null) {
      moveToRandomEmptyNeighbor(neighbor, currentCell);
      neighbor.updateLives(1);
    }
  }

  private void moveToRandomEmptyNeighbor(Cell newCell, Cell prevCell) {
    //move current cell to neighbor
    copyCellToCell(newCell, prevCell);
    if (checkCellReproduction(prevCell)) {
      // spawn a new cell of that type in prevCEll
      if (prevCell.getState().equals("predator")) {
        resetCellToPredatorState(prevCell);
      }
      if (prevCell.getState().equals("prey")) {
        resetCellToPreyState(prevCell);
      }
    } else {
      resetCellToEmpty(prevCell);
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

  private boolean checkCellReproduction(Cell cell) {
    String state = cell.getState();
    int lives = cell.getLives();

    return state.equals("prey") && lives + 1 > preyGenerationRate
        || state.equals("predator") && lives + 1 > predatorGenerationRate;
  }

  private void resetCellToPreyState(Cell cell) {
    cell.update(Color.GREEN, "prey");
  }

  private void resetCellToEmpty(Cell cell) {
    cell.update(Color.WHITE, "empty");
  }

  private void resetCellToPredatorState(Cell cell) {
    cell.update(Color.ORANGE, "predator");
    cell.updateLives(predatorStartingEnergy);
  }

}
