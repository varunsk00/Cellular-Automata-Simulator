package cellsociety.Visuals;

import java.util.ArrayList;
import java.util.Collection;

import cellsociety.Cell;
import cellsociety.Grid;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {

  private double cellWidth;
  private double cellHeight;
  private Grid grid;
  private ArrayList<ArrayList<Shape>> shapeGrid;
  private GridPane gridPane;

  public GridView(Grid grid, double sceneWidth, double sceneHeight) {
    this.cellWidth = sceneWidth / grid.getColumns();
    this.cellHeight = sceneHeight / grid.getRows();
    this.shapeGrid = new ArrayList<>();
    this.grid = grid;
    createShapeGrid();
    gridPane = new GridPane();
    gridPane.setHgap(0.5);
    gridPane.setVgap(0.5);
    renderGrid();
  }

  /**
   * creates a 2D ArrayList of shapes from a grid
   */
  private void createShapeGrid() {
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
      shapeGrid.add(rowShapes);
    }
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
  public void updateGrid(Grid newGrid) {
    this.grid = newGrid;
    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getColumns(); j++) {
        Cell cell = grid.getGrid().get(i).get(j);
        Shape shape = shapeGrid.get(i).get(j);
        shape.setFill(cell.getColor());
      }
    }
  }

  public void renderGrid() {
    gridPane.getChildren().clear();
    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getColumns(); j++) {
        gridPane.add(shapeGrid.get(i).get(j),i,j);
      }
    }
    System.out.println(gridPane);
  }


}
