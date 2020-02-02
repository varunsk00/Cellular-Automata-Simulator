package cellsociety;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class PercGrid extends Grid {
    /**
     * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
     * based on given rows and columns
     *
     * @param row    the number of rows to generate in our grid
     * @param column the number of columns to generate in our grid
     **/
    public PercGrid(int row, int column) {
        super(row, column);
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
        if(   ((getGrid().get(x).get(y+1).getState() == "full") ||
                getGrid().get(x).get(y-1).getState() == "full"||
                getGrid().get(x+1).get(y).getState() == "full"||
                getGrid().get(x-1).get(y).getState() == "full")){
            getGrid().get(x).get(y).update(Color.BLUE, "full");
            System.out.println("leaked: " + (x) + ", " + (y));
        }
    }
}
