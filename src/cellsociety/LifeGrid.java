package cellsociety;

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
}
