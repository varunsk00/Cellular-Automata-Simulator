package cellsociety.Models;

import java.awt.Point;

public class Cell {

  private String state;
  private int lives;
  private Point coordinate;

  /**
   * Constructs a new cell
   *
   * @param state the initial state for cell Sets the cell's instance variables using update()
   **/
  public Cell(String state, int x, int y) {
    this.lives = 1;
    this.state = state;
    this.coordinate = new Point(x, y);
  }

  /**
   * Get current of the cell
   *
   * @return a point coordinate
   */
  public Point getCoordinate() {
    return coordinate;
  }

  /**
   * Update the current coordinate
   *
   * @param point new position to update to
   */
  public void setCoordinate(Point point) {
    this.coordinate = point;
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