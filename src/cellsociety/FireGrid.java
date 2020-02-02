package cellsociety;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class FireGrid extends Grid {
    private double probability;
    private ArrayList<Integer> nums = new ArrayList<>();
    private int prob;
    private Random r = new Random();
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
        for (int i = 0; i < (probability * 10); i++) {
            this.nums.add(i);
        }
    }

    @Override
    public ArrayList<ArrayList<Cell>> createGrid() {
        ArrayList<ArrayList<Cell>> ret = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < getColumns(); j++) {
                if (i == 0 || j == 0 || i == getRows() - 1 || j == getColumns() - 1){
                    row.add(new Cell(Color.YELLOW, "empty"));
                }
                else{
                    row.add(new Cell(Color.GREEN, "tree"));
                }
            }
            ret.add(row);
        }
        return ret;
    }

    @Override
    public void updateGrid() {
        for (ArrayList<Cell> row : getGrid()) {
            for (Cell cell : row) {
                int x = getGrid().indexOf(row);
                int y = row.indexOf(cell);
                if (x > 0 && y > 0 && x < getColumns() - 1 && y < getRows() - 1) {
                    handleBurningCell(x, y);
                    handleMiddleCell(x, y);
                }
            }
        }
    }

    @Override
    public void handleMiddleCell(int x, int y){
        prob = r.nextInt(10);
        if (checkNeighbors(x,y,"burning") && getGrid().get(x).get(y).getState() == "tree" && nums.contains(prob)){
            getGrid().get(x).get(y).update(Color.RED, "burning");
            System.out.println("caught fire: " + (x) + ", " + (y));
        }
    }

    public void handleBurningCell(int x, int y){
        if (getGrid().get(x).get(y).getState() == "burning") {
            getGrid().get(x).get(y).update(Color.YELLOW, "empty");
            System.out.println("extinguished: " + (x) + ", " + (y));
        }
    }
}
