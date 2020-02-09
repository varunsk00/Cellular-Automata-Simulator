package cellsociety.Visuals;

import cellsociety.Models.Grids.Grid;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import java.util.ResourceBundle;

/**
 * Converts a Grid object to a dynamic GridPane object that can be displayed in Main
 * GridPane is dynamic in size and changes based on changes to the size of the window
 * GridPane does not store a Grid, but future implementations could make GridPane instance variable mutable
 * to prevent clearing the GridPane on every update() call
 * @authors Eric Doppelt, Jaidha Rosenblatt
 */
public class HexGridView {

    private GridPane myGridPane;
    private ResourceBundle myResources;
    private Shape myRightHex;
    private Shape myLeftHex;

    /**
     * Basic constructor for a GridView object
     * Takes no parameters but creates an instance that can be used later to call updateGrid()
     * Future variations can store a grid to the GridPane to be updated for efficiency purposes
     * Creates the GridPane and sets Horizontal and Vertical Gaps to 1
     */
    public HexGridView(String language) {
        myResources = ResourceBundle.getBundle(language);
        myGridPane = new GridPane();
        myRightHex = getRightHex();
        myLeftHex = getLeftHex();
    }

    /**
     * Basic getter method to get the GridPane object
     *
     * @return myGridPane instance variable representing the last grid passed through update
     */
    public GridPane getGridPane() {
        return myGridPane;
    }

    /**
     * Method provides the functionality of the Grid View class
     * Clears instance variable myGridPane and updates it to show the grid parameter
     * Dynamically sized to fit size of center in BorderPane in Main
     * Called in Main every time the grid updates through the step() function
     *
     * @param grid takes in a grid to be represented via a GridPane
     */
    public void updateGridView(Grid grid) {

        myGridPane.getChildren().clear();

        boolean frontBuffer = false;

        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getColumns(); j++) {
                if (j == 0 && i % 2 != 0) {
                    Region addedBuffer = new Region();
                    addedBuffer.setBackground(new Background(new BackgroundFill((Paint)Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
                    myGridPane.add(addedBuffer, j, i);
                    myGridPane.setHgrow(addedBuffer, Priority.ALWAYS);
                    myGridPane.setVgrow(addedBuffer, Priority.ALWAYS);
                    System.out.println("FRONTBUFFER: " + i + " , " + j);
                    frontBuffer =  true;
                }

                Region addedLeftHex = new Region();
                addedLeftHex.setShape(myLeftHex);

                Region addedRightHex = new Region();
                addedRightHex.setShape(myRightHex);


                Color regionColor = Color.web(myResources.getString(grid.current(j, i).getState()));
                Background regionBackground = new Background(new BackgroundFill(regionColor, CornerRadii.EMPTY, Insets.EMPTY));
                addedLeftHex.setBackground(regionBackground);
                addedRightHex.setBackground(regionBackground);

                int x = 2 * j;
                if (frontBuffer) x++;

                myGridPane.add(addedLeftHex, x, i);
                myGridPane.setHgrow(addedLeftHex, Priority.ALWAYS);
                myGridPane.setVgrow(addedLeftHex, Priority.ALWAYS);


                myGridPane.add(addedRightHex, ++x, i);
                myGridPane.setHgrow(addedRightHex, Priority.ALWAYS);
                myGridPane.setVgrow(addedRightHex, Priority.ALWAYS);


                if (j == grid.getColumns() - 1 && i % 2 == 0) {
                    Region addedBuffer = new Region();
                    addedBuffer.setBackground(new Background(new BackgroundFill((Paint)Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
                    myGridPane.add(addedBuffer, 2*j + 2, i);
                    myGridPane.setHgrow(addedBuffer, Priority.ALWAYS);
                    myGridPane.setVgrow(addedBuffer, Priority.ALWAYS);
                    System.out.println("BACKBUFFER: " + i + " , " + (int)(j + 1));
                }
            }
            frontBuffer = false;
        }
    }

    // SOURCE: https://www.tutorialspoint.com/javafx/2dshapes_polygon.htm
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