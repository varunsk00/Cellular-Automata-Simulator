package cellsociety;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Visualizer {

    private double SIZE_X;
    private double SIZE_Y;

    private boolean playPressed;
    private boolean speedUpPressed;
    private boolean skipPressed;

    private final static Color HEADER_COLOR =  Color.LIGHTBLUE;

    /**
     * Creates a Visualizer Object that controls GUI
     * @param x is horizontal size of simulator
     * @param y is  vertical size of simulator
     */
    public Visualizer(double x, double y) {
        this.SIZE_X = x;
        this.SIZE_Y = y;
        this.playPressed = false;
        this.skipPressed = false;
        this.speedUpPressed = false;
    }

    /**
     * Creates the first screen scene in the simulator
     * @return a Group of Nodes to be added that contain the user interaction buttons
     */
    public Group createSimulator() {
        Group returnedGroup = new Group();

        Button playButton = new Button("Play!");
        playButton.setLayoutX(0);
        playButton.setLayoutY(0);
        playButton.setPrefSize((3 *SIZE_X) / 10, SIZE_Y / 10);
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playPressed = true;
            }
        });

        Button speedUpButton = new Button("Speed Up");
        speedUpButton.setLayoutX((3 * SIZE_X) / 10);
        speedUpButton.setLayoutY(0);
        speedUpButton.setPrefSize((4 * SIZE_X) / 10, SIZE_Y / 10);

        speedUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                speedUpPressed = true;
            }
        });

        Button skipButton = new Button("Skip Ahead");
        skipButton.setLayoutX((7 * SIZE_X) / 10);
        skipButton.setLayoutY(0);
        skipButton.setPrefSize((3 * SIZE_X) / 10, SIZE_Y / 10);

        skipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    skipPressed = true;
            }
        });

        returnedGroup.getChildren().add(speedUpButton);
        returnedGroup.getChildren().add(skipButton);
        returnedGroup.getChildren().add(playButton);

        return returnedGroup;
    }

    public Group createGrid(Grid Grid) {

    }

}
