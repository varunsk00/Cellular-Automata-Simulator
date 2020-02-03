package cellsociety;

import cellsociety.Visuals.Footer;
import cellsociety.Visuals.GridView;
import cellsociety.Visuals.Header;
import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.util.Random;
import xml.XMLException;
import xml.XMLParser;


public class Main extends Application {

  private static final String RESOURCE_LANGUAGE = "Standard";

  private static final double FRAMES_PER_SECOND = 30;
  private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static double SCENE_WIDTH = 400;
  private static double SCENE_HEIGHT = 500;

  public static final String DATA_FILE_EXTENSION = "*.xml";
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);

  private Grid grid;
  private GridView gridView;
  private Header GUIController;

  private BorderPane root;
  private Header inputHeader;
  private Footer inputFooter;

  private Stage myStage;

  private Random r = new Random();

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Begins our JavaFX application Gets the current grid and sets the stage to a scene with that
   * grid
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Simulation");

    startAnimationLoop();

    grid = new FireGrid(100, 100, 0.6);
    grid.getGrid().get(grid.getColumns() / 2).get(grid.getColumns() / 2).update(Color.RED, "burning");
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


    root = new BorderPane();

    inputHeader = new Header(SCENE_HEIGHT, SCENE_WIDTH, RESOURCE_LANGUAGE);
    root.setTop(inputHeader.renderHeader());

    inputFooter = new Footer(SCENE_HEIGHT, RESOURCE_LANGUAGE);
    root.setBottom(inputFooter.renderFooter());

    gridView = new GridView(grid, SCENE_WIDTH, SCENE_HEIGHT);
    root.setCenter(gridView.getGridPane());

    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    myStage = primaryStage;
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
    if (inputHeader.getLoadStatus()) xmlToGrid();
    else if (inputHeader.getSkipStatus()) skipAhead();
    else if (inputHeader.getPlayStatus()) updateState();
  }


  private void skipAhead() {
    System.out.println(inputFooter.getSkipJump().getValue());
    for (int i = 0; i < inputFooter.getSkipJump().getValue(); i++) {
      grid.updateGrid();
    }
    gridView.updateGrid(grid);
    inputHeader.setSkipOff();
  }

//  private void uploadFile() {
//    FileChooser fc = new FileChooser();
//    File file = fc.showOpenDialog(myStage);
//    if (file == null) System.out.println("Please pick a file!");
//    else if (!file.getName().substring(file.getName().length() - 4).equals(".xml")) System.out.println("Please pick an XML File!");
//    else {
//      myXMLFile = file;
//    }
//
//    inputHeader.setLoadOff();
//  }

  private static FileChooser makeChooser (String extensionAccepted) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    // pick a reasonable place to start searching for files
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
    return result;
  }

  private void xmlToGrid(){
    File dataFile = FILE_CHOOSER.showOpenDialog(myStage);
    try {
      grid = new XMLParser("grid").getGrid(dataFile);
    }
    catch (XMLException e) {
      System.out.println(e.getMessage());
    }
    inputHeader.setLoadOff();
  }
  private void updateState() {
    grid.updateGrid();
    gridView.updateGrid(grid);
  }



}

