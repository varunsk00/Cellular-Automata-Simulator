package cellsociety.Visuals;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class Footer {

    private ResourceBundle myResources;

    private static double MIN_FRAME_SPEED = 1;
    private static int MAX_FRAME_SPEED = 10;
    private static int DEFAULT_FRAME_SPEED = 1;

    private static int MIN_SKIP = 1;
    private static int MAX_SKIP = 10;
    private static int DEFAULT_SKIP = 1;

    private static final String RESOURCES  = "Resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";

    private Slider frameSpeed;
    private Slider skipJump;

    public Footer(double sceneHeight, double sceneWidth, String language) {

        this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    }

    public VBox renderFooter() {
        VBox footer = new VBox();

        HBox labels = new HBox();
        Label speed = new Label(myResources.getString("SpeedSlider"));
        speed.setMaxWidth(Double.MAX_VALUE);
        speed.setAlignment(Pos.CENTER);
        Label skip = new Label(myResources.getString("SkipSlider"));
        skip.setAlignment(Pos.CENTER);
        skip.setMaxWidth(Double.MAX_VALUE);

        labels.getChildren().addAll(speed, skip);
        labels.setHgrow(speed, Priority.ALWAYS);
        labels.setHgrow(skip, Priority.ALWAYS);
        footer.getChildren().add(labels);

        HBox sliders = new HBox();
        frameSpeed = new Slider(MIN_FRAME_SPEED,  MAX_FRAME_SPEED, DEFAULT_FRAME_SPEED);
        setSliderTicks(frameSpeed);
        skipJump = new Slider(MIN_SKIP, MAX_SKIP, DEFAULT_SKIP);
        setSliderTicks(skipJump);

        sliders.getChildren().addAll(frameSpeed, skipJump);
        sliders.setHgrow(frameSpeed, Priority.ALWAYS);
        sliders.setHgrow(skipJump, Priority.ALWAYS);

        footer.getChildren().add(sliders);

        return footer;
    }

    private void setSliderTicks(Slider slider) {
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
    }

    public double getSpeed() {
        return frameSpeed.getValue();
    }

    public double getJumpValue() {
        return skipJump.getValue();
    }

}
