package cellsociety.Visuals;

import cellsociety.Cell;
import cellsociety.Grid;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {

  private GridPane myGridPane;
  private double cellWidth;
  private double cellHeight;

  public GridView(Grid grid, double width, double height) {

    myGridPane = new GridPane();

    cellWidth = width / grid.getColumns();
    cellHeight = height / grid.getRows();
    System.out.println(cellHeight);
    System.out.println(cellWidth);

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

    System.out.println("SIZE = " + myGridPane.getChildren().size());
  }

  public GridPane getGridPane() {
    return myGridPane;
  }

  public void updateGrid(Grid grid){
    System.out.println("updating");
    myGridPane = new GridPane();

    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getColumns(); j++) {
        Shape addedShape = new Rectangle();
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
        // System.out.println(x + y);
        shape.setFill(cell.getColor());
        rowShapes.add(shape);
      }
      shapesGrid.add(rowShapes);
    }
  }
 */