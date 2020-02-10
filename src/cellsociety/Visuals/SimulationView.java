package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Map;

public class SimulationView {

    private VBox mySimulation;
    private StatView myStatView;
    private GridView myGridView;

    public SimulationView(String author, String title, String type, Map stats) {
        mySimulation = new VBox();
        setStatView(title, author, stats);
        setGridView(type);
    }

    private void setStatView(String title, String author, Map stats) {
        myStatView = new StatView(title, author, stats);
        mySimulation.getChildren().add(myStatView.getStatBox());
    }

    private void setGridView(String type) {
        switch(type) {
            case "rectangle":
                myGridView = new RectGridView();
                break;
            case "hexagon":
                myGridView = new HexGridView();
                break;
        }
        System.out.println("TYPE" + myGridView.getClass());
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
