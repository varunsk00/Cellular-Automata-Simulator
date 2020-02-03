package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class LifeGrid extends Grid {
    /**
     * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
     * based on given rows and columns
     *
     * @param rows    the number of rows to generate in our grid
     * @param columns the number of columns to generate in our grid
     **/
    public LifeGrid(int rows, int columns) {
        super(rows, columns);
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
    public void handleMiddleCell(int x, int y){
        ArrayList<Cell> neighbors = getAllNeighbors(x,y);
        int alive_count= 0;
        for (Cell c: neighbors){
            if (c.getState() == "alive"){
                alive_count++;
            }
        }

        if (current(x,y).getState().equals("alive")){
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

    public void handleEdgeCell(int x, int y){
        ArrayList<Cell> neighbors = new ArrayList<>();
        if(y==0){
            if(x==0){
                neighbors.add(getGrid().get(x+1).get(y+1));
                neighbors.add(getGrid().get(x).get(y+1));
                neighbors.add(getGrid().get(x+1).get(y));
            }
            else if (x==getColumns()-1){
                neighbors.add(getGrid().get(x-1).get(y));
                neighbors.add(getGrid().get(x-1).get(y+1));
                neighbors.add(getGrid().get(x).get(y+1));
            }
            else{
                neighbors.add(getGrid().get(x-1).get(y));
                neighbors.add(getGrid().get(x-1).get(y+1));
                neighbors.add(getGrid().get(x).get(y+1));
                neighbors.add(getGrid().get(x+1).get(y+1));
                neighbors.add(getGrid().get(x).get(y+1));
                neighbors.add(getGrid().get(x+1).get(y));
            }
        }
        if(y==getRows()-1){
            if(x==0){
                neighbors.add(getGrid().get(x).get(y-1));
                neighbors.add(getGrid().get(x+1).get(y-1));
                neighbors.add(getGrid().get(x+1).get(y));
            }
            else if (x==getColumns()-1){
                neighbors.add(getGrid().get(x-1).get(y));
                neighbors.add(getGrid().get(x-1).get(y-1));
                neighbors.add(getGrid().get(x).get(y-1));
            }
            else{
                neighbors.add(getGrid().get(x-1).get(y));
                neighbors.add(getGrid().get(x-1).get(y-1));
                neighbors.add(getGrid().get(x).get(y-1));
                neighbors.add(getGrid().get(x+1).get(y-1));
                neighbors.add(getGrid().get(x).get(y-1));
                neighbors.add(getGrid().get(x+1).get(y));
            }
        }
        if(x==0){
            if(y!=0 && y!= getRows()-1){
                neighbors.add(getGrid().get(x).get(y-1));
                neighbors.add(getGrid().get(x+1).get(y-1));
                neighbors.add(getGrid().get(x+1).get(y));
                neighbors.add(getGrid().get(x+1).get(y+1));
                neighbors.add(getGrid().get(x).get(y+1));
            }
        }
        if(x==getColumns()-1){
            if(y!=0 && y!= getRows()-1){
                neighbors.add(getGrid().get(x).get(y-1));
                neighbors.add(getGrid().get(x-1).get(y-1));
                neighbors.add(getGrid().get(x-1).get(y));
                neighbors.add(getGrid().get(x-1).get(y+1));
                neighbors.add(getGrid().get(x).get(y+1));
            }
        }

        int alive_count= 0;
        for (Cell c: neighbors){
            if (c.getState() == "alive"){
                alive_count++;
            }
        }
        if (current(x,y).getState().equals("alive")){
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
