package cellsociety;

import javafx.scene.paint.Color;

public class Cell {
    private Color color;
    private String state;

    public Cell (Color color, String state){
        /**
         * Constructs a new cell
         * @param color the initial color for cell
         * @param state the initial state for cell
         * Sets the cell's instance variables using update()
         **/
        update(color,state);

    }

    public void update(Color color, String state){
        /**
         * @param color the mew color for cell
         * @param state the new state for cell
         * Sets the cell's instance variables to new values
         **/
        this.color = color;
        this.state = state;
    }

    public Color getColor(){
        /**
         * @return the cell's current color
         **/
        return this.color;
    }

    public String getState(){
        /**
         * @return the cell's current state
         **/
        return this.state;
    }
}
