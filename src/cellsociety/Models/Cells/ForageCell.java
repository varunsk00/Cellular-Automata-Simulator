package cellsociety.Models.Cells;

/**
 * This class represents a ForageCell for the Foraging Simulation.
 * Used to represent an Ant, Nest, and Food
 *
 * @author Varun Kosgi
 */
public class ForageCell extends Cell {
    private int foodPher;
    private int homePher;
    private String FULLANT = "fullant";
    private String ANT = "ant";

    /**
     * Constructs a new ForageCell
     *
     * @param state the initial state for cell Sets the cell's instance variables using update()
     * @param x
     * @param y
     */
    public ForageCell(String state, int x, int y) {
        super(state, x, y);
        this.foodPher=0;
        this.homePher=0;
    }

    /**
     * Gets Food Pheremone Count
     *
     * @return the current level of Food Pheromones at a ForageCell
     */
    public int getFoodPher() {
        return foodPher;
    }

    /**
     * Gets Home Pheremone Count
     *
     * @return the current level of Home Pheromones at a ForageCell
     */
    public int getHomePher() {
        return homePher;
    }

    /**
     * Updates the current number of Food Pheromones at a ForageCell
     *
     * @param change the amount to increment the number of food pheromones
     */
    public void updateFoodPher(int change) {
        this.foodPher = this.foodPher + change;
    }

    /**
     * Updates the current number of Home Pheromones at a ForageCell
     *
     * @param change the amount to increment the number of home pheromones
     */
    public void updateHomePher(int change) {
        this.homePher = this.homePher + change;
    }

    /**
     * Sets the State of an Ant to FULLANT
     */
    public void setFull() {
        setNextState(FULLANT);
    }

    /**
     * @return true if a Ant has switched states to FULLANT
     */
    public boolean isFull() {
        return getState().equals(FULLANT);
    }

    /**
     * @return true is an Ant is of state ANT
     */
    public boolean isHungry() {
        return getState().equals(ANT);
    }
}
