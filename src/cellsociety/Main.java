package cellsociety;

import cellsociety.Visuals.GridView;
import cellsociety.Visuals.Header;
import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.paint.Color;

import java.util.Random;
import javafx.util.Pair;
import jdk.swing.interop.SwingInterOpUtils;
import xml.XMLException;
import xml.XMLParser;


public class Main extends Application {

  private static final double FRAMES_PER_SECOND = 30;
  private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static double SCENE_WIDTH = 400;
  private static double SCENE_HEIGHT = 500;

  public static final String DATA_FILE_EXTENSION = "*.xml";
  // NOTE: generally accepted behavior that the chooser remembers where user left it last
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);


  private Grid grid;
  private GridView gridView;
  private Header GUIController;
  public static void main(String[] args) {launch(args);}


  /**
   * Begins our JavaFX application Gets the current grid and sets the stage to a scene with that
   * grid
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Simulation");
    selectFile(primaryStage);

    startAnimationLoop();

//    grid = new FireGrid(100,100, 0.6);
//    grid.getGrid().get(grid.getColumns()/2).get(grid.getColumns()/2).update(Color.RED, "burning");
    //random generation of Percolation blocked bricks (33% blocked)
//    for (int i = 0; i < grid.getRows(); i++) {
//      for (int j = 0; j < grid.getColumns(); j++) {
//        int rr = r.nextInt(3);
//        if (rr == 1){
//          int ran_x = r.nextInt(grid.getColumns());
//          int ran_y = r.nextInt(grid.getRows());
//          grid.getGrid().get(ran_x).get(ran_y).update(Color.BLACK, "blocked");
//        }
//      }
//    }

    Group root = new Group();
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
  // set some sensible defaults when the FileChooser is created
  private static FileChooser makeChooser (String extensionAccepted) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    // pick a reasonable place to start searching for files
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
    return result;
  }

  // display given message to user using the given type of Alert dialog box
  private void showMessage (AlertType type, String message) {
    new Alert(type, message).showAndWait();
  }

  private void selectFile(Stage primaryStage) {
    File dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
    while (dataFile != null) {
      try {
        grid = new XMLParser("grid").getGrid(dataFile);
        System.out.println(grid);
        break;
      }
      catch (XMLException e) {
        // handle error of unexpected file format
        showMessage(AlertType.ERROR, e.getMessage());
      }
      dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
    }
  }



}