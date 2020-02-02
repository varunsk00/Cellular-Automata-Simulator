package cellsociety.Visuals;

import java.util.ArrayList;
import java.util.Collection;

import cellsociety.Cell;
import cellsociety.Grid;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {

  private int cellWidth;
  private int cellHeight;
  private GridPane gridPane;

  public GridView(Grid grid, double sceneWidth, double sceneHeight) {
    this.cellWidth = (int) sceneWidth / grid.getColumns();
    this.cellHeight = (int) sceneHeight / grid.getRows();
    gridPane = new GridPane();
    createGrid(grid);
  }

  /**
   * return a grid that can be added to scene
   *
   * @return a grid as a collection of shapes
   */
  public GridPane getGridPane() {
    return gridPane;
  }

  /**
   * Update shapeGrid based on a new grid
   *
   * @param newGrid the grid used for the updated visualization
   */
  public void createGrid(Grid newGrid) {
    for (int i = 0; i < newGrid.getRows(); i++) {
      for (int j = 0; j < newGrid.getColumns(); j++) {
        Cell cell = newGrid.getGrid().get(i).get(j);
        Shape shape = new Rectangle(cellWidth, cellHeight, cell.getColor());
        gridPane.add(shape, j, i);
      }
    }
  }

  private Shape getShapeByRowColumnIndex(final int row, final int column) {
    Shape result = null;
    for (Node node : gridPane.getChildren()) {
      if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
        result = (Shape) node;
        break;
      }
    }
    return result;
  }

  public void updateGrid(Grid newGrid) {
    for (int i = 0; i < newGrid.getRows(); i++) {
      for (int j = 0; j < newGrid.getColumns(); j++) {
        Cell cell = newGrid.getGrid().get(i).get(j);
        Shape shape = getShapeByRowColumnIndex(i, j);
        System.out.println(shape);
        shape.setFill(cell.getColor());
      }
    }
  }
}
