package cellsociety;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class GridView {
  private double scene_height;
  private double scene_width;
  /**
   * Calls renderGrid() to turn grid into a collection of shape objects that we can add to the root
   * of our scene
   *
   * @param sceneWidth  the width of the scene we want to return
   * @param sceneHeight the height of the scene we want to return. C
   * @return a scene that is a rendered version of our grid
   **/
  public Group createGroup(Grid grid, double sceneWidth, double sceneHeight) {
    scene_width = sceneWidth;
    scene_height = sceneHeight;
    Group gridView = new Group();

    double cellWidth = sceneWidth / grid.getColumns();
    double cellHeight = sceneHeight / grid.getRows();

    Collection<Shape> cells = renderGrid(grid, cellWidth, cellHeight);
    gridView.getChildren().addAll(cells);

    return gridView;
  }

  /**
   * Takes in a grid and transforms it into a collection of shapes that can be visualized
   *
   * @param grid       the grid (ArrayList of ArrayList of cells) to render
   * @param cellWidth  the width of each cell once it is rendered
   * @param cellHeight the height of each cell once it is rendered
   * @return a collection of cells transformed into shapes
   **/
  public Collection<Shape> renderGrid(Grid grid, double cellWidth,
                                      double cellHeight) {
    Collection<Shape> cells = new ArrayList<>();

    for (ArrayList<Cell> row : grid.getGrid()) {
      for (Cell cell : row) {
        int x = grid.getGrid().indexOf(row);
        int y = row.indexOf(cell);
        Shape shape = new Rectangle(x * cellWidth, y * cellHeight+(scene_height/10), cellWidth, cellHeight);
        shape.setFill(cell.getColor());
        cells.add(shape);
      }
    }

    return cells;
  }

}