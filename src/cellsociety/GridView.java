package cellsociety;

import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {

  /**
   * Calls renderGrid() to turn grid into a collection of shape objects that we can add to the root
   * of our scene
   *
   * @param sceneWidth  the width of the scene we want to return
   * @param sceneHeight the height of the scene we want to return. C
   * @return a scene that is a rendered version of our grid
   **/
  public Scene getScene(Grid grid, double sceneWidth, double sceneHeight) {
    Group root = new Group();

    double cellWidth = sceneWidth / grid.getColumns();
    double cellHeight = sceneHeight / grid.getRows();

    Collection<Shape> cells = renderGrid(grid.getGrid(), cellWidth, cellHeight);
    root.getChildren().addAll(cells);

    Scene scene = new Scene(root, sceneWidth, sceneHeight);
    return scene;
  }

  /**
   * Takes in a grid and transforms it into a collection of shapes that can be visualized
   *
   * @param grid       the grid (2D array of cells) to render
   * @param cellWidth  the width of each cell once it is rendered
   * @param cellHeight the height of each cell once it is rendered
   * @return a collection of cells transformed into shapes
   **/
  public Collection<Shape> renderGrid(Cell[][] grid, double cellWidth, double cellHeight) {
    Collection<Shape> cells = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
      Cell[] column = grid[i];
      for (int j = 0; j < column.length; j++) {
        Cell cell = grid[i][j];
        Shape shape = new Rectangle(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
        shape.setFill(cell.getColor());
        cells.add(shape);
      }
    }
    return cells;
  }

}
