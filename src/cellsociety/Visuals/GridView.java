package cellsociety.Visuals;

import cellsociety.Cell;
import cellsociety.Grid;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;

public class GridView {

  private GridPane myGridPane;
  private double sceneWidth;
  private double sceneHeight;

  public GridView(double sceneWidth, double sceneHeight) {

    myGridPane = new GridPane();
    myGridPane.setMaxHeight(.8 * sceneHeight);
    myGridPane.setMaxWidth(sceneWidth);

  }

  public GridPane getGridPane() {
    return myGridPane;
  }

  public void updateGrid(Grid grid){
    myGridPane.getChildren().clear();

    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getColumns(); j++) {
        Rectangle addedShape = new Rectangle(10, 10);
        myGridPane.add(addedShape, j, i);
        addedShape.setFill(grid.getGrid().get(i).get(j).getColor());
        GridPane.setFillWidth(addedShape, true);
        GridPane.setFillHeight(addedShape, true);
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



