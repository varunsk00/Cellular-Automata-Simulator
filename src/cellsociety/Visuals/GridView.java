package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

import java.util.ResourceBundle;

public abstract class GridView {
    protected GridPane myGridPane;
    protected ResourceBundle myResources;

    public GridView(String language) {
        System.out.println("called");
        myGridPane = new GridPane();
        myResources = ResourceBundle.getBundle(language);
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
