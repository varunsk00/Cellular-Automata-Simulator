package cellsociety;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {

  private double cellWidth;
  private double cellHeight;
  private ArrayList<ArrayList<Shape>> shapeGrid;
  private ArrayList<Shape> renderGrid;

  public GridView(Grid grid, double sceneWidth, double sceneHeight) {
    this.cellWidth = sceneWidth / grid.getColumns();
    this.cellHeight = sceneHeight / grid.getRows();
    this.shapeGrid = new ArrayList<>();
    createShapeGrid(grid);
    renderGrid = renderGrid();
  }

  /**
   * creates a 2D ArrayList of shapes from a grid
   * @param grid to visualize
   */
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
      shapeGrid.add(rowShapes);
    }
  }

  /**
   * return a grid that can be added to scene
   * @return a grid as a collection of shapes
   */
  public Collection<Shape> getRenderGrid() {
    return renderGrid;
  }

  /**
   * Update shapeGrid based on a new grid
   * @param grid the grid used for the updated visualization
   */
  public void updateGrid(Grid grid) {
    int rows = grid.getRows();
    int columns = grid.getColumns();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        Cell cell = grid.getGrid().get(i).get(j);
        Shape shape = shapeGrid.get(i).get(j);
        shape.setFill(cell.getColor());
      }
    }
    renderGrid();
  }

  private ArrayList<Shape> renderGrid() {
    ArrayList<Shape> render = new ArrayList<>();
    for (Collection<Shape> row : shapeGrid) {
      for (Shape shape : row) {
        render.add(shape);
      }
    }
    return render;
  }


}