package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class SegGrid extends Grid {
    private Random r = new Random();
    private double prob = 8; //XML * 8
    /**
     * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
     * based on given rows and columns
     *
     * @param rows    the number of rows to generate in our grid
     * @param columns the number of columns to generate in our grid
     **/
    public SegGrid(int rows, int columns) {
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
    public void handleMiddleCell(int x, int y){
        ArrayList<Cell> neighbors = getAllNeighbors(x,y);
        int similar_count= 0;
        for (Cell c: neighbors){
            if (c.getState().equals(current(x,y).getState())){
                similar_count++;
            }
        }
        if (similar_count >= prob){
            System.out.println("satisfied: " + (x) + ", " + (y));
        }
        else{
            System.out.println("unsatisfied: " + (x) + ", " + (y));
            int ran_x = r.nextInt(getColumns());
            int ran_y = r.nextInt(getRows());
            while(current(ran_x,ran_y).getState().equals("empty")){
                current(ran_x,ran_y).update(current(x,y).getColor(),current(x,y).getState());
                System.out.println("relocated to: " + (ran_x) + ", " + (y));
                ran_x = r.nextInt(getColumns());
                ran_y = r.nextInt(getRows());
            }
            current(x,y).update(Color.WHITE, "empty");
        }
    }
}
