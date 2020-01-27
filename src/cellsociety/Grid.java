package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Collection;

public class Grid {
    /**
     * TODO Make abstract, updateGrid
     **/
    private Cell[][] grid;
    private int rows;
    private int columns;

    public Grid(int rows, int columns){
        /**
         * @param rows the number of rows to generate in our grid
         * @param columns the number of columns to generate in our grid
         * Sets rows and columns and instance variables
         * Calls createGrid to initialize a grid of cells based on given rows and columns
         **/
        this.rows = rows;
        this.columns = columns;
        this.grid = createGrid();
    }

    private Cell[][] createGrid(){
        Cell[][] grid = new Cell[rows][columns];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                // temporary code to show that the grid is working
                if (i%2==0){
                    grid[i][j] = new Cell(Color.BLACK, "water" );
                }
                else{
                    grid[i][j] = new Cell(Color.WHITE, "empty" );
                }
            }
        }
        return grid;
    }

    public Scene getScene(double sceneWidth, double sceneHeight){
        /**
         * @param sceneWidth the width of the scene we want to return
         * @param sceneHeight the height of the scene we want to return
         * Calls renderGrid() to turn grid into a collection of shape objects
         * that we can add to the root of our scene
         * @return a scene that is a rendered version of our grid
         **/
        Group root = new Group();

        double cellWidth = sceneWidth/columns;
        double cellHeight = sceneHeight/rows;

        Collection<Shape> cells = renderGrid(cellWidth,cellHeight);
        root.getChildren().addAll(cells);

        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        return scene;
    }

    private Collection<Shape> renderGrid(double cellWidth, double cellHeight){
        Collection<Shape> cells = new ArrayList<>();
        for (int i = 0; i < grid.length; i++){
            Cell[] column = grid[i];
            for (int j = 0; j < column.length; j++){
                Cell cell = grid[i][j];
                Shape shape = new Rectangle(i*cellWidth,j*cellHeight, cellWidth, cellHeight);
                shape.setFill(cell.getColor());
                cells.add(shape);
            }
        }
        return cells;
    }

    public Cell[][] getGrid(){
        /**
         * @return a 2D array Cells
         **/
        return this.grid;
    }

    public Cell[][] updateGrid(){
        /**
         * Checks every cell in the current grid and updates based on state of neighbors
         **/
        int rows = grid.length;
        int columns = grid[0].length;
        Cell[][] newGrid = new Cell[rows][columns];

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                //TODO is a try/catch loop better?
                if (i > 0 && i < rows - 1 && j > 0 && j < columns - 1){
                    updateMiddleCell(i, j);
                }
                else{
                    updateEdgeCell(i,j);
                }
            }
        }
        return newGrid;
    }

    private Cell updateMiddleCell(int x, int y){
        Cell cell = grid[x][y];
        String state = "burning";
        //Check cell neighbors
        for (int i = x-1; i < x+1; i++){
            for (int j = y-1; j < y+1; j++){
                if (getCellState(i,j)==state){
                    cell.update(Color.RED, "burning");
                }
            }
        }
        return cell;
    }

    private Cell updateEdgeCell(int x, int y){
        //TODO
        Cell cell = grid[x][y];
        return cell;
    }

    private String getCellState(int i, int j){
        Cell cell = grid[i][j];
        return cell.getState();
    }

}
