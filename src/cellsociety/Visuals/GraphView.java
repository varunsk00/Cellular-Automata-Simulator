package cellsociety.Visuals;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// Inspitation Came from https://docs.oracle.com/javafx/2/charts/line-chart.htm
public class GraphView {

    private Stage myStage;
    private LineChart myLineChart;
    private Map<String, XYChart.Series> mySeries;

    public GraphView(String type, int time, Set<String> states, Map<String, Integer> stats, Stage parent) {
        myStage = new Stage();
        myStage.setTitle(type + " Chart");
        myStage.initOwner(parent);
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(1);
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Updates on Grid");
        yAxis.setLabel("Count of State");

        myLineChart = new LineChart<Number, Number>(xAxis, yAxis);
        myLineChart.setAnimated(false);
        mySeries = new HashMap<>();

        for (String state : states) {
            XYChart.Series stateSeries = new XYChart.Series();
            stateSeries.setName(state);
            mySeries.put(state, stateSeries);
        }

        updateGraph(time, stats);

        Scene scene = new Scene(myLineChart, 400, 200);
        myStage.setScene(scene);
        myStage.show();
    }

    public void close() {
        myStage.close();
    }

    public void updateGraph(int time, Map<String, Integer> stats) {
        myLineChart.getData().clear();
        for (String state : mySeries.keySet()) {
                if (stats.get(state) == null) stats.put(state, 0);
                mySeries.get(state).getData().add(new XYChart.Data(time, stats.get(state)));
                myLineChart.getData().add(mySeries.get(state));
            }
        }
    }


