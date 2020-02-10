package cellsociety.Models.Grids;

import cellsociety.Models.Cells.*;

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
    public ForageGrid(Map<String, Double> data, Map<String, String> cellTypes, Map<String, String> details) {
        super(data, cellTypes, details, states);
        this.maxPheremones = getDoubleFromData(data, "maxPheromones");
        this.percentFood = getDoubleFromData(data, "percentFood");
        setInits();
    }

    /**
     * Initializes an ArrayList of ArrayLists representative of the grid
     **/
    @Override
    protected List<List<Cell>> createGrid() {
        List<List<Cell>> ret = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < getColumns(); j++) {
                row.add(new Ant("empty", j, i));
            }
            ret.add(row);
        }
        return ret;
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
        Ant currentCell = (Ant) current(x, y);
        if(currentCell.isHungry() || currentCell.isFull()){
            handleAnt(currentCell, neighbors);
        }
        if(currentCell.getState().equals(NEST)){
            current(x,y).setNextState(ANT);
        }
    }

    private void handleAnt(Ant current, List<Cell> neighbors){
        if(current.isFull()){
            goToNest(current, neighbors);
        }
        else if (current.isHungry()){
            goToFood(current, neighbors);
        }
    }

    private void goToNest(Ant current, List<Cell> neighbors){
        Ant maxHomePherCell = maxHomePheromonesCell(neighbors);
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

    private void goToFood(Ant current, List<Cell> neighbors){
        if(foodNeighbor(neighbors) != null){
            current.setFull();
            System.out.println("EAT");
        }
        else if (atNest(current)){
                int rand_i = r.nextInt(neighbors.size());
                if(isEmpty((Ant) neighbors.get(rand_i))) {
                    neighbors.get(rand_i).setNextState(ANT);
                    current.setNextState(NEST);
                }
        }
        else{
            Ant maxFoodPherCell = maxFoodPheromonesCell(neighbors);
            if(maxFoodPherCell != null && isEmpty(maxFoodPherCell)){
                moveAnt(current, maxFoodPherCell);
                dropFoodPheromone(current);
            }
        }
    }

    private Ant foodNeighbor(List<Cell> neighbors){
        for(Cell c: neighbors){
            if (c.getState().equals(FOOD)){
                return (Ant) c;
            }
        }
        return null;
    }

    private Ant homeNeighbor(List<Cell> neighbors){
        for(Cell c: neighbors){
            if (atNest((Ant) c)){
                return (Ant) c;
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

    private void dropFoodPheromone(Ant c){
        if(c.getFoodPher() >= maxPheremones) {
            c.updateFoodPher(0);
        }
        else{
            c.updateFoodPher(1);
        }
    }

    private void dropHomePheromone(Ant c){
        if(c.getHomePher() >= maxPheremones - constant) {
            c.updateHomePher(0);
        }
        else{
            c.updateHomePher(1);
        }
    }

    private int maxFoodPheromones(List<Cell> neighbors){
        int maxFoodPher = 0;
        for (Cell cell : neighbors) {
            Ant neighbor = (Ant) cell;
            if (neighbor.getFoodPher() > maxFoodPher) ;
            maxFoodPher = neighbor.getFoodPher();
        }
        return maxFoodPher;
    }

    private int maxHomePheromones(List<Cell> neighbors){
        int maxHomePher = 0;
        for(Cell cell : neighbors){
            Ant neighbor = (Ant) cell;
            if (neighbor.getHomePher() > maxHomePher);
            maxHomePher = neighbor.getHomePher();
        }
        return maxHomePher;
    }

    private Ant maxFoodPheromonesCell(List<Cell> neighbors){
        int max = maxFoodPheromones(neighbors);
        List<Ant> maxPher = new ArrayList<>();
        for(Cell cell : neighbors){
            Ant neighbor = (Ant) cell;
            if (neighbor.getFoodPher() == max && isEmpty(neighbor));
                maxPher.add(neighbor);
        }
        return randomNeighbor(maxPher);
    }

    private boolean isEmpty(Ant c){
        return c.getState().equals(EMPTY);
    }

    private Ant maxHomePheromonesCell(List<Cell> neighbors){
        int max = maxHomePheromones(neighbors);
        List<Ant> maxPher = new ArrayList<>();
        for(Cell cell : neighbors){
            Ant neighbor = (Ant) cell;
            if (neighbor.getHomePher() == max && neighbor.getState().equals(EMPTY));
            maxPher.add(neighbor);
        }
        return randomNeighbor(maxPher);
    }

    private Ant randomNeighbor(List<Ant> neighbors){
        int randIndex = r.nextInt(neighbors.size());
        for(Ant neighbor: neighbors){
            if(neighbors.get(randIndex).equals(neighbor) && neighbor.getState().equals(EMPTY)){
                return neighbor;
            }
        }
        return null;
    }

    private boolean atNest(Ant current){
        return current.getCoordinate().equals(new Point(getRows()/2,getColumns()/2));
    }

    private void setInits() {
        List<Cell> NestNeighbors = getAllNeighbors(getRows()/2, getColumns()/2);
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                if (r.nextFloat() <= percentFood/2 && !NestNeighbors.contains(current(i,j))) {
                    this.current(i, j).setState(FOOD);
                    this.current(i, j).setNextState(FOOD);
                }
            }
        }
        this.current(getRows()/2,getColumns()/2).setState(NEST);
    }
}
