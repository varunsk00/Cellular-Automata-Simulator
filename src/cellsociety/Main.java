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

  private static final int FRAMES_PER_SECOND = 2;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static double SCENE_WIDTH = 400;
  private static double SCENE_HEIGHT = 500;

  private Grid grid = new Grid(100, 100);

  /**
   * Begins our JavaFX application Gets the current grid and sets the stage to a scene with that
   * grid
   */
  @Override
<<<<<<< Updated upstream
  public void start(Stage primaryStage) {

    Group root = new Group();

=======
  public void start(Stage primaryStage) throws InterruptedException {
    XMLReader reader = new XMLReader();
    reader.main(null);
>>>>>>> Stashed changes
    primaryStage.setTitle("Simulation");
    startAnimationLoop();

    Grid grid = new Grid(100,100);

    GridView gridView = new GridView(grid, SCENE_WIDTH, SCENE_HEIGHT - (SCENE_HEIGHT/10));
    root.getChildren().addAll(gridView.getRenderGrid());

    Visualizer GUIControl = new Visualizer(SCENE_WIDTH, SCENE_HEIGHT);
    root.getChildren().add(GUIControl.createSimulator());
    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();

    /**
     * Updates a grid after it has already been added to a scene!!
     */
    grid.updateGrid();
    gridView.updateGrid(grid);

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