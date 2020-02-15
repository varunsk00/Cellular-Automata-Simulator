package cellsociety.Visuals;

import cellsociety.Models.Cells.Cell;
import cellsociety.Models.Grids.Grid;
import javafx.scene.layout.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * Converts a Grid object to a dynamic GridPane object with Hexagon cell shapes that can be displayed in CAController
 * HexGridView extends GridView inheriting a GridPane that becomes filled with offset Hexagons and a Map told hold the colors for each state
 * Hexagons are broken up into two halves to allow an offset grid
 * The GridPane is dynamic in size and changes to fill the size of the window
 *
 * @author Eric Doppelt
 */
public class HexGridView extends GridView {

  private static final Shape myRightHex = getRightHex();
  private static final Shape myLeftHex = getLeftHex();

  /**
   * Basic constructor for a GridView object
   * Takes no parameters and simply calls the super to instantiate the GridPane
   */
  public HexGridView() {
    super();
  }

  /**
   * Method provides the functionality of the Grid View subclass
   * Clears instance variable myGridPane and updates it to show the grid parameter
   * Stores colors from the grid in myStates Map
   * Dynamically sized to fit size of center in BorderPane in Main
   * Called in Main every time the grid updates through the step() function
   *
   * @param grid takes in a grid to be represented via a GridPane
   */
  @Override
  public void updateGridView(Grid grid) {

    setInstanceVariables(grid);

    boolean rowHasFrontBuffer = false;

    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getColumns(); j++) {
        if (j == 0 && i % 2 != 0) {
          addBuffer(i, j);
          rowHasFrontBuffer = true;
        }

        addHexagon(grid.current(i, j), rowHasFrontBuffer);

        if (j == grid.getColumns() - 1 && i % 2 == 0) {
          addBuffer(i, endBufferIndex(j));
        }
      }
      rowHasFrontBuffer = false;
    }
  }

  private int endBufferIndex(int j) {
    return 2 * j + 2;
  }

  private void addHexagon(Cell cell, boolean frontBuffer) {
    int column = 2 * cell.getCoordinate().x;
    if (frontBuffer) column++;
    int row = cell.getCoordinate().y;

    addHalfHex(myLeftHex, cell.getState(), column, row);
    addHalfHex(myRightHex, cell.getState(), ++column, row);
  }

  private void addHalfHex(Shape shape, String state, int col, int row) {
    Region addedHalfHex = makeHalfHex(shape, state);
    addRegion(addedHalfHex, col, row);
  }

  private Region makeHalfHex(Shape shape, String state) {
    Region tempHalfHex = makeRegion(state);
    tempHalfHex.setShape(shape);
    return tempHalfHex;
  }

  private void addBuffer(int i, int j) {
    Region addedBuffer = new Region();
    addRegion(addedBuffer, j, i);
  }

  private static Shape getRightHex() {
    Polygon rightHex = new Polygon();
    rightHex.getPoints().addAll(0.0, 0.0,
        100.0, 0.0,
        200.0, 200.0,
        100.0, 400.0,
        0.0, 400.0);
    return rightHex;
  }

  private static Shape getLeftHex() {
    Polygon leftHex = new Polygon();
    leftHex.getPoints().addAll(0.0, 0.0,
        -100.0, 0.0,
        -200.0, 200.0,
        -100.0, 400.0,
        0.0, 400.0);
    return leftHex;
  }
}