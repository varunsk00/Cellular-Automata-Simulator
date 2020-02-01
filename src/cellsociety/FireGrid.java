package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class FireGrid extends Grid {
    /**
     * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
     * based on given rows and columns
     *
     * @param rows    the number of rows to generate in our grid
     * @param columns the number of columns to generate in our grid
     **/
    public FireGrid(int rows, int columns) {
        super(rows, columns);
    }

    @Override
    public ArrayList<ArrayList<Cell>> createGrid() {
        ArrayList<ArrayList<Cell>> ret = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < getColumns(); j++) {
                row.add(new Cell(Color.GREEN, "tree"));
            }
            ret.add(row);
        }
        return ret;
    }
    @Override
    public void updateGrid() throws InterruptedException {
        Random r = new Random();
        ArrayList<ArrayList<Cell>> newGrid = getGrid();
        for (ArrayList<Cell> row : newGrid) {
            for (Cell cell : row) {


                int random_x = r.nextInt(getColumns());
                int random_y = r.nextInt(getRows());
                //getGrid().get(random_x).get(random_y).update(Color.RED, "burning");
                //System.out.println("lit: " + (random_x) + ", " + (random_y));
                //Thread.sleep(100);

                int x = getGrid().indexOf(row);
                int y = row.indexOf(cell);
                if(x>0 && y>0 && x<getColumns()-1 && y<getRows()-1){
                    if(newGrid.get(x).get(y+1).getState() == "burning"|| newGrid.get(x).get(y-1).getState() == "burning" ||
                            newGrid.get(x-1).get(y).getState() == "burning" || newGrid.get(x+1).get(y).getState() == "burning"){
                        cell.update(Color.RED, "burning");
                        System.out.println("caught fire: " + (x) + ", " + (y));
                    }
                }
                //
            }
        }
    }
}

