# Simulation Design Plan
### Team Number 04
### Names: Eric Doppelt, Varun Kosgi, Jaidha Rosenblatt
### NetIds: ead45, vsk10, jrr59 

## Introduction
The Cellular Automata is a simulation consisting of multiple cells in a grid that can interact with their neighbors to change their states. The purpose of our program will be to present the user with a Graphical User Interface, load a Configuration file that dictates the rules for the simulation, run the aforementioned simulation, and present an indefinite visualization of the constantly updating grid.

The focus of our design is to create a flexible "open" structure that supports adding new types of simulations without having to change significant functionality of the Main class. We will keep the implementation of the grid and cells "closed" so that it can only be updated by adding new rules to our simulation.

## Overview

We plan on incorporating three major class types in our project. We will have a Cell class that holds a state and a color. An abstract Grid class be a 2D array of Cell objects. We will inherit the abstract Grid class for each of the simulation types and use it to set the specific rules for each simulation type. In the main, we load in the XML file, use it to set the current Grid type and control the animation of grid.

## User Interface

The User Interface will consist of a JavaFX scene with an option to load a specific .xml configuration file. The user will be presented with a button to press that will allow them to browse their computer for a .xml configuration file. If the configuration file is not in the valid format, the user will get an error message prompting them to choose a file that is correctly formatted. The user will also be able to pause and play the simulation at will. They will also be an option to speed up the simulation and skip forward.

![GUI Design](GUI_Design.png)

## Design Details

#### Components

 - Cell Class
     - Contains information for the state of a given cell
         - This could be encoded in a variety of types of instance variables, such as ints, Strings, or JavaFX Rectangle, for example.
     - Methods
         - public void updateCell(Color color, String state)
             - Update the color and state of a cell
         - public String getState()
             - Get the current state of the cell
         - public Color getColor()
             - Get the current color of the cell
     - To shift responsibility off of the Grid class, Cells can store their Rectangle visualization in JavaFX and color their own Rectangle to be displayed in

 - Abstract Grid Class
     - A 2D array of Cell objects
     - Each type of simulation is a new class that inherits the abstract Grid class 
     - Returns a JavaFX scene from the grid that represents the current state of the simulation
         - Could return either a Collection of Cells or a Scene to be added to the root
    - Methods:
        - public Grid() (constructor)
            - Initializes a 2D array based on loaded configuration
        - public Cell[][] getGrid()
            - Returns 2d Array
        - public abstract updateGrid()
            - Runs rules once and changes state of each cell
            - Coded in subclasses
            - Call either handleMiddleCell() or handleEdgeCell() depending on the indices for the array
        - public abstract handleMiddleCell()
            - Updates the state of a cell in the middle of the grid
        - public abstract handleEdgeCell()
         - Updates the state of a cell on the edge of the grid
         - Must be able to catch a null pointer exception
    

 - Concrete Grid subclasses
     - Extends Grid class and represents a distinct type of simulation (fire spread, population, water flow)
     - Constructor takes an XML file as a parameter which contains information that is set to instance variables for the Grid Type
     - Changes updateState() method in Grid class based on rules of simuation

 - Main Class
     - Constantly updates the simulation with inputs from other private classes (Simulation loop)
     - myGrid instance variable
     - Uses a JavaFX environment to present a GUI for the user
     - Handles XML file and calls appropriate grid object
     - Methods
         - private void step()
             - Uses an animation timer to run simulation
             - Calls updateGrid()
         - private Grid loadGrid(File file)
             - Read first line of the file to determine simulation type
             - Create Grid type

#### Use Cases

*Use Case One*
1. Create a GameOfLife grid class that inherits abstract Grid
2. call updateGrid() on whole grid
3. while looping through grid, handleMiddleCell() is called due to indices within array

*Use Case Two*
1. Create a GameOfLife grid class that inherits abstract Grid
2. call updateGrid() on whole grid
3. while looping through grid, handleEdgeCell() is called due to indices within array

*Use Case Three*

1. in the Main classes step() function, first call gridType.updateGrid()
2. updateGrid() iterates through each cell calling either handleMiddleCell() or handleEdgeCell()
3. handleMiddleCell() or handleEdgeCell() calls Cell.update() with the current state and color.
4. After each cells state has been updated, the step() function then adds the Scene returned from grid.getScene() to the root to be displayed

*Use Case Four*
1. In the Main start() method, read in the Simulation Type from the XML file.
2. Based on the type, create a Grid subclass of the simulation type and pass in the XML file as a parameter
3. The Grid subclass constructor parses the XML and sets instanceVariable myProbCatchFire equal to the value

*Use Case Five*
1. Click open on the GUI
2. Select new XML file
3. call loadGrid() function with file as parameter
4. in loadGrid(), read the type of Grid dictated by XML, create that grid subclass with XML file as parameter to encode variables like probCatch and initial state
5. set new grid object to myGrid and begin running step


## Design Considerations

During discussion, the group decided on giving the Grid subclasses a large amount of functionality, including converting a grid to a JavaFX Scene, loading an XML file configuration, and updating the cells within a grid. From an object-oriented perspective, we believed that it made the most sense for the Grid object to handle these tasks. The Grid is the only object with access to every cell, the array of cells, and the ability to update cells. It made the most sense that the XML file could define the size of the grid as part of the grid constructor and the grid itself has the ability to return its scene. We discussed how this may be giving too much functionality to a single class and may result in a code smell (long class). As we continue to develop, we may have to offload these functionalities into other classes to improve our code sign and readability.

We also spent time considering the interaction between the Cell and Grid Classes. We decided to make Cell an object with enough functionality to update its own state and store its updated state. We considered relegating this function to the Grid class, but decided against it in order to make the Cell class more actively involved within the program instead of simply being a variable storage vessel. We decided on making the Grid class abstract since a Grid Object must have certain rules before being initialized. The Grid class simply provides methods intrinsic to all Grids. The Grid subclasses, however, are instantiable and inherit all the method from the Grid class, since they have loaded their configurations and have set rules.

## Team Responsibilities

 * Team Member #1: Varun
 
    Primary
    * **Figuring out grid creation**
    * **Neighboring cells w/n grid and Edge Cases**


   Secondary
    * *Loading XML Config*
    * *Convert grid to visualization*

 * Team Member #2: Jaidha
    Primary
    * **Setup Main classes and game loop**
    * **Loading XML Config**
    * **Cell class**


   Secondary
    * *GUI development*
    * *Segregation, predator-prey, and fire*

    
 * Team Member #3: Eric
    Primary
    * **Convert grid to visualization**
    * **GUI development**


   Secondary
    * *Neighboring cells w/n grid*
    * *Segregation, predator-prey, and fire*