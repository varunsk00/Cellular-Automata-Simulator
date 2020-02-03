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
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getColumns(); j++) {

        if (r.nextFloat() <= percentPredator / 2) {
          this.current(i, j).update(Color.ORANGE, "predator_0");
        }
        if (r.nextFloat() <= percentPrey / 2) {
          this.current(i, j).update(Color.GREEN, "prey_0");
        }
      }
    }
  }

  @Override
  public void handleMiddleCell(int x, int y) {
    ArrayList<Cell> neighbors = getNeighbors(x, y);
    Cell currentCell = current(x, y);
    if (current(x, y).getState().contains("prey")) {
      int rng = r.nextInt(4);
      Cell neighbor = neighbors.get(rng);
      while (neighbor.getState().equals(("empty"))) {
        neighbor.update(currentCell.getColor(), updateStateString(currentCell.getState()));
        currentCell.update(Color.WHITE, "empty");
        System.out.println("updating " + x + "," + y);
      }
    }
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
}
