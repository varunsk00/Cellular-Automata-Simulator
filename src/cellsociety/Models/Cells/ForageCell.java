package cellsociety.Models.Cells;

public class ForageCell extends Cell {
    private int foodPher;
    private int homePher;
    private String FULLANT = "fullant";
    private String ANT = "ant";

    public ForageCell(String state, int x, int y) {
        super(state, x, y);
        this.foodPher=0;
        this.homePher=0;
    }

    public int getFoodPher() {
        return foodPher;
    }

    public int getHomePher() {
        return homePher;
    }

    public void updateFoodPher(int change) {
        this.foodPher = this.foodPher + change;
    }

    public void updateHomePher(int change) {
        this.homePher = this.homePher + change;
    }

    public void setFull() {
        setNextState(FULLANT);
    }

    public boolean isFull() {
        return getState().equals(FULLANT);
    }

    public boolean isHungry() {
        return getState().equals(ANT);
    }
}
