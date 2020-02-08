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
  private List<List<Cell>> newGrid;
  private List<Point> emptyCells;
  private List<Point> preyCells;


  public PredPreyGrid(Map<String, String> data) {
    super(data);
    this.predatorStartingEnergy = parseIntFromMap(data, "predatorStartingEnergy");
    this.predatorEnergyPerPrey = parseIntFromMap(data, "predatorEnergyPerPrey");
    this.preyGenerationRate = parseIntFromMap(data, "preyGenerationRate");
    this.predatorGenerationRate = parseIntFromMap(data, "predatorGenerationRate");
    this.percentPredator = parseDoubleFromMap(data, "percentPredator");
    this.percentPrey = parseDoubleFromMap(data, "percentPrey");
    this.emptyCells = new ArrayList<>();
    this.preyCells = new ArrayList<>();

    createGrid();
    setInits();
  }

  @Override
  public void updateGrid() {
    newGrid = this.createGrid();
    super.updateGrid();
    replaceGrid(newGrid);
  }

  private void setInits() {
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getColumns(); j++) {
        if (r.nextFloat() <= percentPredator) {
          setCell(newPredatorCell(i, j));
        }
        if (r.nextFloat() <= percentPrey) {
          setCell(newPreyCell(i, j));
        }
      }
    }
  }

  @Override
  public void updateCell(int x, int y, List<Cell> neighbors) {
    storeNeighborState(emptyCells, EMPTY);
    storeNeighborState(preyCells, PREY);
    Cell currentCell = current(x, y);
    //prey can move

    if (currentCell.getState().equals(PREY)) {
      handlePrey(neighbors, currentCell);
    }
  }

  private void handlePrey(List<Cell> neighbors, Cell currentCell) {
    Point newCell = returnNeighborByState(neighbors);
    if (newCell==null){
      System.out.println("no neighbor");
      return;
    }
    moveCell(currentCell, newCell);
  }


  private Point returnNeighborByState(List<Cell> neighbors) {
    System.out.println(preyCells);
    System.out.println("size of preys" + preyCells.size());
    System.out.println("size of empties" + emptyCells.size());

    List<Cell> emptyNeighbors = new ArrayList<>();
    for (Cell c : neighbors) {
      if (c.getState().equals(EMPTY) && emptyCells.contains(c.getCoordinate())) {
        emptyNeighbors.add(c);
      }
    }
    if (emptyNeighbors.size() == 0) {
      return null;
    }

    return emptyNeighbors.get(r.nextInt(emptyNeighbors.size())).getCoordinate();
  }

  private void moveCell(Cell currentCell, Point newCoordinate) {
    int newX = newCoordinate.x;
    int newY = newCoordinate.y;
    int currentX = currentCell.getX();
    int currentY = currentCell.getY();

    currentCell.updateLives(1);
    System.out.println(currentCell.getLives());

    //Copy current cell into new position in localGrid
    changeLocalGrid(currentCell, newX, newY);
    System.out.println("moving");
    if (currentCell.getLives() > preyGenerationRate) {
      //spawns
      System.out.println("should be spawning");
      Cell cell = newPreyCell(currentX, currentY);
      changeLocalGrid(cell, currentX, currentY);

    } else if(currentCell.getLives()<= preyGenerationRate) {
      //resets
      System.out.println("resetting cell at" + currentCell.getCoordinate());
      Cell cell = newEmptyCell(currentX, currentY);
      changeLocalGrid(cell, currentX, currentY);
    }

  }

  private void changeLocalGrid(Cell cell, int x, int y) {
    List<Cell> row = newGrid.get(x);
    row.set(y, cell);
    newGrid.set(x, row);
  }


  private Cell newPreyCell(int x, int y) {
    Cell cell = new Cell(PREY, x, y);
    cell.setLives(1);
    return cell;
  }

  private Cell newEmptyCell(int x, int y) {
    Cell cell = new Cell(EMPTY, x, y);
    cell.setLives(0);
    return cell;
  }

  private Cell newPredatorCell(int x, int y) {
    Cell cell = new Cell(PREDATOR, x, y);
    return cell;
  }

}
