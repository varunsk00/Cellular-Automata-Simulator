package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class Main extends Application {

  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static double SCENE_WIDTH = 400;
  private static double SCENE_HEIGHT = 500;

  /**
   * Begins our JavaFX application Gets the current grid and sets the stage to a scene with that
   * grid
   */
  @Override
  public void start(Stage primaryStage) {

    PercGrid grid = new PercGrid(100, 100);
    grid.updateGrid();
    Group root = new Group();

    primaryStage.setTitle("Simulation");
    startAnimationLoop();

    GridView gridView = new GridView();
    Group gridGroup = gridView.createGroup(grid, SCENE_WIDTH, SCENE_HEIGHT - (SCENE_HEIGHT/10));


    Visualizer GUIControl = new Visualizer(SCENE_WIDTH, SCENE_HEIGHT);
    root.getChildren().addAll(GUIControl.createSimulator(), gridGroup);

    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void startAnimationLoop() {
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  private void step(double elapsedTime) {
    // TODO Add animations
  }
}