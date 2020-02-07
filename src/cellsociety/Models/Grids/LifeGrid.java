package cellsociety.Models.Grids;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cellsociety.Models.Cell;
import java.util.ArrayList;

public class LifeGrid extends Grid {
  private Random r = new Random();
  private ArrayList<Point> aliveCells;
  private static double percentAlive;
  private final String DEAD = "dead";
  private final String ALIVE = "alive";

  /**
     * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
     * based on given rows and columns
     *
     **/
  public LifeGrid(Map<String, String> data) {
        super(data);
        this.percentAlive = parseDoubleFromMap(data,"percentAlive");
        this.aliveCells = new ArrayList<>();
        setAliveCells();
    }

  @Override
  public void updateGrid(){
      storeNeighborState(aliveCells, ALIVE);
      super.updateGrid();
  }

  @Override
  protected List<List<Cell>> createGrid() {
      List<List<Cell>> ret = new ArrayList<>();
      for (int i = 0; i < getRows(); i++) {
          List<Cell> row = new ArrayList<>();
          for (int j = 0; j < getColumns(); j++) {
              row.add(new Cell(DEAD,i,j));
          }
          ret.add(row);
      }
      return ret;
  }

  @Override
  protected void updateCells(int x, int y, List<Cell> neighbors){
        int alive_count= 0;
        for (Cell c: neighbors){
            if (c.getState() == "alive"){
                alive_count++;
            }
        }
        if (current(x,y).getState().equals("alive") && checkNeighbors(x, y, aliveCells)){
            if(alive_count==2 || alive_count ==3){
                surviveCell(x,y);
            }
            else{
                killCell(x,y);
            }
        }
        else{
            if (alive_count==3){
                surviveCell(x,y);
            }
            else{
                killCell(x,y);
            }
        }
    }

  private void setAliveCells(){
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                if (r.nextFloat() <= percentAlive){
                    this.getCell(i,j).updateState(ALIVE);
                }
            }
        }
    }

  private void killCell(int x, int y){
      current(x,y).updateState(DEAD);
      System.out.println("died: " + (x) + ", " + (y));
  }

  private void surviveCell(int x, int y){
      current(x,y).updateState(ALIVE);
      System.out.println("survives: " + (x) + ", " + (y));
  }
}
