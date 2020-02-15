package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Map;

/**
 * Abstract GridView class that creates a basic GridPane layout to be filled with CellShapes in GridView subclasses
 * Class holds GridPane and Map instance variables
 * Shape of Node is implemented in Subclasses
 * @author Eric Doppelt
 */
public abstract class GridView {

  protected GridPane myGridPane;
  protected Map<String, String> myStates;

  /**
   * Basic constructor instantiates GridPane
   */
  public GridView() {
    myGridPane = new GridPane();
  }

  /**
   * Basic getter method to get the GridPane object
   *
   * @return myGridPane instance variable representing the last grid passed through update
   */
  public Pane getGridPane() {
    return myGridPane;
  }

  /**
   * Abstract method used to update the GridPane by making it match the grid passed in
   * Implementation in subclasses differs due to the shape of the cell
   *
   * @param grid is Grid object to convert to a GridPane representation
   */
  public abstract void updateGridView(Grid grid);

  protected void makeNodeDynamic(Node region) {
    myGridPane.setHgrow(region, Priority.ALWAYS);
    myGridPane.setVgrow(region, Priority.ALWAYS);
  }

  protected void setInstanceVariables(Grid grid) {
      myGridPane.getChildren().clear();
      myStates = grid.getStateMap();
  }

  protected Region makeRegion(String state)  {
    Color regionColor = Color.web(myStates.get(state));
    Region returnedRegion = new Region();
    Background regionBackground = new Background(
            new BackgroundFill(regionColor, CornerRadii.EMPTY, Insets.EMPTY));
    returnedRegion.setBackground(regionBackground);
    return returnedRegion;
  }

  protected void addRegion(Region addedRegion, int i, int j) {
    myGridPane.add(addedRegion, i, j);
    makeNodeDynamic(addedRegion);
  }
}
