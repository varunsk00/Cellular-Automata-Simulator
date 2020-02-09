package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.ResourceBundle;

/**
 * Converts a Grid object to a dynamic GridPane object that can be displayed in Main
 * GridPane is dynamic in size and changes based on changes to the size of the window
 * GridPane does not store a Grid, but future implementations could make GridPane instance variable mutable
 * to prevent clearing the GridPane on every update() call
 * @authors Eric Doppelt, Jaidha Rosenblatt
 */
public class RectGridView {

    private GridPane myGridPane;
    private ResourceBundle myResources;

    /**
     * Basic constructor for a GridView object
     * Takes no parameters but creates an instance that can be used later to call updateGrid()
     * Future variations can store a grid to the GridPane to be updated for efficiency purposes
     * Creates the GridPane and sets Horizontal and Vertical Gaps to 1
     */
    public RectGridView(String language) {
        myResources = ResourceBundle.getBundle(language);    myGridPane = new GridPane();
        myGridPane.setHgap(1);
        myGridPane.setVgap(1);
    }

    /**
     * Basic getter method to get the GridPane object
     *
     * @return myGridPane instance variable representing the last grid passed through update
     */
    public GridPane getGridPane() {
        return myGridPane;
    }

    /**
     * Method provides the functionality of the Grid View class
     * Clears instance variable myGridPane and updates it to show the grid parameter
     * Dynamically sized to fit size of center in BorderPane in Main
     * Called in Main every time the grid updates through the step() function
     *
     * @param grid takes in a grid to be represented via a GridPane
     */
    public void updateGridView(Grid grid) {

        myGridPane.getChildren().clear();

        for (int i = 0; i < grid.getColumns(); i++) {
            for (int j = 0; j < grid.getRows(); j++) {
                Region addedShape = new Region();
                Color regionColor = Color.web(myResources.getString(grid.current(j, i).getState()));
                Background regionBackground = new Background(new BackgroundFill(regionColor, CornerRadii.EMPTY, Insets.EMPTY));
                addedShape.setBackground(regionBackground);
                myGridPane.add(addedShape, i, j);
                myGridPane.setHgrow(addedShape, Priority.ALWAYS);
                myGridPane.setVgrow(addedShape, Priority.ALWAYS);
            }
        }
    }
}

