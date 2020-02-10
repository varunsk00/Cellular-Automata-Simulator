package cellsociety.Controllers;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.util.ResourceBundle;

/**
 * Header Class serves as a controller unit, taking in input from the user to pause/play the game,
 * skip ahead, clear the simulations, or load a file. It is displayed at the top of the BorderPane
 * in CAController and has a HBox with Buttons that affect the game Display is dynamic and
 * grows/shrinks to increasing/decreasing the width of the window Works in conjunction with the
 * Footer class, since the Footer class determines the number of frames to jump Every simulation
 * needs a header, which should be instantiated in CAController and added to the top of the
 * BorderPane
 *
 * @author Eric Doppelt
 */
public class ButtonControls {

  private ResourceBundle myResources;

  private boolean playPressed;
  private boolean skipPressed;
  private boolean loadPressed;
  private boolean clearPressed;

  private HBox myButtons;
  private Button playButton;

  /**
   * Basic constructor for a header Sets its ResourceBundle to the one specified by the given
   * language Sets all instance variables to false, indicating that no buttons have been pressed
   * Calls renderHeader() which creates the HBox with Buttons Assumes that language exists in
   * Resources folder, otherwise throws an InvocationTargetException error
   *
   * @param language is the language of the ResourceBundle
   */
  public ButtonControls(String language) {
    myResources = ResourceBundle.getBundle(language);
    this.playPressed = false;
    this.skipPressed = false;
    this.loadPressed = false;
    this.clearPressed = false;
    renderHeader();
  }

  /**
   * Basic getter method for the header used in Main
   *
   * @return myHeader which is the HBox private instance variable representing the header (with
   * functional buttons)
   */
  public HBox getHeader() {
    return myButtons;
  }

  /**
   * Basic getter method returning whether or not to play the simulation This triggers the
   * updateStatus() method in main if playPressed is true
   *
   * @return the boolean private instance variable playPressed
   */
  public boolean getPlayStatus() {
    return playPressed;
  }

  /**
   * Basic getter method returning whether or not to load a file This triggers the handleXML() in
   * CAController
   *
   * @return the boolean private instance variable loadPressed
   */
  public boolean getLoadStatus() {
    return loadPressed;
  }

  /**
   * Basic getter method returning whether or not to skip ahead in the game This triggers the
   * skipAhead() method in CAController
   *
   * @return the boolean private instance variable skipPressed
   */
  public boolean getSkipStatus() {
    return skipPressed;
  }

  /**
   * Method that pauses the simulation If game is playing, sets playPressed to false and makes
   * playButton display Play key Called when loading a file to pause the game Does nothing if game
   * is already Paused
   */
  public void togglePause() {
    if (playPressed) {
      playButton.fire();
    }
  }

  /**
   * Basic setter method that sets the loadPressed variable to false Called in handleXML() method to
   * stop the load screen from rerendering
   */
  public void setLoadOff() {
    loadPressed = false;
  }

  /**
   * Basic setter method that sets the skipPressed variable to false Called in skipAhead() method to
   * only skip once
   */
  public void setSkipOff() {
    skipPressed = false;
  }

  /**
   * Basic getter method to get the value of clearPressed instance variable Used to determine
   * whether or not to clear the simulations
   *
   * @return clearPressed instance variable
   */
  public boolean getClearStatus() {
    return clearPressed;
  }

  /**
   * Basic setter method to set the value of clearPressed to false Called after the Grids have
   * already been cleared in CAController
   */
  public void setClearOff() {
    clearPressed = false;
  }

  private void renderHeader() {
    myButtons = new HBox();
    playButton = makePlayButton();
    Button loadButton = makeButton("LoadButton", event -> loadPressed = true);
    Button skipButton = makeButton("SkipButton", event -> skipPressed = true);
    Button clearButton = makeButton("ClearButton", event -> clearPressed = true);

    myButtons.getChildren().addAll(loadButton, playButton, skipButton, clearButton);

    formatButton(playButton);
    formatButton(loadButton);
    formatButton(skipButton);
    formatButton(clearButton);
  }

  private Button makePlayButton() {
    Button tempButton = new Button(myResources.getString("PlayButton"));
    tempButton.setOnAction(e -> {
      if (playPressed) {
        tempButton.setText(myResources.getString("PlayButton"));
      } else {
        tempButton.setText(myResources.getString("PauseButton"));
      }

      playPressed = !playPressed;
    });
    tempButton.setMaxWidth(Double.MAX_VALUE);
    return tempButton;
  }

  private Button makeButton(String key, EventHandler e) {
    Button tempButton = new Button(myResources.getString(key));
    tempButton.setMaxWidth(Double.MAX_VALUE);
    tempButton.setOnAction(e);
    return tempButton;
  }

  private void formatButton(Button tempButton) {
    myButtons.setHgrow(tempButton, Priority.ALWAYS);
  }
}