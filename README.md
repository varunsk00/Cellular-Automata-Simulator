Simulation
====

This project implements a cellular automata simulator.

Names: Eric Doppelt, Jaidha Rosenblatt, Varun Kosgi

### Timeline

Start Date: January 23rd, 2020

Finish Date: February 10th, 2020

Hours Spent: 120 Hours Combined

### Primary Roles

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

### Resources Used
[JavaFX Duplicate Series Added](https://stackoverflow.com/questions/32151435/javafx-duplicate-series-added)
[JavaFX Duplicate Series](https://stackoverflow.com/questions/38369601/javafx-chart-java-lang-illegalargumentexception-duplicate-series-added/38371845)
[Making an Immutable List](https://stackoverflow.com/questions/30348292/how-to-create-immutable-list-in-java)
[Handling a String to Double Exception](https://stackoverflow.com/questions/29954080/how-to-handle-exception-when-convert-string-to-double)
[Handling Grid Out of Bounds](https://stackoverflow.com/questions/29808883/how-to-check-if-a-2d-array-is-out-of-bounds)
[Exception Handling and Debugging](https://docs.oracle.com/cd/E17276_01/html/gsg_xml/java/exceptions.html)
[Designing with exceptions](https://www.javaworld.com/article/2076721/designing-with-exceptions.html?page=2)
[Using a Switch](https://www.w3schools.com/java/java_switch.asp)
[Understanding XML](https://www.w3schools.com/xml/xml_whatis.asp)
[Reading XML](https://javarevisited.blogspot.com/2011/12/parse-xml-file-in-java-example-tutorial.html)
[Parsing XML](https://www.edureka.co/blog/java-xml-parser/)

### Running the Program

Main class: src/Main

Data files needed: XML data files, Standard.properties, StandardGraph.properties, 

Features implemented:
1. Implemented all Features described in Basic Implementation
2. Different types of cell shapes (eight neighbor and hexagonal)
3. Two additional simulations  (RPS and Ant Foraging)
4. Implemented error checking (with custom exceptions) for XML files and caught them using Alert messages
    - Missing piece of XML (missingState.xml)
    - Cell locations outside the bounds of the grid's size (fireWithLayoutError.xml)
    - Invalid values for integers/double properties (invalidColumnNumber.xml and invalidDouble.xml)
    - Invalid grid type (invalidGridType.xml)
    - Invalid simulation type (invalidSimulationType.xml)
    - Null value for required property (nullValue.xml)
5. Variable statistics and graphs for each simulation
6. Finite Grid Edges
7. XML Configuration can be either hardcoded with specific locations or randomized with probability
8. Grid can be styled via XML in the following ways:
    - Kind of cell shape to use (Hexagonal v. Rectangle)
    - Color of cell (hex value is given in XML)
9. Display a line graph of stats regarding the count of the number of cells for each state
10. Sliders can be used to speed up and skip ahead the simulation
11. Multiple simulations cane run at once and they will autosize to fit the Window

### Notes/Assumptions

Assumptions or Simplifications:

1. Assumes the Grid has positive width and height. If the grid is 0x0 or has negative dimensions, the program runs a blank grid with the states of each cell always equal to 0 in the stat counter.
2. If window is shrunk far enough, the grids disappear from view. This program assumes the window will be reasonably large to display all the information.
3. CAController technically has View elements encapsulated in it and does not entirely fit within the MVC model (but still serves as a controller mainly taking input).

Interesting data files:
1. Segregation since it employs Hex Cell Shapes

Known Bugs: 
1. When the grid gets beyond a 100x100 Cell size, the grid rendering gets extremely slow. 
2. The graph for the Foraging Simulation is hard to see because the variable for Empty Cells is magnitudes larger than the other variables. 

Extra credit: Multiple Grids dynamically resize and readjust after clear simulations has been pressed, including when both Hex and Rectangular are displayed.


### Impressions
**Varun**:
The Cellular Automata project was extremely enriching, but also very time-consuming (literally the most time I have ever put into a coding project with Breakout being a close second). Being a back-end developer for the Simulation project was very fun and I learned a significant amount of Java code practice. It was really rewarding to watch my front-end team members take the results of my algorithms and turn them into something presentable. Granted, back-end programming is very logic heavy, and I would like to experiment more with the data manipulation and JavaFX syntax associated with the front-end eventually. I used this project to try and improve my code design skills (including refactoring!!). This marked a big change from my Breakout project, and I am generally happy with my efforts to refactor and keep clean coding conventions. 

**Jaidha**:
I have learned a ton throughout the completion of this project. In the past, I have been assigned the frontend, so working with file parsing and the backend was an interesting challenge. While it was challenging to learn, I feel that I have a good grasp of how to parse XML files and handle errors. I also spent around 15 hours just on Predator Prey and went through many implementations. This was frustrating, but was incredibly satisfying to see it finished. Overall, I am happy with the effort I put into this project and the amount I learned.

**Eric**:
Overall, I really enjoyed this project. It gave me a good amount of practice dealing with various JavaFX GUI problems, such as creating dynamically resizable nodes; working with various Panes; using the MVC model; and using encapsulation within various components.

Going forward, if I could change the project, my only recommendation would be to be clearer about the expectation for the complete project. I did not understand that we were expected to complete 2 sub-bullets from the third tier until I saw it on Piazza two days ago. Had I known this earlier, I likely would have had time to implement the final Visualization task.

Aside from that, I thought it was a great project!