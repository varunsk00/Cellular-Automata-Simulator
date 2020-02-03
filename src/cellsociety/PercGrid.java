package cellsociety;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class PercGrid extends Grid {

  private Random r = new Random();

  /**
   * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
   * based on given rows and columns
   *
   * @param rows           the number of rows to generate in our grid
   * @param columns        the number of columns to generate in our grid
   */
  public PercGrid(int rows, int columns, double percentBlocked) {
    super(rows, columns);
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
    if (getGrid().get(x).get(y).getState() == "empty" && checkNeighbors(x, y, "full")) {
      getGrid().get(x).get(y).update(Color.BLUE, "full");
      System.out.println("leaked: " + (x) + ", " + (y));
    }
  }

  //TODO: HEAVY REFACTORING!!! THIS IS DISGUSTING ATM
  @Override
  public void handleEdgeCell(int x, int y) {
    if (y == 0 && getGrid().get(x).get(y).getState() == "empty") {
      if (x == 0) {
        if (checkRight(x, y, "full") || checkDown(x, y, "full")) {
          getGrid().get(x).get(y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      } else if (x == getColumns() - 1) {
        if (checkLeft(x, y, "full") || checkDown(x, y, "full")) {
          getGrid().get(x).get(y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      } else {
        if (checkLeft(x, y, "full") || checkRight(x, y, "full") || checkDown(x, y, "full")) {
          getGrid().get(x).get(y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
    }
    if (y == getRows() - 1 && getGrid().get(x).get(y).getState() == "empty") {
      if (x == 0) {
        if (checkRight(x, y, "full") || checkUp(x, y, "full")) {
          getGrid().get(x).get(y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      } else if (x == getColumns() - 1) {
        if (checkLeft(x, y, "full") || checkUp(x, y, "full")) {
          getGrid().get(x).get(y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      } else {
        if (checkLeft(x, y, "full") || checkRight(x, y, "full") || checkUp(x, y, "full")) {
          getGrid().get(x).get(y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
    }
    if (x == 0 && getGrid().get(x).get(y).getState() == "empty") {
      if (y != 0 && y != getRows() - 1) {
        if (checkRight(x, y, "full") || checkDown(x, y, "full") || checkUp(x, y, "full")) {
          getGrid().get(x).get(y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
    }
    if (x == getColumns() - 1 && getGrid().get(x).get(y).getState() == "empty") {
      if (y != 0 && y != getRows() - 1) {
        if (checkLeft(x, y, "full") || checkDown(x, y, "full") || checkUp(x, y, "full")) {
          getGrid().get(x).get(y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
    }
  }
}

