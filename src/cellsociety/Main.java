package cellsociety;

import cellsociety.Visuals.Footer;
import cellsociety.Visuals.GridView;
import cellsociety.Visuals.Header;
import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.Random;
import xml.XMLException;
import xml.XMLParser;


public class Main extends Application {

  private static final String DEFAULT_RESOURCE_FOLDER = "/Resources/";
  private static final String STYLESHEET = "default.css";
  private static final String RESOURCE_LANGUAGE = "Standard";

  private static double FRAMES_PER_SECOND = 1;
  private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  private double SCENE_WIDTH = 500;
  private double SCENE_HEIGHT = 500;

  public static final String DATA_FILE_EXTENSION = "*.xml";
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);

  private Grid grid;
  private GridView gridView;
  private BorderPane root;
  private Header header;
  private Footer footer;
  private Stage myStage;
  private Timeline animation;

  private Random r = new Random();

  private static final Color BACKGROUND_COLOR = Color.ALICEBLUE;

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

    setBorderPane();
    setHeader();
    setFooter();
    setGridView();

    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    scene.getStylesheets()
        .add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    myStage = primaryStage;
    myStage.setScene(scene);
    startAnimationLoop();
    myStage.show();
  }

  private void setBorderPane() {
    root = new BorderPane();
    root.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    root.setMaxHeight(SCENE_HEIGHT);
    root.setMaxWidth(SCENE_WIDTH);
  }

  private void setHeader() {
    header = new Header(RESOURCE_LANGUAGE);
    root.setTop(header.getHeader());
  }

  private void setFooter() {
    footer = new Footer(RESOURCE_LANGUAGE);
    root.setBottom(footer.getFooter());
  }

  private void setGridView() {
    gridView = new GridView();
    root.setCenter(gridView.getGridPane());
  }

  private void startAnimationLoop() {
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step(SECOND_DELAY);
    });
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  private void step(double elapsedTime) {
    if (header.getLoadStatus()) handleXML();
    else if (header.getSkipStatus()) skipAhead();
    else if (header.getSpeedStatus()) updateSpeed();
    else if (header.getPlayStatus()) updateState();
  }

  private void skipAhead() {
    for (int i = 0; i < footer.getJumpValue(); i++) {
      grid.updateGrid();
    }
    gridView.updateGrid(grid);
    header.setSkipOff();
  }

  private static FileChooser makeChooser(String extensionAccepted) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    // pick a reasonable place to start searching for files
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
    return result;
  }

  private void handleXML() {
    File dataFile = FILE_CHOOSER.showOpenDialog(myStage);
    if (dataFile==null){
      header.setLoadOff();
      return;
    }
    try {
      XMLParser parser = new XMLParser("grid");
      grid = parser.getGrid(dataFile);
      header.setPlayOff();
      gridView.updateGrid(grid);
      header.setAuthorTitle(parser.getAuthors(dataFile), parser.getTitle(dataFile));
      }
    catch (XMLException e) {
      System.out.println(e.getMessage());
    }
    header.setLoadOff();
  }

  private void updateSpeed() {
    animation.setRate(footer.getSpeed());
    header.setSpeedOff();
  }

  private void updateState() {
    if (grid != null) {
      grid.updateGrid();
      gridView.updateGrid(grid);
    }
  }
}
