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

    private static int MIN_FRAME_SPEED = 1;
    private static int MAX_FRAME_SPEED = 5;
    private static int DEFAULT_FRAME_SPEED = 1;


    private static int MIN_SKIP = 1;
    private static int MAX_SKIP = 10;
    private static int DEFAULT_SKIP = 1;

    private static final String RESOURCES  = "Resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";

    private Slider frameSpeed;
    private Slider skipJump;

    private VBox footer;

    public Footer(String language) {
        this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        renderFooter();
    }

    public double getSpeed() {
        return frameSpeed.getValue();
    }

    public double getJumpValue() {
        return skipJump.getValue();
    }

    public VBox getFooter() {return footer;}

    private void renderFooter() {
        footer = new VBox();

        HBox allLabels = new HBox();
        addLabel("SpeedSlider", allLabels);
        addLabel("SkipSlider", allLabels);

        //addReturnSlider() adds the Sliders to the frame and then returns them as instance variables for later access
        HBox allSliders = new HBox();
        frameSpeed = addAndReturnSlider(MIN_FRAME_SPEED, MAX_FRAME_SPEED, DEFAULT_FRAME_SPEED, allSliders);
        skipJump = addAndReturnSlider(MIN_SKIP, MAX_SKIP, DEFAULT_SKIP, allSliders);

        footer.getChildren().add(allLabels);
        footer.getChildren().add(allSliders);
    }

    private void addLabel(String key, HBox text) {
        Label tempLabel = new Label(myResources.getString(key));
        tempLabel.setMaxWidth(Double.MAX_VALUE);
        tempLabel.setAlignment(Pos.CENTER);
        text.setHgrow(tempLabel, Priority.ALWAYS);
        text.getChildren().add(tempLabel);
    }

    private Slider addAndReturnSlider(int min, int max, int def, HBox sliders) {
        Slider tempSlider = new Slider(min, max, def);
        sliders.setHgrow(tempSlider, Priority.ALWAYS);
        setSliderTicks(tempSlider);
        sliders.getChildren().add(tempSlider);
        return tempSlider;
    }

    private void setSliderTicks(Slider slider) {
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
    }
}
