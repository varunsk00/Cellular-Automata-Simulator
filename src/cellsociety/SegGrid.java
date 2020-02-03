package cellsociety;

import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class SegGrid extends Grid {

  public static final List<String> DATA_FIELDS = List.of(
      "rows",
      "columns",
      "satisfiedThreshold",
      "percentFull"
  );

  private Random r = new Random();
  private double prob;
  private double percentFull;

  /**
   * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
   * based on given rows and columns
   *
   * @param rows    the number of rows to generate in our grid
   * @param columns the number of columns to generate in our grid
   **/
  public SegGrid(int rows, int columns, double satisfiedThreshold, double percentFull) {
    super(rows, columns);
    this.prob = satisfiedThreshold * 8;
    this.percentFull = percentFull;
    setInits();
  }

  public SegGrid(Map<String, String> dataValues) {
    this(Integer.parseInt(dataValues.get(DATA_FIELDS.get(0))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(1))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(2))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(3))));
  }

  private void setInits() {
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getColumns(); j++) {

        if (r.nextFloat() <= percentFull/2) {
          this.current(i, j).update(Color.BLUE, "X");
        }
        if (r.nextFloat() <= percentFull/2) {
          this.current(i, j).update(Color.RED, "O");
        }
      }
    }
  }

  @Override
  public ArrayList<ArrayList<Cell>> createGrid() {
    ArrayList<ArrayList<Cell>> ret = new ArrayList<>();
    for (int i = 0; i < getRows(); i++) {
      ArrayList<Cell> row = new ArrayList<>();
      for (int j = 0; j < getColumns(); j++) {
        row.add(new Cell(Color.WHITE, "empty"));
      }
      ret.add(row);
    }
    return ret;
  }

  @Override
  public void handleMiddleCell(int x, int y) {
    ArrayList<Cell> neighbors = getAllNeighbors(x, y);
    int similar_count = 0;
    for (Cell c : neighbors) {
      if (c.getState().equals(current(x, y).getState())) {
        similar_count++;
      }
    }
    if (similar_count >= prob) {
      System.out.println("satisfied: " + (x) + ", " + (y));
    } else {
      System.out.println("unsatisfied: " + (x) + ", " + (y));
      int ran_x = r.nextInt(getColumns());
      int ran_y = r.nextInt(getRows());
      while (current(ran_x, ran_y).getState().equals("empty")) {
        current(ran_x, ran_y).update(current(x, y).getColor(), current(x, y).getState());
        System.out.println("relocated to: " + (ran_x) + ", " + (y));
        ran_x = r.nextInt(getColumns());
        ran_y = r.nextInt(getRows());
      }
      current(x, y).update(Color.WHITE, "empty");
    }
  }

  @Override
  public void handleEdgeCell(int x, int y) {
    ArrayList<Cell> neighbors = new ArrayList<>();
    if (y == 0) {
      if (x == 0) {
        neighbors.add(getGrid().get(x + 1).get(y + 1));
        neighbors.add(getGrid().get(x).get(y + 1));
        neighbors.add(getGrid().get(x + 1).get(y));
      } else if (x == getColumns() - 1) {
        neighbors.add(getGrid().get(x - 1).get(y));
        neighbors.add(getGrid().get(x - 1).get(y + 1));
        neighbors.add(getGrid().get(x).get(y + 1));
      } else {
        neighbors.add(getGrid().get(x - 1).get(y));
        neighbors.add(getGrid().get(x - 1).get(y + 1));
        neighbors.add(getGrid().get(x).get(y + 1));
        neighbors.add(getGrid().get(x + 1).get(y + 1));
        neighbors.add(getGrid().get(x).get(y + 1));
        neighbors.add(getGrid().get(x + 1).get(y));
      }
    }
    if (y == getRows() - 1) {
      if (x == 0) {
        neighbors.add(getGrid().get(x).get(y - 1));
        neighbors.add(getGrid().get(x + 1).get(y - 1));
        neighbors.add(getGrid().get(x + 1).get(y));
      } else if (x == getColumns() - 1) {
        neighbors.add(getGrid().get(x - 1).get(y));
        neighbors.add(getGrid().get(x - 1).get(y - 1));
        neighbors.add(getGrid().get(x).get(y - 1));
      } else {
        neighbors.add(getGrid().get(x - 1).get(y));
        neighbors.add(getGrid().get(x - 1).get(y - 1));
        neighbors.add(getGrid().get(x).get(y - 1));
        neighbors.add(getGrid().get(x + 1).get(y - 1));
        neighbors.add(getGrid().get(x).get(y - 1));
        neighbors.add(getGrid().get(x + 1).get(y));
      }
    }
    if (x == 0) {
      if (y != 0 && y != getRows() - 1) {
        neighbors.add(getGrid().get(x).get(y - 1));
        neighbors.add(getGrid().get(x + 1).get(y - 1));
        neighbors.add(getGrid().get(x + 1).get(y));
        neighbors.add(getGrid().get(x + 1).get(y + 1));
        neighbors.add(getGrid().get(x).get(y + 1));
      }
    }
    if (x == getColumns() - 1) {
      if (y != 0 && y != getRows() - 1) {
        neighbors.add(getGrid().get(x).get(y - 1));
        neighbors.add(getGrid().get(x - 1).get(y - 1));
        neighbors.add(getGrid().get(x - 1).get(y));
        neighbors.add(getGrid().get(x - 1).get(y + 1));
        neighbors.add(getGrid().get(x).get(y + 1));
      }
    }

    int similar_count = 0;
    for (Cell c : neighbors) {
      if (c.getState().equals(current(x, y).getState())) {
        similar_count++;
      }
    }
    if (similar_count >= 4) {
      System.out.println("satisfied: " + (x) + ", " + (y));
    } else {
      System.out.println("unsatisfied: " + (x) + ", " + (y));
      int ran_x = r.nextInt(getColumns());
      int ran_y = r.nextInt(getRows());
      while (current(ran_x, ran_y).getState().equals("empty")) {
        current(ran_x, ran_y).update(current(x, y).getColor(), current(x, y).getState());
        System.out.println("relocated to: " + (ran_x) + ", " + (y));
        ran_x = r.nextInt(getColumns());
        ran_y = r.nextInt(getRows());
      }
      current(x, y).update(Color.WHITE, "empty");
    }
  }
}
