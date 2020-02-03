package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PredPreyGrid extends Grid {

  public static final List<String> DATA_FIELDS = List.of(
      "rows",
      "columns",
      "predatorEnergy",
      "preyGenerationRate",
      "predatorGenerationRate",
      "percentPredator",
      "percentPrey"
  );

  private static int predatorEnergy;
  private static int preyGenerationRate;
  private static int predatorGenerationRate;
  private static double percentPredator;
  private static double percentPrey;
  private Random r = new Random();

  public PredPreyGrid(int rows, int columns, int predatorEnergy, int preyGenerationRate,
      int predatorGenerationRate, double percentPredator, double percentPrey) {
    super(rows, columns);
    this.predatorEnergy = predatorEnergy;
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
          resetPredatorState(current(i, j));
        }
        if (r.nextFloat() <= percentPrey / 2) {
          resetPreyState(current(i, j));
        }
      }
    }
  }

  @Override
  public void handleMiddleCell(int x, int y) {
    ArrayList<Cell> neighbors = getNeighbors(x, y);
    Cell currentCell = current(x, y);
    if (current(x, y).getState().contains("prey")) {
      moveToRandomNeighbor(neighbors, currentCell);
    }
  }

  private void moveToRandomNeighbor(ArrayList<Cell> neighbors, Cell currentCell) {
    Cell neighbor = getRandomBlankNeighbor(neighbors);
    if (neighbor==null){
      return;
    }
    neighbor.update(currentCell.getColor(), updateStateString(currentCell.getState()));
    if (checkPreyReproduction(currentCell)) {
      resetPreyState(currentCell);
      System.out.println("Spawning new cell: " + neighbor.getState() + ", old cell: " +currentCell.getState());
    } else {
      currentCell.update(Color.WHITE, "empty");
    }

  }

  private Cell getRandomBlankNeighbor(ArrayList<Cell> neighbors) {
    ArrayList<Cell> blankNeighbors = new ArrayList<>();
    for (Cell cell: neighbors){
      if (cell.getState().equals("empty")){
        blankNeighbors.add(cell);
      }
    }
    //if no blankNeighbors
    if (blankNeighbors.size()==0){
      return null;
    }
    int rng = r.nextInt(blankNeighbors.size());
    return blankNeighbors.get(rng);
  }

  private String updateStateString(String s) {
    if (!s.contains("_")) {
      return s;
    }
    String type = s.split("_")[0];
    String numString = s.split("_")[1];
    int count = Integer.parseInt(numString) + 1;
    return type + "_" + count;
  }

  private Boolean checkPreyReproduction(Cell cell) {
    String s = cell.getState();
    int count = Integer.parseInt(s.split("_")[1]) + 1;
    return count + 1 > preyGenerationRate;
  }

  private void resetPreyState(Cell cell) {
    cell.update(Color.GREEN, "prey_0");
  }

  private void resetPredatorState(Cell cell) {
    cell.update(Color.ORANGE, "predator_0");
  }

}
