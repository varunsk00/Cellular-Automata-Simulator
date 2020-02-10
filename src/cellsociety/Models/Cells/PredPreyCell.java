package cellsociety.Models.Cells;

public class PredPreyCell extends Cell {
    private int lives;

    public PredPreyCell(String state, int x, int y) {
        super(state, x, y);
        this.lives=0;
    }

    /**
     * set a cell's lives
     *
     * @param lives an int representing the lives to set to our cell
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * update the cell's lives based on a given change
     *
     * @param change an int representing the desired change for lives
     */
    public void updateLives(int change) {
        this.lives = this.lives + change;
    }

    /**
     * @return the cell's current lives
     */
    public int getLives() {
        return lives;
    }
}
