package cellsociety.Grids;
import java.awt.*;
import java.util.List;
import java.util.Map;

import cellsociety.Cell;
import cellsociety.Grids.Grid;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class PercGrid extends Grid {
  // field names expected to appear in data file holding values for this object
  private static final List<String> DATA_FIELDS = List.of(
      "rows",
      "columns",
      "percentBlocked"
  );

  private ArrayList<Point> fullCells;
  private static double percentBlocked;
  private Random r = new Random();
  /**
   * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
   * based on given rows and columns
   *
   * @param row    the number of rows to generate in our grid
   * @param column the number of columns to generate in our grid
   **/
  public PercGrid(int row, int column, double percentBlocked) {
    super(row, column);
    this.percentBlocked = percentBlocked;
    this.fullCells = new ArrayList<>();
    setFullCells();
    setBlockedCells();
  }

  /**
   *
   * @return the instance variables in our simulation
   */
  public static List<String> getDataFields() {
    return DATA_FIELDS;
  }

  public PercGrid(Map<String, String> dataValues) {
    this(Integer.parseInt(dataValues.get(DATA_FIELDS.get(0))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(1))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(2))));
  }

  @Override
  public void updateGrid(){
    storeNeigborState(fullCells, "full");
    super.updateGrid();
  }

  @Override
  protected void updateCells(int x, int y, List<Cell> neighbors){
    if (isFillable(x,y)) {
      leakCell(x,y);
    }
  }

  private void setFullCells(){
    for (int i = 0; i < this.getColumns(); i++) {
      if (this.current(0, i).getState() != "blocked") {
        this.current(0, i).update(Color.BLUE, "full");
      }
    }
  }

  private void setBlockedCells(){
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getColumns(); j++) {
        if (r.nextFloat() <= percentBlocked){
          this.getGrid().get(i).get(j).update(Color.BLACK, "blocked");
        }
      }
    }
  }

  private boolean isFillable(int x, int y){
    return current(x,y).getState().equals("empty") && checkNeighbors(x, y, fullCells);
  }

  private void leakCell(int x, int y){
    current(x,y).update(Color.BLUE, "full");
    System.out.println("leaked: " + (x) + ", " + (y));
  }
}

