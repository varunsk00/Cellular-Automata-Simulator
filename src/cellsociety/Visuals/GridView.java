package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.scene.layout.GridPane;
import java.util.ResourceBundle;

public abstract class GridView {
    protected GridPane myGridPane;
    protected ResourceBundle myResources;

    public GridView() {
        myGridPane = new GridPane();
    }

    /**
     * Basic getter method to get the GridPane object
     *
     * @return myGridPane instance variable representing the last grid passed through update
     */
    public GridPane getGridPane() {
        return myGridPane;
    }

    public abstract void updateGridView(Grid grid);
}
