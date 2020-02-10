package cellsociety.Models.Cells;

import java.awt.Point;

public class Cell {

  private String state;
  private String nextState;
  private Point coordinate;


  /**
   * Constructs a new cell
   *
   * @param state the initial state for cell Sets the cell's instance variables using update()
   **/
  public Cell(String state, int x, int y) {
    this.state = state;
    this.nextState = state;
    this.coordinate = new Point(x, y);
  }

  public String getNextState() {
    return nextState;
  }

  public void setNextState(String nextState) {
    this.nextState = nextState;
  }

  /**
   * Get current of the cell
   *
   * @return a point coordinate
   */
  public Point getCoordinate() {
    return this.coordinate;
  }

  public int getX(){
    return this.coordinate.x;
  }

  public int getY(){
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