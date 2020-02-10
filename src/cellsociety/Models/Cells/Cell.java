package cellsociety.Models.Cells;

import java.awt.Point;

/**
 * This class represents a single Cell within a simulation.
 *
 * @author Varun Kosgi
 * @author Jaidha Rosenblatt
 */
public class Cell {

  private String state;
  private String nextState;
  private Point coordinate;

  /**
   * Constructs a new cell
   *
   * @param state the initial state for cell Sets the cell's instance variables using update()
   * @param x     x-coordinate of cell
   * @param y     y-coordinate of cell
   **/
  public Cell(String state, int x, int y) {
    this.state = state;
    this.nextState = state;
    this.coordinate = new Point(x, y);
  }

  /**
   * Get the upcoming state of a cell
   *
   * @return the modified state
   */
  public String getNextState() {
    return nextState;
  }

  /**
   * Set the upcoming state of the cell
   *
   * @param nextState the new state that will be accessed by the grid
   */
  public void setNextState(String nextState) {
    this.nextState = nextState;
  }

  /**
   * Get current location of the cell
   *
   * @return a point coordinate
   */
  public Point getCoordinate() {
    return this.coordinate;
  }

  /**
   * Get the X-component of the Point
   *
   * @return the X-coordinate of a cell
   */
  public int getX() {
    return this.coordinate.x;
  }

  /**
   * Get the Y-component of the Point
   *
   * @return the Y-coordinate of a cell
   */
  public int getY() {
    return this.coordinate.y;
  }

  /**
   * Updates instance variables of cell
   *
   * @param state the new state for cell Sets the cell's instance variables to new values
   **/
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the cell's current state
   **/
  public String getState() {
    return this.state;
  }
}