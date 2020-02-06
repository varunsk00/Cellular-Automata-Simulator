package cellsociety.Visuals;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.ResourceBundle;

/**
 * Header Class serves as a controller unit, taking in input from the user to pause/play the game, skip ahead, speed up, or load a file.
 * It is displayed at the top of the BorderPane in Main and has a VBox with Buttons that affect the game and author/title info
 * Display is dynamic and grows/shrinks to increasing/decreasing the width of the window
 * Works in conjunction with the Footer class, since the Footer class determines new speed/frames to jump
 * Every simulation needs a header, which should be instantiated in Main and added to the top of the BorderPane
 * @author Eric Doppelt
 */
public class Header {

  private static final String RESOURCES  = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = "../../../" + RESOURCES + "/";

  private static final String AUTHOR_INTRO = "Author: ";
  private static final String TITLE_INTRO = "Title: ";
  private ResourceBundle myResources;

  private boolean playPressed;
  private boolean skipPressed;
  private boolean loadPressed;

  private HBox myButtons;
  private Button playButton;

  /**
   * Basic constructor for a header
   * Sets its ResourceBundle to the one specified by the given language
   * Sets all instance variables to false, indicating that no buttons have been pressed
   * Calls renderHeader() which creates the HBox with Buttons and Empty Author/Title info for Main
   * Assumes that language exists in Resources folder, otherwise throws an InvocationTargetException error
   * @param language is the language of the ResourceBundle
   */
  public Header(String language) {

    myResources = ResourceBundle.getBundle(language);
    this.playPressed = false;
    this.skipPressed = false;
    this.loadPressed = false;

    renderHeader();
  }

  /**
   * Basic getter method for the header used in Main
   * @return myHeader which is the VBox private instance variable representing the header (with functional buttons and title/author info)
   */
  public HBox getHeader() {return myButtons;}

  /**
   * Basic getter method returning whether or not to play the simulation
   * This triggers the updateStatus() method in main if playPressed is true
   * @return the boolean private instance variable playPressed
   */
  public boolean getPlayStatus() {return playPressed;}

  /**
   * Basic getter method returning whether or not to load a file
   * This triggers the xmlToGrid() in Main
   * @return the boolean private instance variable loadPressed
   */
  public boolean getLoadStatus() {return loadPressed;}

  /**
   * Basic getter method returning whether or not to skip ahead in the game
   * This triggers the skipAhead() method in main
   * @return the boolean private instance variable skipPressed
   */
  public boolean getSkipStatus() {return skipPressed;}

  /**
   * Basic getter method returning whether or not to speed/slow the game down the simulation
   * This triggers the updateSpeed() method in main
   * @return the boolean private instance variable speedPressed
   */

  /**
   * Method that pauses the simulation
   * If game is playing, sets playPressed to false and makes playButton display Play key
   * Called when loading a file to pause the game
   * Does nothing if game is already Paused
   */
  public void togglePause() {
    if (playPressed) playButton.fire();
  }

  /**
   * Basic setter method that sets the loadPressed variable to false
   * Called in handleXML() method to stop the load screen from rerendering
   */
  public void setLoadOff() {loadPressed = false;}

  /**
   * Basic setter method that sets the skipPressed variable to false
   * Called in skipAhead() method to only skip once
   */
  public void setSkipOff() {skipPressed = false;}

  /**
   * Basic setter method that sets the speedPressed variable to false
   * Called in updateSpeed() to stop the speed from constantly being set to the slider value
   */

  private void renderHeader() {
    myButtons = new HBox();
    playButton = makePlayButton();
    Button loadButton = makeButton("LoadButton", event -> loadPressed = true);
    Button skipButton = makeButton("SkipButton", event -> skipPressed = true);

    myButtons.getChildren().addAll(loadButton, playButton, skipButton);
    formatButtons(loadButton, playButton, skipButton);
  }

  private void addLabel(String info, HBox header) {
    Label addedLabel = new Label(info);
    addedLabel.setMaxWidth(Double.MAX_VALUE);
    addedLabel.setAlignment(Pos.CENTER);
    header.setHgrow(addedLabel, Priority.ALWAYS);
    header.getChildren().add(addedLabel);
  }

  private Button makePlayButton() {
    Button tempButton = new Button(myResources.getString("PlayButton"));
    tempButton.setOnAction(e -> {
      if (playPressed) tempButton.setText(myResources.getString("PlayButton"));
      else tempButton.setText(myResources.getString("PauseButton"));

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

  private void formatButtons(Button load, Button skip, Button play) {
    myButtons.setHgrow(load, Priority.ALWAYS);
    myButtons.setHgrow(skip, Priority.ALWAYS);
    myButtons.setHgrow(play, Priority.ALWAYS);
  }
}