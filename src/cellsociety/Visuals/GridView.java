package cellsociety.Visuals;

import cellsociety.Grid;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;

public class GridView {

  private GridPane myGridPane;

  public GridView() {
    myGridPane = new GridPane();
    myGridPane.setHgap(1);
    myGridPane.setVgap(1);
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
    for (int i = 0; i < grid.getColumns(); i++) {
        for (int j = 0; j < grid.getRows(); j++) {
        Color oldColor = grid.getGrid().get(j).get(i).getColor();
        Paint newPaint = ((Region)myGridPane.getChildren().get(grid.getRows() * i + j)).getBackground().getFills().get(0).getFill();
        Color newColor = (Color)newPaint;

        if (!oldColor.equals(newColor)) {
        System.out.println(i + ", " + j);
        System.out.println(oldColor);
        System.out.println(newColor);

        myGridPane.getChildren().remove(grid.getRows() * i + j);
        Region addedShape = new Region();
        addedShape.setBackground(new Background(new BackgroundFill(grid.getGrid().get(j).get(i).getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        myGridPane.getChildren().add(grid.getRows() * i + j, addedShape);
        myGridPane.setHgrow(addedShape, Priority.ALWAYS);
        myGridPane.setVgrow(addedShape, Priority.ALWAYS);
        }
        }
        }

 */