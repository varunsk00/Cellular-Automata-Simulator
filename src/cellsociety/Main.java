package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static double SCENE_WIDTH = 400;
    private static double SCENE_HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) {
        /**
         * Begins our JavaFX application
         * Gets the current grid and sets the stage to a scene with that grid
         */
        primaryStage.setTitle("Simulation");
        startAnimationLoop();

        Grid grid = new Grid(20,20);
        Scene scene = grid.getScene(SCENE_WIDTH,SCENE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startAnimationLoop(){
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step(double elapsedTime){
        // TODO Add animations
    }
}
