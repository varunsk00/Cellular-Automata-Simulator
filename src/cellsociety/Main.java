package cellsociety;
import cellsociety.Controllers.CAController;

/**
 * Extremely Basic Main class that creates a CAController Object to run the simulation
 * Launches the Application in the constructor of CAController Called
 */
public class Main {
  /**
   * Main class of the program creates a CAController Object
   * @param args is the String[] passed into Main and needed to launch the Application
   */
  public static void main(String[] args) {
    CAController simulator = new CAController(args);;
  }
}


