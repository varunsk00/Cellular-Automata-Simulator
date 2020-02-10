package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.Map;

/**
 * SimulationView encapsulates a StatView and GridView object to create a representation of a
 * simulation StatView is stacked on top of a GridView via a VBox which is dynamic and fills the
 * size of the screen Dependent on StatView and GridView class
 *
 * @author Eric Doppelt
 */
public class SimulationView {

  private VBox mySimulation;
  private StatView myStatView;
  private GridView myGridView;

  /**
   * Constructor for a SimulationView that creates the VBox and adds the StatView and GridView to
   * it
   *
   * @param author is the author of the simulation displayed to the user in the StatView
   * @param title  is the title of the simulation displayed to the user in the StatView
   * @param type   is the type of the simulation used in the backend
   * @param stats  are the current count of each state in the simulation displayed to the user in
   *               the StatView
   */
  public SimulationView(String author, String title, String type, Map stats) {
    mySimulation = new VBox();
    setStatView(title, author, stats);
    setGridView(type);
  }

  /**
   * Getter method to return the VBox holding the StatView and GridView Called in CAController
   *
   * @return VBox holding all the information for the simulation
   */
  public VBox getSimulationView() {
    return mySimulation;
  }

  /**
   * Setter method to create the visual representation of the Grid Calls updateGridView in GridView
   * to update the GridPane Calls .updateStats in StatView to display the current count for each
   * type of state
   *
   * @param grid is the grid to update the SimulationView to represent
   */
  public void updateGridView(Grid grid) {
    myGridView.updateGridView(grid);
    myStatView.updateStats(grid.getStats());
  }

  private void setStatView(String title, String author, Map stats) {
    myStatView = new StatView(title, author, stats);
    mySimulation.getChildren().add(myStatView.getStatBox());
  }

  private void setGridView(String type) {
    switch (type) {
      case "rectangle":
        myGridView = new RectGridView();
        break;
      case "hexagon":
        myGridView = new HexGridView();
        break;
    }
    mySimulation.getChildren().add(myGridView.getGridPane());
    mySimulation.setVgrow(myGridView.getGridPane(), Priority.ALWAYS);
  }
}
