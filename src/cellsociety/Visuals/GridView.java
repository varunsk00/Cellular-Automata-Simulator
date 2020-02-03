package cellsociety.Visuals;

import cellsociety.Cell;
import cellsociety.Grid;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {

  private GridPane myGridPane;

  public GridView(double sceneWidth, double sceneHeight) {

    myGridPane = new GridPane();
    myGridPane.setHgap(1);
    myGridPane.setVgap(1);

    myGridPane.setPrefHeight(.8 * sceneHeight);
    myGridPane.setPrefWidth(sceneWidth);

  }

  public GridPane getGridPane() {
    return myGridPane;
  }

  public void updateGrid(Grid grid){
    myGridPane.getChildren().clear();

    for (int i = 0; i < grid.getColumns(); i++) {
      for (int j = 0; j < grid.getRows(); j++) {
        Region addedShape = new Region();
        addedShape.setBackground(new Background(new BackgroundFill(grid.getGrid().get(j).get(i).getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        myGridPane.add(addedShape, i, j);
        myGridPane.setHgrow(addedShape, Priority.ALWAYS);
        myGridPane.setVgrow(addedShape, Priority.ALWAYS);
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



