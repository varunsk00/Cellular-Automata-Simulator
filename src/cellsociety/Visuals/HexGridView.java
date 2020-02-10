package cellsociety.Visuals;

import cellsociety.Models.Cells.Cell;
import cellsociety.Models.Grids.Grid;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * Converts a Grid object to a dynamic GridPane object with Hexagon cell shapes that can be displayed in CAController
 * HexGridView extends GridView inheriting a GridPane
 * The GridPane is dynamic in size and changes based on changes to the size of the window
 * @authors Eric Doppelt
 */
public class HexGridView extends GridView {

    private Shape myRightHex;
    private Shape myLeftHex;
    private Map<String, String> stateMap;

    /**
     * Basic constructor for a GridView object
     * Takes no parameters but creates an instance that can be used later to call updateGrid()
     * Future variations can store a grid to the GridPane to be updated for efficiency purposes
     * Creates the GridPane and sets Horizontal and Vertical Gaps to 1
     */
    public HexGridView() {
        myRightHex = getRightHex();
        myLeftHex = getLeftHex();
    }

    /**
     * Method provides the functionality of the Grid View class
     * Clears instance variable myGridPane and updates it to show the grid parameter
     * Dynamically sized to fit size of center in BorderPane in Main
     * Called in Main every time the grid updates through the step() function
     *
     * @param grid takes in a grid to be represented via a GridPane
     */
    @Override
    public void updateGridView(Grid grid) {
        myGridPane.getChildren().clear();
        stateMap = grid.getStateMap();
        boolean rowHasFrontBuffer = false;

        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getColumns(); j++) {
                if (j == 0 && i % 2 != 0) {
                    addBuffer(i, j);
                    rowHasFrontBuffer =  true;
                }

                addHexagon(grid.current(i, j), rowHasFrontBuffer);

                if (j == grid.getColumns() - 1 && i % 2 == 0) {
                    addBuffer(i, endBufferIndex(j));
                }
            }
            rowHasFrontBuffer = false;
        }
    }

    private int endBufferIndex(int j) {
        return 2 * j + 2;
    }

    private void addHexagon(Cell cell, boolean buffer) {
        int column = 2 * cell.getCoordinate().x;
        if (buffer) column++;
        int row = cell.getCoordinate().y;

        Color regionColor = Color.web(stateMap.get(cell.getState()));

        Region addedLeftHex = makeHalfHex(myLeftHex, regionColor);
        Region addedRightHex = makeHalfHex(myRightHex, regionColor);

        myGridPane.add(addedLeftHex, column, row);
        myGridPane.add(addedRightHex, ++column, row);

        makeNodeDynamic(addedLeftHex);
        makeNodeDynamic(addedRightHex);
    }

    private Region makeHalfHex(Shape shape, Color color) {
        Region tempHalfHex = new Region();
        tempHalfHex.setShape(shape);
        Background regionBackground = new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
        tempHalfHex.setBackground(regionBackground);
        return tempHalfHex;
    }

    private void addBuffer(int i, int j) {
        Region addedBuffer = new Region();
        myGridPane.add(addedBuffer, j, i);
        myGridPane.setHgrow(addedBuffer, Priority.ALWAYS);
        myGridPane.setVgrow(addedBuffer, Priority.ALWAYS);
    }

    private void makeNodeDynamic(Node region) {
        myGridPane.setHgrow(region, Priority.ALWAYS);
        myGridPane.setVgrow(region, Priority.ALWAYS);
    }

    private Shape getRightHex() {
        Polygon rightHex = new Polygon();
        rightHex.getPoints().addAll(0.0, 0.0,
                100.0, 0.0,
                200.0, 200.0,
                100.0, 400.0,
                0.0, 400.0);
        return rightHex;
    }

    private Shape getLeftHex() {
        Polygon leftHex = new Polygon();
        leftHex.getPoints().addAll(0.0, 0.0,
                -100.0, 0.0,
                -200.0, 200.0,
                -100.0, 400.0,
                0.0, 400.0);
        return leftHex;
    }
}