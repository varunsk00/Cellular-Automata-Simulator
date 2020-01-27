package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * Start of the program.
     */
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static double SCENE_WIDTH = 400;
    public static double SCENE_HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulation");
        startAnimationLoop();

        Group root = new Group();
        Scene scene = new Scene(root, SCENE_WIDTH,SCENE_HEIGHT);
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

    }
}
