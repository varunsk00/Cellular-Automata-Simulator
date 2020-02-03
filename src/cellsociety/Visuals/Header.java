package cellsociety.Visuals;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ResourceBundle;

public class Header {

  private double sceneHeight;
  private double sceneWidth;

  private static final String RESOURCES  = "Resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";

  private ResourceBundle myResources;

  private boolean playPressed;
  private boolean speedUpPressed;
  private boolean skipPressed;
  private boolean loadPressed;

  public Header(double sceneHeight, double sceneWidth, String language) {
    this.sceneHeight = sceneHeight;
    this.sceneWidth = sceneWidth;

    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    this.playPressed = false;
    this.skipPressed = false;
    this.speedUpPressed = false;
    this.loadPressed = false;
  }

  public HBox renderHeader() {
    HBox header = new HBox();
    header.setMaxWidth(sceneWidth);
    header.setPrefHeight((.1) * sceneHeight);

    Button loadButton = new Button(myResources.getString("Load"));
    loadButton.setMaxWidth(Double.MAX_VALUE);
    loadButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        loadPressed = true;
      }
    });

    Button playButton = new Button(myResources.getString("Play"));
    playButton.setMaxWidth(Double.MAX_VALUE);
    playButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        playPressed = (playPressed) ? false : true;
      }
    });

    Button speedUpButton = new Button(myResources.getString("SpeedUp"));
    speedUpButton.setMaxWidth(Double.MAX_VALUE);
    speedUpButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        speedUpPressed = (speedUpPressed) ? false : true;
      }
    });

    Button skipButton = new Button(myResources.getString("Skip"));
    skipButton.setMaxWidth(Double.MAX_VALUE);
    skipButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        skipPressed = (skipPressed) ? false : true;
      }
    });

    header.getChildren().add(loadButton);
    header.setHgrow(loadButton, Priority.ALWAYS);
    header.getChildren().add(speedUpButton);
    header.setHgrow(speedUpButton, Priority.ALWAYS);
    header.getChildren().add(skipButton);
    header.setHgrow(skipButton, Priority.ALWAYS);
    header.getChildren().add(playButton);
    header.setHgrow(playButton, Priority.ALWAYS);

    return header;
  }

  public boolean getPlayStatus() {
    return playPressed;
  }

  public boolean getLoadStatus() {
    return loadPressed;
  }

  public void setLoadOff() {loadPressed = false;}

  public boolean getSkipStatus() {return skipPressed;}

  public void setSkipOff() {skipPressed = false;}

  public boolean getSpeedStatus() {return speedUpPressed;}

  public void setSpeedOff() {speedUpPressed = false;}
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

   /**
   * Takes in a grid and transforms it into a collection of shapes that can be visualized
   *
   * @return a collection of cells transformed into shapes
   **/

  /*
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
   */
