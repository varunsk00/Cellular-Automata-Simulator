package cellsociety.Visuals;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Inspitation Came from https://docs.oracle.com/javafx/2/charts/line-chart.htm
public class GraphView {

    private Stage myStage;
    private LineChart myLineChart;
    private Map<String, XYChart.Series> mySeries;

    public GraphView(String type, int time, Map<String, Integer> stats) {
        myStage = new Stage();
        myStage.setTitle(type + " chart");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Count of State");
        xAxis.setLabel("Number of Updates on Grid");

        myLineChart = new LineChart<Number, Number>(xAxis, yAxis);
        mySeries = new HashMap<>();

        for (String state: stats.keySet()) {
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
        for (String state : stats.keySet()) {
            XYChart.Series tempSeries = mySeries.get(state);
            tempSeries.getData().add(new XYChart.Data(time, stats.get(state)));
            myLineChart.getData().add(tempSeries);
        }
    }
}

