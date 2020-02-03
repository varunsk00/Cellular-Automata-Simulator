package cellsociety.Visuals;

import java.util.ArrayList;
import java.util.Collection;

import cellsociety.Cell;
import cellsociety.Grid;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {

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
  }
 */

  /**
   * return a grid that can be added to scene
   * @return a grid as a collection of shapes
   */



