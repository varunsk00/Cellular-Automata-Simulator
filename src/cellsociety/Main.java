package cellsociety;

import cellsociety.Grids.Grid;
import cellsociety.Visuals.Footer;
import cellsociety.Visuals.GridView;
import cellsociety.Visuals.Header;
import java.io.File;

import cellsociety.Visuals.StatBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import cellsociety.xml.XMLException;
import cellsociety.xml.XMLParser;

/**
 * Main class of the program runs the JavaFX Application
 * Creates a JavaFX program based on a chosen XML file via a FileChooser that creates a Cellular Automaton simulation
 * Depends on a Footer and Header to get User Input, and then a Grid Subclass and GridViewer to create, update, and visualize the Grid
 * Depends on XMLException and XMLParser to load in the grid via an XML file
 * Assumes that the Grid Size won't be too large, since lag begins to start for simulations at 100x100 Grid size
 * Once grids become bigger than 100x100, they begin to skip frames since the computer is too slow to render each frame
 * @author Eric Doppelt, Jaidha Rosenblatt, Varun Kosgi
 */

public class Main extends Application {

  private static final String STYLESHEET = "default.css";
  private static final String RESOURCE_LANGUAGE = "Standard";

  private static double FRAMES_PER_SECOND = 1;
  private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  private double SCENE_WIDTH = 1000;
  private double SCENE_HEIGHT = 500;

  public static final String DATA_FILE_EXTENSION = "*.xml";
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);

  private static final Color GRID_BACKGROUND = Color.BEIGE;
  private static final Color ALL_COLOR = Color.ALICEBLUE;

  private List<GridView> allGridViews;
  private List<Grid> allGrids;
  private int totalGrids;

  private BorderPane root;
  private HBox center;
  private Header header;
  private Footer footer;
  private Stage myStage;
  private Timeline animation;
  private VBox allLeftStats;
  private VBox allRightStats;

  /**
   * Launches the JavaFX program
   * @param args Usual String Array passed to Main
   */

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Begins our JavaFX application
   * Starts the Animation Loop and sets the Border Pane, filling it with a Header, Footer, and Gridview
   * Sets the stage and scene and shows it
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Simulation");
    startAnimationLoop();

    setBorderPane();
    setHeader();
    setFooter();
    setCenter();
    setStats();

    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    scene.getStylesheets()
        .add(getClass().getResource("/" + STYLESHEET).toExternalForm());
    myStage = primaryStage;
    myStage.setScene(scene);
    myStage.show();
  }

  private void setBorderPane() {
    root = new BorderPane();
    root.setBackground(new Background(new BackgroundFill(ALL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    root.setMaxWidth(SCENE_WIDTH);
    root.setMaxHeight(SCENE_WIDTH);
  }

  private void setHeader() {
    header = new Header(RESOURCE_LANGUAGE);
    root.setTop(header.getHeader());
  }

  private void setFooter() {
    footer = new Footer(RESOURCE_LANGUAGE);
    root.setBottom(footer.getFooter());
  }

  private void setCenter() {
    allGridViews = new ArrayList<GridView>();
    allGrids = new ArrayList<Grid>();
    totalGrids = 0;

    center = new HBox();
    center.setBackground(new Background(new BackgroundFill(GRID_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
    root.setCenter(center);

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

  // Talk to TA before final about whether elapsedTime is needed
  private void step(double elapsedTime) {
    updateSpeed();

    if (header.getLoadStatus()) handleXML();
    else if (header.getSkipStatus()) skipAhead();
    else if (header.getPlayStatus()) updateState();
  }

  private void skipAhead() {
    System.out.println(footer.getSkipValue());
    System.out.println(totalGrids);

    for (int i = 0; i < footer.getSkipValue(); i++) {
      for (Grid tempGrid: allGrids) {
        tempGrid.updateGrid();
      }
    }

    for (int i = 0; i < totalGrids; i++) {
      allGridViews.get(i).updateGrid(allGrids.get(i));
    }

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
    header.togglePause();

      File dataFile = FILE_CHOOSER.showOpenDialog(myStage);

      if (dataFile == null) {
        header.setLoadOff();
        return;
      }

      try {
        XMLParser parser = new XMLParser("grid", dataFile);
        Grid tempGrid = parser.getGrid();
        GridView tempGridView = new GridView(parser.getGridType());
        tempGridView.updateGrid(tempGrid);

        allGrids.add(tempGrid);
        allGridViews.add(tempGridView);
        center.setHgrow(tempGridView.getGridPane(), Priority.ALWAYS);
        totalGrids++;
        center.getChildren().add(tempGridView.getGridPane());
        addStats(tempGrid.getTitle(), tempGrid.getAuthor(), new HashMap<>());
      } catch (XMLException e) {
        System.out.println(e.getMessage());
      }
    header.setLoadOff();
  }

  private void addStats(String title, String author, HashMap<String, Double> stats)  {
    StatBox tempStats = new StatBox(title, author, stats);
    if (allGrids.size() % 2 == 0) {
        allRightStats.getChildren().add(tempStats.getStatBox());
      } else {
        allLeftStats.getChildren().add(tempStats.getStatBox());
    }
  }

  private void updateSpeed() {
    animation.setRate(footer.getSpeed());
  }

  private void updateState() {
    for (int i = 0; i < totalGrids; i++) {
        allGrids.get(i).updateGrid();
        allGridViews.get(i).
                updateGrid(allGrids.get(i));
      }
    }

    private void setStats() {
    allLeftStats = new VBox();
    allLeftStats.setPrefWidth(SCENE_WIDTH / 8);
    root.setLeft(allLeftStats);
    allRightStats = new VBox();
    allRightStats.setPrefWidth(SCENE_WIDTH / 8);
    root.setRight(allRightStats);
    }
  }

