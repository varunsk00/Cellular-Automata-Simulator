package cellsociety;

import javafx.scene.paint.Color;

public class Cell {

  private Color color;
  private String state;
  private int lives;

  /**
   * Constructs a new cell
   *
   * @param color the initial color for cell
   * @param state the initial state for cell Sets the cell's instance variables using update()
   **/
  public Cell(Color color, String state) {
    this.lives = 1;
    update(color, state);
  }

  /**
   * Updates instance variables of cell
   *
   * @param color the new color for cell
   * @param state the new state for cell Sets the cell's instance variables to new values
   **/
  public void update(Color color, String state) {
    this.color = color;
    this.state = state;
  }

  /**
   * @return the cell's current color
   */
  public Color getColor() {
    return this.color;
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
   * @return the cell's current state
   **/
  public String getState() {
    return this.state;
  }
}