package cellsociety.Grids;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cellsociety.Cell;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class LifeGrid extends Grid {
  private static final List<String> DATA_FIELDS = List.of(
      "rows",
      "columns",
      "percentAlive"
  );
  private Random r = new Random();
  private ArrayList<Point> aliveCells;
  private static double percentAlive;

  /**
     * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
     * based on given rows and columns
     *
     * @param rows    the number of rows to generate in our grid
     * @param columns the number of columns to generate in our grid
     **/
  public LifeGrid(int rows, int columns, double percentAlive) {
        super(rows, columns);
        this.percentAlive = percentAlive;
        this.aliveCells = new ArrayList<Point>();
        setAliveCells();
    }

  public LifeGrid(Map<String, String> dataValues) {
    this(Integer.parseInt(dataValues.get(DATA_FIELDS.get(0))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(1))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(2))));
  }

  /**
   *
   * @return the instance variables in our simulation
   */
  public static List<String> getDataFields() {
    return DATA_FIELDS;
  }

  private void setAliveCells(){
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getColumns(); j++) {
        if (r.nextFloat() <= percentAlive){
          this.getGrid().get(i).get(j).update(Color.BLACK, "alive");
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
              row.add(new Cell(Color.WHITE, "dead"));
          }
          ret.add(row);
      }
      return ret;
  }

  @Override
  public void updateGrid(){
      storeNeigborState(aliveCells, "alive");
      for (ArrayList<Cell> row : getGrid()) {
          for (Cell cell : row) {
              int x = getGrid().indexOf(row);
              int y = row.indexOf(cell);
              if (isMiddleCell(x, y)) {
                  handleMiddleCell(x, y);
              } else {
                  handleEdgeCell(x, y);
              }
          }
      }
  }

  @Override
  public void handleMiddleCell(int x, int y){
      ArrayList<Cell> neighbors = getAllNeighbors(x,y);
      updateCells(x,y,neighbors);
  }

  public void handleEdgeCell(int x, int y){
      ArrayList<Cell> neighbors = handleEdgeCases(x,y);
      updateCells(x,y,neighbors);
  }

  public void updateCells(int x, int y, ArrayList<Cell> neighbors){
        int alive_count= 0;
        for (Cell c: neighbors){
            if (c.getState() == "alive"){
                alive_count++;
            }
        }
        if (checkNeighbors(x, y, aliveCells) && current(x,y).getState().equals("alive")){
            if(alive_count==2 || alive_count ==3){
                current(x,y).update(Color.BLACK, "alive");
                System.out.println("survived: " + (x) + ", " + (y));
            }
            else{
                current(x,y).update(Color.WHITE, "dead");
                System.out.println("died: " + (x) + ", " + (y));
            }
        }
        else{
            if (alive_count==3){
                current(x,y).update(Color.BLACK, "alive");
                System.out.println("reproduced: " + (x) + ", " + (y));
            }
            else{
                current(x,y).update(Color.WHITE, "dead");
            }
        }
    }
}
