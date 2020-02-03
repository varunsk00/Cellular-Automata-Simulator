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

import java.util.Random;


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
  private Random r = new Random();


  /**
   * Begins our JavaFX application Gets the current grid and sets the stage to a scene with that
   * grid
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Simulation");
    startAnimationLoop();

    grid = new LifeGrid(50,50);
    //random generation of Percolation blocked bricks and LIFE alive bricks (33% blocked/alive)
    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getColumns(); j++) {
        if (r.nextFloat() <= 0.2){
          int ran_x = r.nextInt(grid.getColumns());
          int ran_y = r.nextInt(grid.getRows());
          grid.getGrid().get(ran_x).get(ran_y).update(Color.BLACK, "alive");
          //grid.getGrid().get(ran_x).get(ran_y).update(Color.BLACK, "blocked");
        }
      }
    }


    GUIController = new Header(SCENE_WIDTH, "Standard");
    gridView = new GridView(grid,SCENE_WIDTH,SCENE_HEIGHT);
    root.getChildren().addAll(gridView.getRenderGrid());

    root.getChildren().addAll(GUIController.renderHeader());

    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void startAnimationLoop() {
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step(SECOND_DELAY);
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  private void step(double elapsedTime) {
    if (GUIController.getPlayStatus()) {
      grid.updateGrid();
      gridView.updateGrid(grid);
    }
  }
}