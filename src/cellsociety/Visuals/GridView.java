package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.scene.layout.GridPane;

/**
 * Abstract GridView class that creates a basic GridPane layout to be filled with CellShapes in GridView subclasses
 * Class only creates GridPane instance variable, a getter method, and makes updateGridView mandatory in subclasses
 * @author Eric Doppelt
 */
public abstract class GridView {
    protected GridPane myGridPane;

    /**
     * Basic constructor instantiates GridPane
     */
    public GridView() {
        myGridPane = new GridPane();
    }

    /**
     * Basic getter method to get the GridPane object
     * @return myGridPane instance variable representing the last grid passed through update
     */
    public GridPane getGridPane() {
        return myGridPane;
    }

    /**
     * Abstract class used to update the GridPane by making it match the grid passed in
     * Implementation in subclasses differs due to the shape of the cell
     * @param grid is Grid object to convert to a GridPane representation
     */
    public abstract void updateGridView(Grid grid);
}
