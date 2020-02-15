# Simulation Design Final
### Eric Doppelt, Jaidha Rosenbblatt, Varun Kosgi

## Team Roles and Responsibilities

 - Varun Kosgi:
    - Figured out Grid Creation
    - Developed the logic for all Simulations except PredPrey
    - Dealt with Cell inheritance
    - Dealt with Grid inheritance
- Jaidha Rosenblatt:
    - Handled all aspects of XML handling
    - Designed all Custom Errors for XML
    - Created the PredPrey Grid Simulation
    - Created the Pop Up messages for Errors in the GUI
    - Initialized main and cell classes
- Eric Doppelt
    - Handled all aspects in the GUI aside from Error Messages
    - Created the Button/Slider Controllers
    - Created the GridView inheritance to deal with different shapes
    - Created the GraphView class to display stats
    - Created the LoadButton display to hand off the XML file to the backend

**Note**: The aforementioned roles describe which team member was responsible for the implementation and refactoring of each component for the project. The design itself and debugging was more collaborative, with each member taking input from others for their work.


## Design goals
#### What Features are Easy to Add
**Model**
1. Adding new neighbor-checking logic (hexagons, eight neighbors, etc)
2. Creating game logic for a new simlulation with a Grid subclass
3. Creating a new Cell subclass

**Controller**
1. Adding a new Simulation XML file (new types of cells, simulation variables, grid types)
2. Specifying a new initial layout for new and current simulations

**View**
We wanted to make it easy to add new simulations and controls to the display. As such, the following list summarizes new visualization features that are supported:

1. Visualizing multiple running simulations at once
2. Visualizing the stats of multiple simulations at once both:
    a. Via a Header
    b. Via a Popup Graph
3. Adding a New Button to the header
4. Adding a New Slider fo the footer
5. Visualizing Different Shapes (Hexagon v. Rectangular)
6. Adding a new language to the program via a Resource Bundle
7. Adding a new css design

## High-level Design
#### Core Classes
***Model***
- **Cells** - different Cell types that can hold and update different states of information depending on the simulation type
    - **Cell.java**
        - Contains core **Cell** object functionality, such as state updating and current coordinate getting
    - **ForageCell.java**
        - **Cell** subclass with states of Hungry, Full, and pheromone getting/setting
    - **PredPreyCell.java**
        - **Cell** subclass with life information
- **Grids** - different simulation types with separate game logic. They all are based on a double list of **Cell** objects
    - **Grid.java**
        - Contains core Grid functionality shared by all simulations, including neighbor-checking methods, grid initialization, coordinate updating, and simulation updating
    - **FireGrid.java**
        - **Grid** subclass that simulates a wildfire
    - **ForageGrid.java**
        - **Grid** subclass that simulates ants foraging for food from a nest
    - **LifeGrid.java**
        - **Grid** subclass that simulates the Game of Life
    - **PercGrid.java**
        - **Grid** subclass that simulates Percolation
    - **PredPreyGrid.java**
        - **Grid** subclass that simulates Predator/Prey interactions in an environment
    - **RPSGrid.java**
        - **Grid** subclass that simulates Bacterial competition using a Rock-Paper-Scissors style of survival
    - **SegGrid.java**
        - **Grid** subclass that simulatesself-segregation based on neighbor satisfaction

***Controller***
- **Main.java**
    - Creates a new instance of **CAController** to run our program
- **ButtonController.java**
    - Takes input from the user to manipulate the simulations it runs. Allows users to play and pause the simulations, load in a new one, skip frames ahead, and clear all simulations. This class is dependent on the **SliderController** when the SkipAhead button is called, since that returns the number of frames to skip ahead by. 
- **SliderController.java**
    - Takes input from the user to manipulate the running simulations. Allows the user to set the speed of the simulation (which changes the animation rate of the **Timeline**). It also allows users to set a number of frames to skip ahead by in the visualization, which is toggled when the Skip Ahead button is pressed in the **ButtonController** class.
- **CAController.java**
    - Dependent on every Class in cellsociety. Creates a BorderPane that encapsulates a **ButtonControls** on top, a **SliderControls** on Bottom, and multiple **SimulationViews** in the center. It creates a new instance of **XMLParser** and creates a grid based on a selected an XML file. It has an animation timer loop which handles button interaction and updating the grid. Finally, this class also handles errors by rendering a pop-up window with the error message.
- **XMLParser.java**
    - Takes in a XML file and parses it section by section. It also has a getGrid method which creates a new specific grid object by parsing an XML file. There is a two way dependency on this class with the **Grid** class and a dependency with the **XMLException** class.
- **XMLException.java**
    - Custom exception class taken from lecture. Is called by **XMLParser** and Grid class to handle XML related errors.

***Visuals/View***
- **GridView.java**
    - This class is abstract and extended in **HexGridView** and **RectGridView**. It provides a basic template to display a Grid, which is inputted in an the *update()* method which returns a GridPane representing the visualized Grid.
    - **HexGridView.java**
        - Displays a Grid passed in with Hexagonal cells. The Grid is dynamic and grows or shrinks with a moving window.
    - **RectGridView.java**
        - Displays a Grid passed in with Rectangular cells. The Grid is dynamic and grows or shrinks with a moving window.
- **StatView.java**
    - This class displays header that is attached to a running simulation. Using labels, it displays the title, author, and count of every state that is currently displayed in the Grid View's simulation that it is attached to. This class is dynamic and the statview stretches to fill the width of a window.
- **SimulationView.java**
    - This simply encapsulates the **StatView** and a **GridView** subclass. Together, these objects contain all the information needed for a given simulation. This class is dynamic and stretches to fill its given window. If multiple are displayed at the same time, they split the size equally.
- **GraphView.java**
    - This shows the stats of a given simulation via a PopUp window. The stats run concurrently with the simulation, showing the rise and fall of the count for each state in the corresponding simulation.
    

## Assumptions that Affect the Design

- Assumes the Grid has positive width and height. If the grid is 0x0 or has negative dimensions, the program either runs a blank grid with the states of each cell always equal to 0 in the stat counter, or it throws an error. This depends on the formation of the grid. If cells must be hard-coded in initialization, the error is thrown.
- If window is shrunk far enough, the grids disappear from view. This program assumes the window will be reasonably large to display all the information.
- CAController technically has View elements encapsulated in it and does not entirely fit within the MVC model (but still serves as a controller mainly taking input and manipulating models).
- Assumes that the shape of the cell fits within a GridPane model. By dividing the cell into various parts (such as splitting the hexagon), this can accomplished in a contrived manner.

#### Features Affected by Assumptions
We did not encounter any features in the "Complete" version of Simulation that we could not acomplish with our design.

With that said, our hexagons do not interlock, which is a disadvantage of the GridPane. To make interlocking shapes that are not rectangular, a different pane could have been used.

## New Features HowTo

#### Easy to Add Features
- Adding a new Simulation requires three steps
    - Create a new subclass of **Grid.java** with new simulation logic
    - Add an XML File using the proper syntax within the **/data** folder
    - Add case within the **XMLParser.java**
- Adding a new type of grid
    - Create a new updateNeighbors method in **Grid.java**
    - Add grid specific updateNeighbors method into case switch in **Grid.java**
    - Create new subclass of **GridView.java** to visualize a different shape
- Add a instance of an exisiting simulation
    - Create new XML file in standard format
    - Click "Load File"
    - Select XML File
    - Simulation will either render of XML Error will pop up
- Add a New Button
    - In **ButtonControllers**, add a new boolean and boolean getter method corresponding to the button.
    - In *renderHeader()*, create an instance of it with *makeButton()* setting the boolean to true on click.
    - Call *formatButton()* on new button
    - In **CAController**, in *step()*, check the boolean getter method, and add a *handle()* method to deal with new functionality.
- Add a New Slider
    - In **SliderControllers**, create a Slider instance variable and a getter method returning the sliders value.
    - In *renderFooter()*, add a new label via *addLabel* describing the slider
    - In *renderFooter()*, add a new slider via *addAndReturnSlider* and set the return to the instance variable.
    - Where needed in *CAController*, return the value of the slider at its instance using the getter method.

#### Other Features not yet Done
- Interactive Cells (clicking to change state)
    - Create a pinger of the mouse state over the GridView, set the state upon click
    - Alternatively, make each cell a button instead of a region (easiest with rectangles)
- Different Grid Boundaries (Toroidal, infinite, etc.)
    - Manipulate edge cases within **Grid.java** to create a new boundary, wrap edge cases around to the other side with coordinates matching the final col/row from the first col/row
- Interlocking Cells of Different Shapes
    - Current implementation of Hexagons does not interlock; perhaps using an alternative to GridPane could solve this issue
- Triangular Neighbor Cells
    - Create new neighbor-checking method within **Grid.java** with support for 12 neighbors
    - Create new Triangle gridview as a subclass of **GridView.java** that sets the shape using Polygons
- Extra simulations (Langton's Loop, SugarScape)
    - Explore simulation logic and develop algorithm accordingly as a subclass of **Grid.java**
- Saving the state of a grid as an XML file
    - Allow **CAController** to pass the current grid as an immutable list into **XMLParser**. **XMLParser** would then iterate through each cell and save it as a `<cell>` with a specific coordinate and state into the `<layout>` tag. **XMLParser** would then concatenate this with the existing XML file that is running and export it as a new XML file.
- Updating the CSS file by reading in XML
    - Create a new format of XML files that specifies certain CSS properties for our simulation. Add a new type of file (instead of just grid) and methods in **XMLParser** that would be specific to  parsing style XML files. Prompt the user in **CAController** to load a specific CSS file.