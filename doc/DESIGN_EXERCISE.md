# DESIGN_EXERCISE

###CRC Cards:

*Cell Class*
void updateCell()
String readCell() // returns state which is represented by Strings

Fills the grid class, called to update cell by reading other cells

*Abstract Grid Class*
Information needed in config file:
- Cell interaction with neighbors
- All possible states of cell

Constructor:
- Initializes a 2D array of Cell Objects

Methods:
abstract update() updates the state of every cell using Cell.updateCell() // changes depending on game/grid type
updateCell(int x, int y, String state) updates the state of the cell at position x,y in grid

Used in the Main class

EX:*Fire Grid Class extends Grid Class*
Inherits from Grid Class
.update() method is changed to read neighboring cells states, update cells according to the Fire Rules

*Main Class*

Runs simulation (handles input, updates state continuously)

Creates the Grid in the start() method
Call the grid.update() method in the step() function
Visualizes the grid using JavaFX in the step() function



