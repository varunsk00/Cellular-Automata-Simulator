package cellsociety.Visuals;
import cellsociety.Models.Grids.Grid;
import javafx.scene.layout.Region;

/**
 * Converts a Grid object to a dynamic GridPane object with Rectangle cell shapes that can be displayed in CAController
 * RectGridView extends GridView inheriting a GridPane and Map
 * The GridPane is dynamic in size and Rectangles grow or shrink to fill the size of the window
 *
 * @authors Eric Doppelt
 */
public class RectGridView extends GridView {

  /**
   * Basic constructor for a RectGridView object
   * Takes no parameters but creates an instance that can be used later to call updateGrid()
   * Creates the GridPane and sets Horizontal and Vertical Gaps to 1 to show the Grid well
   */
  public RectGridView() {
    super();
    myGridPane.setHgap(1);
    myGridPane.setVgap(1);
  }

  /**
   * Method provides the functionality of the Grid View class
   * Clears instance variable myGridPane and updates it to show the grid parameter
   * Dynamically sized to fit size of center in BorderPane in Main
   * Called in Main every time the grid updates through the step() function
   *
   * @param grid takes in a grid to be represented via a GridPane
   */
  @Override
  public void updateGridView(Grid grid) {
    setInstanceVariables(grid);

    for (int i = 0; i < grid.getColumns(); i++) {
      for (int j = 0; j < grid.getRows(); j++) {
        Region addedRect = makeRegion(grid.current(j, i).getState());
        addRegion(addedRect, i, j);
      }
    }
  }
}

