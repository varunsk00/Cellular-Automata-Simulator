package cellsociety;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class PercGrid extends Grid {
    private Random r = new Random();
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
        if (current(x,y).getState().equals("empty")  && checkNeighbors(x,y,"full")) {
            current(x,y).update(Color.BLUE, "full");
            System.out.println("leaked: " + (x) + ", " + (y));
        }
    }

    //TODO: HEAVY REFACTORING!!! THIS IS DISGUSTING ATM
    @Override
    public void handleEdgeCell(int x, int y){
        if(y==0 && current(x,y).getState().equals("empty")){
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
        if(y==getRows()-1 && current(x,y).getState().equals("empty")){
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
        if(x==0 && getGrid().get(x).get(y).getState().equals("empty")){
            if(y!=0 && y!= getRows()-1){
                if(checkRight(x,y,"full") || checkDown(x,y,"full") || checkUp(x,y,"full")){
                    getGrid().get(x).get(y).update(Color.BLUE, "full");
                    System.out.println("leaked: " + (x) + ", " + (y));
                }
            }
        }
        if(x==getColumns()-1 && current(x,y).getState().equals("empty")){
            if(y!=0 && y!= getRows()-1){
                if(checkLeft(x,y,"full") || checkDown(x,y,"full") || checkUp(x,y,"full")){
                    current(x,y).update(Color.BLUE, "full");
                    System.out.println("leaked: " + (x) + ", " + (y));
                }
            }
        }
    }
}

