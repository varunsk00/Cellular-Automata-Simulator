package cellsociety.Controllers;

import cellsociety.Controllers.xml.XMLException;
import cellsociety.Controllers.xml.XMLParser;
import cellsociety.Models.Grids.Grid;
import cellsociety.Visuals.GraphView;
import cellsociety.Visuals.SimulationView;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CAController extends Application {

    private static final String STYLESHEET = "cellsociety/Resources/default.css";
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

    private List<SimulationView> allSimulationViews;
    private List<Grid> allGrids;
    private List<GraphView> allGraphs;
    private int totalGrids;

    private BorderPane root;
    private HBox center;
    private ButtonControls header;
    private SliderControls footer;
    private Stage myStage;
    private Timeline animation;

    /**
     * Begins our JavaFX application
     * Starts the Animation Loop and sets the Border Pane, filling it with a Header, Footer, and Gridview
     * Sets the stage and scene and shows it
     */

    public CAController() {
        System.out.println("Creating CAController");
    }

    public CAController(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulation");
        startAnimationLoop();

        setBorderPane();
        setHeader();
        setFooter();
        setCenter();

        allGraphs = new ArrayList<>();

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
        header = new ButtonControls(RESOURCE_LANGUAGE);
        root.setTop(header.getHeader());
    }

    private void setFooter() {
        footer = new SliderControls(RESOURCE_LANGUAGE);
        root.setBottom(footer.getFooter());
    }

    private void setCenter() {
        allSimulationViews = new ArrayList<SimulationView>();
        allGrids = new ArrayList<Grid>();
        totalGrids = 0;

        center = new HBox();
        center.setBackground(new Background(new BackgroundFill(GRID_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(center);

    }

    private void startAnimationLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() {
        updateSpeed();
        if (header.getLoadStatus()) handleXML();
        else if (header.getSkipStatus()) skipAhead();
        else if (header.getClearStatus()) updateClear();
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
            allSimulationViews.get(i).updateGridView(allGrids.get(i));
        }

        header.setSkipOff();
    }

    private static FileChooser makeChooser(String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Text Files", extensionAccepted));
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
            XMLParser parser = new XMLParser(dataFile);
            Grid tempGrid = parser.getGrid();
            Map<String, String> details = tempGrid.getDetails();


          SimulationView tempSimulation = new SimulationView(details.get("author"), details.get("title"), details.get("gridType"), tempGrid.getStats());
            tempSimulation.updateGridView(tempGrid);

            allGrids.add(tempGrid);
            allSimulationViews.add(tempSimulation);
            center.getChildren().add(tempSimulation.getSimulationView());
            center.setHgrow(tempSimulation.getSimulationView(), Priority.ALWAYS);
            totalGrids++;

            allGraphs.add(new GraphView(details.get("title"), tempGrid, tempGrid.getStateMap().keySet(), myStage));

        } catch (XMLException e) {
            System.out.println(e.getMessage());
        }
        header.setLoadOff();
    }

    private void updateSpeed() {
        animation.setRate(footer.getSpeed());
    }

    private void updateState() {
        for (int i = 0; i < totalGrids; i++) {
            Grid tempGrid = allGrids.get(i);
            tempGrid.updateGrid();
            allSimulationViews.get(i).
                    updateGridView(tempGrid);
            allGraphs.get(i).updateGraph(tempGrid.getNumIterations(), tempGrid.getStats());
        }
    }

    private void updateClear() {
        allGrids.clear();
        allSimulationViews.clear();
        for (GraphView tempGraph : allGraphs) tempGraph.close();

        totalGrids = 0;
        center.getChildren().clear();
        header.setClearOff();
    }
}

