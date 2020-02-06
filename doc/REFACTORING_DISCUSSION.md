# Refactoring Discussion!
## Eric Doppelt, Jaidha Rosenblatt, Varun Kosgi

Priorities:
1. One thing we immediately notice is that our Models of our Grids contain color. This is a problem, since it has a View-like responsibility and there is importation of JavaFX within each Grid class. To fix this, we can offload the coloring of cells to the GridView class and map each state to a specific color via a ResourceBundle.

2. Some of our grid methods that affect the ArrayList are public, when in reality they could be protected.

3. Our packages are currently split well, but there is room for improvement. We can rename them to correspond with the model, view, and configuration packages, and can avoid having classes without packages (like Cell).

4. Additionally, our Resource packages could be improved to move essential files into src.

5. There is a good amount of duplicated code within our grid subclasses. The duplicated code could be offloaded to the Grid class as to avoid the repition of code and make the addition of new Simulation types more dynamic and adaptable. Ideally, we should only have a method responsible for each simulation's logic within each subclass.

6. We often are using hard-coding states in the model instead of moving them into variables.

7. Two chains of conditionals to select grid subclass in the XML parser
8. Declarations should use Java "collection" instead of ArrayList in Grids
9. Moving data in XML into a instance variable