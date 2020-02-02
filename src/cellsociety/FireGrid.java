package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class FireGrid extends Grid {
    private double probability;
    ArrayList<Integer> nums = new ArrayList<>();
    /**
     * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
     * based on given rows and columns
     *
     * @param rows    the number of rows to generate in our grid
     * @param columns the number of columns to generate in our grid
     **/
    public FireGrid(int rows, int columns, double probCatch) {
        super(rows, columns);
        this.probability = probCatch;
    }

    @Override
    public ArrayList<ArrayList<Cell>> createGrid() {
        ArrayList<ArrayList<Cell>> ret = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < getColumns(); j++) {
                if (i==0||j==0||i==getRows()-1||j==getColumns()-1){
                    row.add(new Cell(Color.YELLOW, "empty"));
                }
                else {
                    row.add(new Cell(Color.GREEN, "tree"));
                }
            }
            ret.add(row);
        }
        return ret;
    }
    //TODO REFACTOR
    @Override
    public void updateGrid() throws InterruptedException {
        Random r = new Random();
        ArrayList<ArrayList<Cell>> newGrid = getGrid();
        for (ArrayList<Cell> row : newGrid) {
            for (Cell cell : row) {


                int prob = r.nextInt(10);
                for(int i = 0; i< (probability*10); i++){
                    nums.add(i);
                }
                //getGrid().get(random_x).get(random_y).update(Color.RED, "burning");
                //System.out.println("lit: " + (random_x) + ", " + (random_y));
                //Thread.sleep(100);

                int x = getGrid().indexOf(row);
                int y = row.indexOf(cell);
                if(x>0 && y>0 && x<getColumns()-1 && y<getRows()-1){
                    if(newGrid.get(x).get(y+1).getState() == "burning" && nums.contains(prob) ){
                        cell.update(Color.RED, "burning");
                        System.out.println("caught fire: " + (x) + ", " + (y));
                    }
                    if(newGrid.get(x).get(y-1).getState() == "burning" && nums.contains(prob) ){
                        cell.update(Color.RED, "burning");
                        System.out.println("caught fire: " + (x) + ", " + (y));
                    }
                    if(newGrid.get(x-1).get(y).getState() == "burning" && nums.contains(prob) ){
                        cell.update(Color.RED, "burning");
                        System.out.println("caught fire: " + (x) + ", " + (y));
                    }
                    if(newGrid.get(x+1).get(y).getState() == "burning" && nums.contains(prob) ){
                        cell.update(Color.RED, "burning");
                        System.out.println("caught fire: " + (x) + ", " + (y));
                    }
                }
                //
            }
        }
    }
}

