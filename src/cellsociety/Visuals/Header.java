package cellsociety.Visuals;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.util.ResourceBundle;

/**
 * Header Class serves as a controller unit, taking in input from the user to pause/play the game, skip ahead, speed up, or load a file.
 * Works in conjunction with the Footer class, since the Footer class determines new speed/frames to jump
 *
 */
public class Header {

  private static final String RESOURCES  = "Resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";

  private ResourceBundle myResources;

  private boolean playPressed;
  private boolean speedUpPressed;
  private boolean skipPressed;
  private boolean loadPressed;

  private HBox myHeader;

  public Header(String language) {

    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    this.playPressed = false;
    this.skipPressed = false;
    this.speedUpPressed = false;
    this.loadPressed = false;

    renderHeader();
  }

  public HBox getHeader() {return myHeader;}

  public boolean getPlayStatus() {return playPressed;}

  public boolean getLoadStatus() {return loadPressed;}

  public boolean getSkipStatus() {return skipPressed;}

  public boolean getSpeedStatus() {return speedUpPressed;}

  public void setLoadOff() {loadPressed = false;}

  public void setSkipOff() {skipPressed = false;}

  public void setSpeedOff() {speedUpPressed = false;}

  private void renderHeader() {
    myHeader = new HBox();

    Button playButton = makePlayButton();

    Button loadButton = makeButton("LoadButton", event -> loadPressed = true);
    Button skipButton = makeButton("SkipButton", event -> skipPressed = true);
    Button speedButton = makeButton("SpeedButton", event -> speedUpPressed = true);

    myHeader.getChildren().addAll(loadButton, playButton, speedButton, skipButton);
    formatButtons(loadButton, playButton, speedButton, skipButton);
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

  private void formatButtons(Button load, Button speed, Button skip, Button play) {
    myHeader.setHgrow(load, Priority.ALWAYS);
    myHeader.setHgrow(speed, Priority.ALWAYS);
    myHeader.setHgrow(skip, Priority.ALWAYS);
    myHeader.setHgrow(play, Priority.ALWAYS);
  }
}
