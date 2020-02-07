package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Map;

public class SimulationView {

    private VBox mySimulation;
    private StatView myStatView;
    private GridView myGridView;

    public SimulationView(String language, String author, String title, Map stats) {
        mySimulation = new VBox();
        setStatView(title, author, stats);
        setGridView(language);
    }

    private void setStatView(String title, String author, Map stats) {
        myStatView = new StatView(title, author, stats);
        mySimulation.getChildren().add(myStatView.getStatBox());
    }

    private void setGridView(String language) {
        myGridView = new GridView(language);
        mySimulation.getChildren().add(myGridView.getGridPane());
        mySimulation.setVgrow(myGridView.getGridPane(), Priority.ALWAYS);
    }

    public VBox getSimulationView() {
        return mySimulation;
    }

    public void updateGridView(Grid grid) {
        myGridView.updateGridView(grid);
        myStatView.updateStats(grid.getStats());
    }
}
