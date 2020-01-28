package cellsociety;

import javafx.scene.paint.Color;

public class Cell {

  private Color color;
  private String state;

  /**
   * Constructs a new cell
   *
   * @param color the initial color for cell
   * @param state the initial state for cell Sets the cell's instance variables using update()
   **/
  public Cell(Color color, String state) {
    update(color, state);

  }

  /**
   * @param color the mew color for cell
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
   * @return the cell's current state
   **/
  public String getState() {
    return this.state;
  }
}
