package cellsociety;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Visualizer {

  private double sceneHeight;
  private double sceneWidth;

  private boolean playPressed;
  private boolean speedUpPressed;
  private boolean skipPressed;

  private final static Color HEADER_COLOR = Color.LIGHTBLUE;

  public Visualizer(double sceneWidth, double sceneHeight) {
    this.sceneHeight = sceneHeight;
    this.sceneWidth = sceneWidth;

    this.playPressed = false;
    this.skipPressed = false;
    this.speedUpPressed = false;
  }

  public Group renderHeader() {
    Group returnedGroup = new Group();

    Button playButton = new Button("Play!");
    playButton.setLayoutX(0);
    playButton.setLayoutY(0);
    playButton.setPrefSize((3 * sceneWidth) / 10, sceneHeight / 10);
    playButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        playPressed = true;
      }
    });

    Button speedUpButton = new Button("Speed Up");
    speedUpButton.setLayoutX((3 * sceneWidth) / 10);
    speedUpButton.setLayoutY(0);
    speedUpButton.setPrefSize((4 * sceneWidth) / 10, sceneHeight / 10);

    speedUpButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        speedUpPressed = true;
      }
    });

    Button skipButton = new Button("Skip Ahead");
    skipButton.setLayoutX((7 * sceneWidth) / 10);
    skipButton.setLayoutY(0);
    skipButton.setPrefSize((3 * sceneWidth) / 10, sceneHeight / 10);

    skipButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        skipPressed = true;
      }
    });

    returnedGroup.getChildren().add(speedUpButton);
    returnedGroup.getChildren().add(skipButton);
    returnedGroup.getChildren().add(playButton);

    return returnedGroup;
  }

  /**
   * Takes in a grid and transforms it into a collection of shapes that can be visualized
   *
   * @return a collection of cells transformed into shapes
   **/
  public Group renderGrid(Grid grid) {
    double cellWidth = sceneWidth / grid.getColumns();
    double cellHeight = (.8  * sceneHeight) / grid.getRows();

    Group returnedCells = new Group();
    System.out.println("rendering grid");
    for (int i = 0; i < grid.getColumns(); i++) {
      for (int j = 0; j < grid.getRows(); j++) {
        Rectangle addedCell = new Rectangle(i * cellWidth, (sceneHeight / 10) + j * cellHeight, cellWidth, cellHeight);
        addedCell.setFill(grid.getGrid().get(i).get(j).getColor());
        returnedCells.getChildren().add(addedCell);
      }
    }
    return returnedCells;
  }
}
  /**
   * NOTE: not sure what the point of this is, but leaving it in
   */
  /*
    public void update(Group group){
    for (Shape cell : myGrid) {
      cell.setFill(Color.LIGHTBLUE);
    }
  }
   */
