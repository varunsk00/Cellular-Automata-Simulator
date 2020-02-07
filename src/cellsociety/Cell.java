package cellsociety;

import javafx.scene.paint.Color;

public class Cell {

  private Color color;
  private String state;
  private int lives;

  /**
   * Constructs a new cell
   *
   * @param state the initial state for cell Sets the cell's instance variables using update()
   **/
  public Cell(String state) {
    this.lives = 1;
    this.state = state;
  }

  /**
   * Updates instance variables of cell
   *
   * @param state the new state for cell Sets the cell's instance variables to new values
   **/
  public void updateState(String state) {
    this.state = state;
  }

  /**
   * @return the cell's current lives
   */
  public int getLives() {
    return lives;
  }

  /**
   * update the cell's lives based on a given change
   *
   * @param change an int representing the desired change for lives
   */
  public void updateLives(int change) {
    this.lives = this.lives + change;
  }

  /**
   * set a cell's lives
   *
   * @param lives an int representing the lives to set to our cell
   */
  public void setLives(int lives) {
    this.lives = lives;

  }

  /**
   * @return the cell's current state
   **/
  public String getState() {
    return this.state;
  }
}