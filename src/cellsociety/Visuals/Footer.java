package cellsociety.Visuals;

import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ResourceBundle;

public class Footer {

    private double sceneWidth;
    private ResourceBundle myResources;

    private static int MIN_FRAME_SPEED = 1;
    private static int MAX_FRAME_SPEED = 10;
    private static int DEFAULT_FRAME_SPEED = 1;

    private static int MIN_SKIP = 1;
    private static int MAX_SKIP = 10;
    private static int DEFAULT_SKIP = 1;

    private static final String RESOURCES  = "Resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";

    public Footer(double sceneWidth, String language) {
        this.sceneWidth = sceneWidth;
        this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    }

    public HBox renderFooter() {
        HBox footer = new HBox();
        footer.setPrefWidth(Double.MAX_VALUE);

        Slider frameSpeed = new Slider(MIN_FRAME_SPEED,  MAX_FRAME_SPEED, DEFAULT_FRAME_SPEED);
        Slider skipJump = new Slider(MIN_SKIP, MAX_SKIP, DEFAULT_SKIP);

        footer.getChildren().add(frameSpeed);
        footer.setHgrow(frameSpeed, Priority.ALWAYS);

        footer.getChildren().add(skipJump);
        footer.setHgrow(skipJump, Priority.ALWAYS);

        return footer;
    }

}
