package cellsociety.Visuals;

import java.util.ArrayList;
import java.util.Collection;

import cellsociety.Cell;
import cellsociety.Grid;
<<<<<<< HEAD
import javafx.collections.ObservableList;
import javafx.scene.Node;
=======
>>>>>>> 783306b09f6954efde008347a5971ef78a2c96f4
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {

<<<<<<< HEAD
  private int cellWidth;
  private int cellHeight;
  private GridPane gridPane;

  public GridView(Grid grid, double sceneWidth, double sceneHeight) {
    this.cellWidth = (int) sceneWidth / grid.getColumns();
    this.cellHeight = (int) sceneHeight / grid.getRows();
    gridPane = new GridPane();
    createGrid(grid);
=======
  private GridPane myGridPane;
  private double cellWidth;
  private double cellHeight;

  public GridView(Grid grid, double sceneWidth, double sceneHeight) {

    myGridPane = new GridPane();

    cellWidth = sceneWidth / grid.getColumns();
    cellHeight = (.85) * sceneHeight / grid.getRows();

    renderGridPane(grid);

  }

  // assumes that the grid is the right size
  private void renderGridPane(Grid grid) {
    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getColumns(); j++) {
        Cell tempCell = grid.getGrid().get(i).get(j);
        Shape tempShape = new Rectangle(cellWidth, cellHeight);
        tempShape.setFill(tempCell.getColor());
        myGridPane.add(tempShape, i, j);
      }
    }
  }

  public GridPane getGridPane() {
    return myGridPane;
  }

  public void updateGrid(Grid grid){
    myGridPane.getChildren().clear();

    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getColumns(); j++) {
        Shape addedShape = new Rectangle(cellWidth, cellHeight);
        addedShape.setFill(grid.getGrid().get(i).get(j).getColor());
        myGridPane.add(addedShape, i, j);
        }
      }
  }
}

/*
 public void createShapeGrid(Grid grid) {
    for (ArrayList<Cell> row : grid.getGrid()) {
      ArrayList<Shape> rowShapes = new ArrayList<>();
      for (Cell cell : row) {
        int x = grid.getGrid().indexOf(row);
        int y = row.indexOf(cell);
        Shape shape = new Rectangle(x * cellWidth, y * cellHeight, cellWidth,
            cellHeight);
        shape.setFill(cell.getColor());
        rowShapes.add(shape);
      }
      shapesGrid.add(rowShapes);
    }
>>>>>>> 783306b09f6954efde008347a5971ef78a2c96f4
  }
 */

  /**
   * return a grid that can be added to scene
   *
   * @return a grid as a collection of shapes
   */
<<<<<<< HEAD
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
=======



>>>>>>> 783306b09f6954efde008347a5971ef78a2c96f4
