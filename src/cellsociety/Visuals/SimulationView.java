package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

import java.util.Map;

public class SimulationView {

    private VBox mySimulation;
    private StatView myStatView;
    private HexGridView myHexGridView;

    public SimulationView(String language, String author, String title, Shape shape, Map stats) {
        mySimulation = new VBox();
        setStatView(title, author, stats);
        setGridView(language, shape);
    }

    private void setStatView(String title, String author, Map stats) {
        myStatView = new StatView(title, author, stats);
        mySimulation.getChildren().add(myStatView.getStatBox());
    }

    private void setGridView(String language, Shape shape) {
        myHexGridView = new HexGridView(language);
        mySimulation.getChildren().add(myHexGridView.getGridPane());
        mySimulation.setVgrow(myHexGridView.getGridPane(), Priority.ALWAYS);
    }

    public VBox getSimulationView() {
        return mySimulation;
    }

    public void updateGridView(Grid grid) {
        myHexGridView.updateGridView(grid);
        myStatView.updateStats(grid.getStats());
    }
}
