package cellsociety;

import cellsociety.Visuals.GridView;
import cellsociety.Visuals.Header;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.paint.Color;


public class Main extends Application {

  private static final double FRAMES_PER_SECOND = 30;
  private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static double SCENE_WIDTH = 400;
  private static double SCENE_HEIGHT = 500;

  private Grid grid;
  private GridView gridView;
  private Header GUIController;
  public static void main(String[] args) {launch(args);}
  private Group root = new Group();


  /**
   * Begins our JavaFX application Gets the current grid and sets the stage to a scene with that
   * grid
   */
  @Override
  public void start(Stage primaryStage) throws InterruptedException {

    primaryStage.setTitle("Simulation");
    startAnimationLoop();

    grid = new FireGrid(100,100, 0.6);
    grid.getGrid().get(grid.getRows()/2).get(grid.getColumns()/2).update(Color.RED, "burning");

    GridView gridView = new GridView(grid, SCENE_WIDTH, SCENE_HEIGHT - (SCENE_HEIGHT/10));
    root.getChildren().addAll(gridView.getRenderGrid());
    GUIController = new Header(SCENE_WIDTH, "Standard");
    gridView = new GridView(grid,SCENE_WIDTH,SCENE_HEIGHT);
    root.getChildren().addAll(gridView.getRenderGrid());

    root.getChildren().addAll(GUIController.renderHeader());

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
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      try {
        step(SECOND_DELAY);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  private void step(double elapsedTime) throws InterruptedException {
    if (GUIController.getPlayStatus()) {
      grid.updateGrid();
      gridView.updateGrid(grid);
    }
  }
}