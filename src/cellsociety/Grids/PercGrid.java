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
  public static final List<String> DATA_FIELDS = List.of(
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
    this.fullCells = new ArrayList<Point>();
    setFullCells();
    setBlockedCells();
  }


  public PercGrid(Map<String, String> dataValues) {
    this(Integer.parseInt(dataValues.get(DATA_FIELDS.get(0))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(1))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(2))));
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
    public void updateGrid(){
        handleNeigbors(fullCells, "full");
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
      if (checkNeighbors(x, y, fullCells) && current(x,y).getState().equals("empty")) {
      current(x,y).update(Color.BLUE, "full");
      System.out.println("leaked: " + (x) + ", " + (y));
    }
  }

  //TODO: HEAVY REFACTORING!!! THIS IS DISGUSTING ATM
  @Override
  public void handleEdgeCell(int x, int y){
    if(y==0 && current(x,y).getState().equals("empty") && checkNeighbors(x, y, fullCells)){
      if(x==0){
        if(checkRight(x,y,"full") || checkDown(x,y,"full")){
          current(x,y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
      else if(x==getColumns()-1){
        if(checkLeft(x,y,"full") || checkDown(x,y,"full")){
          current(x,y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
      else{
        if(checkLeft(x,y,"full") || checkRight(x,y,"full") || checkDown(x,y,"full")){
          current(x,y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
    }
    if(y==getRows()-1 && current(x,y).getState().equals("empty") && checkNeighbors(x, y, fullCells)){
      if(x==0){
        if(checkRight(x,y,"full") || checkUp(x,y,"full")){
          current(x,y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
      else if(x==getColumns()-1){
        if(checkLeft(x,y,"full") || checkUp(x,y,"full")){
          current(x,y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
      else{
        if(checkLeft(x,y,"full") || checkRight(x,y,"full") || checkUp(x,y,"full")){
          current(x,y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
    }
    if(x==0 && getGrid().get(x).get(y).getState().equals("empty") && checkNeighbors(x, y, fullCells)){
      if(y!=0 && y!= getRows()-1){
        if(checkRight(x,y,"full") || checkDown(x,y,"full") || checkUp(x,y,"full")){
          getGrid().get(x).get(y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
    }
    if(x==getColumns()-1 && current(x,y).getState().equals("empty") && checkNeighbors(x, y, fullCells)){
      if(y!=0 && y!= getRows()-1){
        if(checkLeft(x,y,"full") || checkDown(x,y,"full") || checkUp(x,y,"full")){
          current(x,y).update(Color.BLUE, "full");
          System.out.println("leaked: " + (x) + ", " + (y));
        }
      }
    }
  }
}

