package cellsociety.Models.Grids;

import cellsociety.Models.Cell;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ForageGrid extends Grid {
    private Random r = new Random();
    private double maxPheremones;
    private double percentFood;
    private final int constant = 1;
    private static final List<String> states = List.of("food", "empty", "nest", "ant", "fullant");
    private final String FOOD = states.get(0);
    private final String EMPTY = states.get(1);
    private final String NEST = states.get(2);
    private final String ANT = states.get(3);
    private final String FULLANT = states.get(4);

    public ForageGrid(Map<String, Double> data, Map<String, String> cellTypes, Map<String, String> details, Map<String, Point> layout) {
        super(data, cellTypes, details, states);
        this.maxPheremones = getDoubleFromData(data, "maxPheromones");
        this.percentFood = getDoubleFromData(data, "percentFood");
        setLayout(layout);
    }

  private void setLayout(Map<String, Point> layout) {
    if (layout == null){
      setInits();
    }
    else{
      setInitState(layout);
    }
  }

    @Override
    public void updateGrid() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                List<Cell> neighbors = getAllNeighbors(i, j);
                updateCell(i, j, neighbors);
            }
        }
        setCellsToFutureStates();
        numIterations++;
    }

    private void setCellsToFutureStates() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                String state = current(i, j).getNextState();
                setCellState(i, j, state);
            }
        }
    }

    @Override
    protected void updateCell(int x, int y, List<Cell> neighbors) {
        if(current(x,y).getState().equals(ANT) || current(x,y).getState().equals(FULLANT)){
            handleAnt(x,y, neighbors);
        }
        if(current(x,y).getState().equals(NEST)){
            current(x,y).setNextState(ANT);
        }
    }

    private void handleAnt(int x, int y, List<Cell> neighbors){
        if(current(x,y).getState().equals(FULLANT)){
            goToNest(current(x,y), neighbors);
        }
        else if (current(x,y).getState().equals(ANT)){
            goToFood(current(x,y), neighbors);
        }
    }

    private void goToNest(Cell current, List<Cell> neighbors){
        Cell maxHomePherCell = maxHomePheromonesCell(current, neighbors);
        Cell home = homeNeighbor(neighbors);
        if(home != null){
            current.setNextState(EMPTY);
            System.out.println("HOME");
        }
        else if(maxHomePherCell != null && isEmpty(maxHomePherCell)){
            moveAnt(current, maxHomePherCell);
            dropHomePheromone(current);
        }
    }

    private void goToFood(Cell current, List<Cell> neighbors){
        if(foodNeighbor(neighbors) != null){
            current.setNextState(FULLANT);
            System.out.println("EAT");
        }
        else if (atNest(current)){
                int rand_i = r.nextInt(neighbors.size());
                if(isEmpty(neighbors.get(rand_i))) {
                    neighbors.get(rand_i).setNextState(ANT);
                    current.setNextState(NEST);
                }
        }
        else{
            Cell maxFoodPherCell = maxFoodPheromonesCell(current, neighbors);
            if(maxFoodPherCell != null && isEmpty(maxFoodPherCell)){
                moveAnt(current, maxFoodPherCell);
                dropFoodPheromone(current);
            }
        }
    }

    private Cell foodNeighbor(List<Cell> neighbors){
        for(Cell c: neighbors){
            if (c.getState().equals(FOOD)){
                return c;
            }
        }
        return null;
    }

    private Cell homeNeighbor(List<Cell> neighbors){
        for(Cell c: neighbors){
            if (atNest(c)){
                return c;
            }
        }
        return null;
    }

    private void moveAnt(Cell a, Cell b){
        if(a.getState().equals(ANT)){
            b.setNextState(ANT);
            a.setNextState(EMPTY);
        }
        else if (a.getState().equals(FULLANT)){
            b.setNextState(FULLANT);
            a.setNextState(EMPTY);
        }
    }

    private void dropFoodPheromone(Cell c){
        if(c.getFoodPher() >= maxPheremones) {
            c.updateFoodPher(0);
        }
        else{
            c.updateFoodPher(1);
        }
    }

    private void dropHomePheromone(Cell c){
        if(c.getHomePher() >= maxPheremones - constant) {
            c.updateHomePher(0);
        }
        else{
            c.updateHomePher(1);
        }
    }

    private int maxFoodPheromones(List<Cell> neighbors){
        int maxFoodPher = 0;
        for(Cell neighbor : neighbors){
            if (neighbor.getFoodPher() > maxFoodPher);
            maxFoodPher = neighbor.getFoodPher();
        }
        return maxFoodPher;
    }

    private int maxHomePheromones(List<Cell> neighbors){
        int maxHomePher = 0;
        for(Cell neighbor : neighbors){
            if (neighbor.getHomePher() > maxHomePher);
            maxHomePher = neighbor.getHomePher();
        }
        return maxHomePher;
    }

    private Cell maxFoodPheromonesCell(Cell current, List<Cell> neighbors){
        int max = maxFoodPheromones(neighbors);
        List<Cell> maxPher = new ArrayList<>();
        for(Cell neighbor : neighbors){
            if (neighbor.getFoodPher() == max && isEmpty(neighbor));
                maxPher.add(neighbor);
        }
        return randomNeighbor(maxPher);
    }

    private boolean isEmpty(Cell c){
        return c.getState().equals(EMPTY);
    }

    private Cell maxHomePheromonesCell(Cell current, List<Cell> neighbors){
        int max = maxHomePheromones(neighbors);
        List<Cell> maxPher = new ArrayList<>();
        for(Cell neighbor : neighbors){
            if (neighbor.getHomePher() == max && neighbor.getState().equals(EMPTY));
            maxPher.add(neighbor);
        }
        return randomNeighbor(maxPher);
    }

    private Cell randomNeighbor(List<Cell> neighbors){
        int randIndex = r.nextInt(neighbors.size());
        for(Cell neighbor: neighbors){
            if(neighbors.get(randIndex).equals(neighbor) && neighbor.getState().equals(EMPTY)){
                return neighbor;
            }
        }
        return null;
    }

    private boolean atNest(Cell current){
        return current.getCoordinate().equals(new Point(getRows()/2,getColumns()/2));
    }

    private void setInits() {
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                if (r.nextFloat() <= percentFood/2) {
                    this.current(i, j).setState(FOOD);
                    this.current(i, j).setNextState(FOOD);
                }
            }
        }
        this.current(getRows()/2,getColumns()/2).setState(NEST);
    }
}
