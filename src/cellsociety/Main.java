package cellsociety;

import cellsociety.Visuals.Footer;
import cellsociety.Visuals.GridView;
import cellsociety.Visuals.Header;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.paint.Color;


public class Main extends Application {

  private static final String RESOURCE_LANGUAGE = "Standard";

  private static final double FRAMES_PER_SECOND = 60;
  private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static double SCENE_WIDTH = 400;
  private static double SCENE_HEIGHT = 500;

  private Grid grid;
  private GridView gridView;
  public static void main(String[] args) {launch(args);}
  private BorderPane root;


  /**
   * Begins our JavaFX application Gets the current grid and sets the stage to a scene with that
   * grid
   */
  @Override
  public void start(Stage primaryStage) throws InterruptedException {
    primaryStage.setTitle("Simulation");
    startAnimationLoop();

    root = new BorderPane();

    grid = new FireGrid(3,3, 0.6);
    grid.getGrid().get(1).get(1).update(Color.RED, "burning");

    gridView = new GridView(grid, SCENE_WIDTH, SCENE_HEIGHT - 100);
    root.setCenter(gridView.getGridPane());

    Header headerInput = new Header(SCENE_WIDTH, RESOURCE_LANGUAGE);
    root.setTop(headerInput.renderHeader());

    Footer footerInput = new Footer(SCENE_WIDTH, RESOURCE_LANGUAGE);
    root.setBottom(footerInput.renderFooter());
    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();
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
    System.out.println(grid);
      grid.updateGrid();
    System.out.println(grid);
      gridView.updateGrid(grid);
    }
  }
