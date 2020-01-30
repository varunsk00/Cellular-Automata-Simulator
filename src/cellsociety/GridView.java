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

  private double sceneHeight;
  private double sceneWidth;
  private double cellWidth;
  private double cellHeight;
  private Grid grid;
  private Collection<Shape> gridView;

  public GridView(Grid grid, double sceneWidth, double sceneHeight) {
    this.sceneHeight = sceneHeight;
    this.sceneWidth = sceneWidth;
    this.grid = grid;
    this.cellWidth = sceneWidth / grid.getColumns();
    this.cellHeight = sceneHeight / grid.getRows();
    this.gridView = new ArrayList<>();
    renderGrid();
  }

  public Collection<Shape> getGridView() {
    return this.gridView;
  }

  /**
   * Takes in a grid and transforms it into a collection of shapes that can be visualized
   *
   * @return a collection of cells transformed into shapes
   **/
  public void renderGrid() {
    for (ArrayList<Cell> row : grid.getGrid()) {
      for (Cell cell : row) {
        int x = grid.getGrid().indexOf(row);
        int y = row.indexOf(cell);
        double heightOffset = sceneHeight / 10;
        Shape shape = new Rectangle(x * cellWidth, y * cellHeight + heightOffset, cellWidth,
            cellHeight);
        shape.setFill(cell.getColor());
        gridView.add(shape);
      }
    }
  }

  public void update(){
    for (Shape cell : gridView) {
      cell.setFill(Color.LIGHTBLUE);
    }
  }

}