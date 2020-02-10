package cellsociety.Controllers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

/**
 * SliderControls class serves as a controller unit, allowing the user to input two variables: simulation speed and number of frames to skip.
 * SliderControls works in conjunction with ButtonControls via the CAController class. Once a value for Frames to Skip has been set in the SliderControls through a Slider,
 * the CAController has an encapsulated ButtonControls class with a Button to press to add the effect of the value into the simulation.
 * Display is dynamic and shrinks/grows to increases/decreases in the width of the Window
 * Every simulation needs a SliderControls, which should be instantiated in the start() method in Main and added to the bottom of the BorderPane
 * @author Eric Doppelt
 */
public class SliderControls {

    private ResourceBundle myResources;

    private static final int MIN_FRAME_SPEED = 1;
    private static final int MAX_FRAME_SPEED = 5;
    private static int DEFAULT_FRAME_SPEED = 1;


    private static final int MIN_SKIP = 1;
    private static final int MAX_SKIP = 10;
    private static final int DEFAULT_SKIP = 1;

    private Slider frameSpeed;
    private Slider framesSkipped;

    private VBox footer;

    /**
     * Constructor for a SliderControls that sets the language of the Footer and calls renderFooter()
     * renderFooter() adds Labels and Sliders to set values as mentioned in class description
     * @param language is the String that represents the language for the ResourceBundle
     */
    public SliderControls(String language) {
        this.myResources = ResourceBundle.getBundle(language);
        renderFooter();
    }

    /**
     * Basic getter method that returns the value from the Slider determining the speed of the simulation
     * @return the value in the frameSpeed slider
     */
    public double getSpeed() {
        return frameSpeed.getValue();
    }

    /**
     * Basic getter method that returns the value from the Slider determining the number of frames to skip ahead
     * @return the value in the framesSkipped slider
     */
    public double getSkipValue() {
        return framesSkipped.getValue();
    }

    /**
     * Basic getter method that returns the Footer VBox containing the Labels and Sliders
     * @return the VBox private instance variable footer
     */
    public VBox getFooter() {
        return footer;
    }

    private void renderFooter() {
        footer = new VBox();

        HBox allLabels = new HBox();
        addLabel("SpeedSlider", allLabels);
        addLabel("SkipSlider", allLabels);

        //addReturnSlider() adds the Sliders to the frame and then returns them as instance variables for later access
        HBox allSliders = new HBox();
        frameSpeed = addAndReturnSlider(MIN_FRAME_SPEED, MAX_FRAME_SPEED, DEFAULT_FRAME_SPEED, allSliders);
        framesSkipped = addAndReturnSlider(MIN_SKIP, MAX_SKIP, DEFAULT_SKIP, allSliders);

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