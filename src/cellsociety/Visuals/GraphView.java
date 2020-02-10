package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

// Inspiration Came from https://docs.oracle.com/javafx/2/charts/line-chart.htm
/**
 * GraphView creates a new window in the Simulation Application that displays the Stats of a Current Simulation
 * The graph is a line graph that shows the count of each state in the given simulation at its current instance
 * updateGraph() is called after the grid is updated in CAController
 * @author Eric Doppelt
 */

public class GraphView {

    private Stage myStage;
    private LineChart myLineChart;
    private Map<String, XYChart.Series> mySeries;
    private ResourceBundle myResourceBundle;

    private static final int ITERATION_TICK_UNITS = 1;
    private static final String RESOURCE_LANGUAGE = "StandardGraph";

    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 500;

    /**
     * General Constructor for a GraphView object that creates a new window for a given simulation
     * Creates a LineChart in a new Window that displays the count of each state for a simulation at its instance
     * @param type specifies the type of simulation to be displayed in the title of the stage
     * @param grid is the grid whose stats are being displayed
     * @param states specifies the types of states a cell can have in the simulation type
     * @param parent is the stage in CAController which is the parent stage of any given GraphView's window
     */
    public GraphView(String type, Grid grid, Set<String> states, Stage parent) {
        myResourceBundle = ResourceBundle.getBundle(RESOURCE_LANGUAGE);

        setStage(type, parent);

        createSeries(states);

        makeGraph();
        updateGraph(grid.getNumIterations(), grid.getStats());
        displayGraph();
    }

    /**
     * Adds a new set of data points to the GraphView
     * Does not change the graph if time is less than 0
     * @param time is the number of iterations the Grid has gone through
     * @param stats is a Map that connects each State to its count
     */
    public void updateGraph(int time, Map<String, Integer> stats) {
        System.out.println(time);
        if (time < 0) return;
        myLineChart.getData().clear();
        for (String state : mySeries.keySet()) {
                if (stats.get(state) == null) stats.put(state, 0);
                mySeries.get(state).getData().add(new XYChart.Data(time, stats.get(state)));
                myLineChart.getData().add(mySeries.get(state));
            }
        }

    /**
     * Closes the GraphView by setting the stage to closed
     */
    public void close() {
        myStage.close();
    }

    private void setStage(String type, Stage parent) {
        myStage = new Stage();
        myStage.setTitle(String.format("%s %s", type, myResourceBundle.getString("GraphSuffix")));
        myStage.initOwner(parent);
    }

    private NumberAxis makeAxis(String key) {
        NumberAxis returnedAxis = new NumberAxis();
        returnedAxis.setLabel(myResourceBundle.getString(key));
        return returnedAxis;
    }

    private void makeGraph() {
        NumberAxis xAxis = makeAxis("GraphX");
        NumberAxis yAxis = makeAxis("GraphY");
        xAxis.setTickUnit(ITERATION_TICK_UNITS);

        myLineChart = new LineChart<Number, Number>(xAxis, yAxis);
        myLineChart.setAnimated(false);
    }

    private void createSeries(Set<String> states) {
        mySeries = new HashMap<>();
        for (String state : states) {
            XYChart.Series stateSeries = new XYChart.Series();
            stateSeries.setName(state);
            mySeries.put(state, stateSeries);
        }
    }

    private void displayGraph() {
        Scene scene = new Scene(myLineChart, WINDOW_WIDTH, WINDOW_HEIGHT);
        myStage.setScene(scene);
        myStage.show();
    }
}