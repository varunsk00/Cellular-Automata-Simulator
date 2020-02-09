package cellsociety.Models.Grids;

import java.awt.*;
import java.util.List;
import java.util.Map;
import cellsociety.Models.Cell;
import java.util.ArrayList;
import java.util.Random;

public class RPSGrid extends Grid {

    private Random r = new Random();

    private ArrayList<Point> sameCells;
    private final String R = "R";
    private final String P = "P";
    private final String S = "S";
    private final String EMPTY = "empty";
    private double s;
    private double m;
    private double time = 0.0;
    private double K;
    private double diffusivityRate;
    private double growthRate;


    /**
     * Sets rows and columns and instance variables Calls createGrid to initialize a grid of cells
     * based on given rows and columns
     **/
    public RPSGrid(Map<String, String> data) {
        super(data);
        this.s = parseDoubleFromMap(data, "s-empiricalTest");
        this.m = parseDoubleFromMap(data, "sigmoidFunctionRate");
        this.K = parseDoubleFromMap(data, "decayRate");
        this.diffusivityRate = parseDoubleFromMap(data, "diffusivityRate");
        this.growthRate = parseDoubleFromMap(data, "growthRate");
        setInits();
    }

    @Override
    protected void updateCell(int x, int y, List<Cell> neighbors) {
        time = time + (1.0/(double)(getRows()*getColumns()));
        double decay = 1 - Math.exp(-K*time);
        int k = 0;
        List<Cell> emptyNeighbors = new ArrayList<>();
        for(Cell c: neighbors){
            if (c.getState().equals(EMPTY)){
                emptyNeighbors.add(c);
            }
            else if (c.getState().equals(current(x,y).getState()) && !c.getState().equals(EMPTY)) {
                k++;
            }
        }
        //reproduction
        if(r.nextFloat() <= growthRate){
            if(emptyNeighbors.size() > 0){
                Cell randomEmptyNeighbor = emptyNeighbors.get(r.nextInt(emptyNeighbors.size()));
                randomEmptyNeighbor.setState(current(x,y).getState());
                System.out.println("Parent: " + current(x,y).getCoordinate());
                System.out.println("Child: " + randomEmptyNeighbor.getCoordinate());
            }
        }
        //decay
        else if (r.nextFloat() <=  decay){
            current(x,y).setState(EMPTY);
            System.out.println("DEAD");
        }

        //conjugation
        else if (r.nextFloat() <= 0.5){
            //RPS Mechanic
            Cell randomNeighbor = neighbors.get(r.nextInt(neighbors.size()));
            double sigmoid = 1/(1+Math.exp(-(k-m)/s));
            if (r.nextFloat() <=  sigmoid){
                if(current(x,y).getState().equals(R) && randomNeighbor.getState().equals(S)){
                    current(randomNeighbor.getCoordinate().x, randomNeighbor.getCoordinate().y).setState(EMPTY);
                }
                if(current(x,y).getState().equals(R) && randomNeighbor.getState().equals(P)){
                    current(x, y).setState(EMPTY);
                }
                if(current(x,y).getState().equals(P) && randomNeighbor.getState().equals(R)){
                    current(randomNeighbor.getCoordinate().x, randomNeighbor.getCoordinate().y).setState(EMPTY);
                }
                if(current(x,y).getState().equals(P) && randomNeighbor.getState().equals(S)){
                    current(x, y).setState(EMPTY);
                }
                if(current(x,y).getState().equals(S) && randomNeighbor.getState().equals(P)){
                    current(randomNeighbor.getCoordinate().x, randomNeighbor.getCoordinate().y).setState(EMPTY);
                }
                if(current(x,y).getState().equals(S) && randomNeighbor.getState().equals(R)) {
                    current(x, y).setState(EMPTY);
                }
        }

        //bacteria movement
        for(Cell c: neighbors){
            if (r.nextFloat() <= 0.5*diffusivityRate && (!c.getState().equals(EMPTY) && !current(x,y).equals(EMPTY))){
                String current_state = current(x,y).getState();
                current(x,y).setState(c.getState());
                c.setState(current_state);
                System.out.println("SWAP");
        }
    }
}
}

    private void setInits() {
        this.current(r.nextInt(getRows()),r.nextInt(getColumns())).setState(R);
        this.current(r.nextInt(getRows()),r.nextInt(getColumns())).setState(P);
        this.current(r.nextInt(getRows()),r.nextInt(getColumns())).setState(S);
    }
}