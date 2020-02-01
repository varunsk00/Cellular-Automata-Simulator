package cellsociety;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class PercGrid extends Grid{
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
    public void updateGrid() throws InterruptedException {
        Random r = new Random();
        ArrayList<ArrayList<Cell>> newGrid = getGrid();
        for (ArrayList<Cell> row : newGrid) {
            for (Cell cell : row) {


                int random_x = r.nextInt(getColumns());
                int random_y = r.nextInt(getRows());
                getGrid().get(random_x).get(random_y).update(Color.BLUE, "full");
//                System.out.println("opened: " + (random_x) + ", " + (random_y));

                int x = getGrid().indexOf(row);
                int y = row.indexOf(cell);
                if(x>0 && y>0 && x<99 && y<99){
                    if(newGrid.get(x).get(y+1).getState() == "full"|| newGrid.get(x).get(y-1).getState() == "full" ||
                            newGrid.get(x-1).get(y).getState() == "full" || newGrid.get(x+1).get(y).getState() == "full"){
                        //cell.update(Color.BLUE, "full");
                        //System.out.println("leak: " + (x) + ", " + (y));
                    }
                }
                //Thread.sleep(100);
            }
        }
    }
}

//        grid.get(50).get(50).update(Color.BLUE, "full");
//        newGrid.get(50).get(50).update(Color.BLUE, "full");

