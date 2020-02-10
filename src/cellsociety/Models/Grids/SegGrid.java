package cellsociety.Models.Grids;

import java.awt.*;
import java.util.List;
import java.util.Map;
import cellsociety.Models.Cells.*;
import java.util.ArrayList;
import java.util.Random;

public class SegGrid extends Grid {
    private Random r = new Random();
    private double prob;
    private double percentFull;
    private ArrayList<Point> sameCells;
    private static final int numberOfNeighbors = 8;
    private static final List<String> states = List.of("X", "O", "empty");
    private final String X = states.get(0);
    private final String O = states.get(1);
    private final String EMPTY = states.get(2);


    /**
     * Constructs a new SegGrid Simulation
     * @param data map for this simulation's specific variables
     * @param cellTypes map from state to colors
     * @param details miscellaneous grid information, such as authors, titles, gridtype, etc.
     * @param layout map from Cell states to points, if null -> random generated initial state
     */
    public SegGrid(Map<String, Double> data, Map<String, String> cellTypes, Map<String, String> details, Map<String, Point> layout) {
      super(data, cellTypes, details, states);
      this.prob = getDoubleFromData(data,"satisfiedThreshold") * numberOfNeighbors;
      this.percentFull = getDoubleFromData(data, "percentFull");
      this.sameCells = new ArrayList<>();
      setLayout(layout);
    }

    /**
     * Overrides updateGrid() method to store the coordinates of Cells that are the same
     */
    @Override
    public void updateGrid() {
      int x = 0;
      int y = 0;
      storeCellsByState(sameCells, getCell(x,y).getState());
      super.updateGrid();
    }

    @Override
    protected void updateCell(int x, int y, List<Cell> neighbors) {
      int similarCount = 0;
      for (Cell c : neighbors) {
        if (c.getState().equals(current(x, y).getState())) {
          similarCount++;
        }
      }
      if (similarCount >= prob) {
        System.out.println("satisfied: " + (x) + ", " + (y));
      } else {
        System.out.println("unsatisfied: " + (x) + ", " + (y));
        int ranX = r.nextInt(getColumns());
        int ranY = r.nextInt(getRows());
        while (current(ranX, ranY).getState().equals(EMPTY)) {
          current(ranX, ranY).setState(current(x, y).getState());
          System.out.println("relocated to: " + (ranX) + ", " + (y));
          ranX = r.nextInt(getColumns());
          ranY = r.nextInt(getRows());
        }
        current(x, y).setState(EMPTY);
      }
      numIterations++;
    }

    private void setLayout(Map<String, Point> layout) {
      if (layout == null){
        setLocalInitState();
      }
      else{
        setInitState(layout);
      }
    }

    private void setLocalInitState() {
      for (int i = 0; i < this.getRows(); i++) {
        for (int j = 0; j < this.getColumns(); j++) {

          if (r.nextFloat() <= percentFull / 2) {
            this.current(i, j).setState(X);
          }
          if (r.nextFloat() <= percentFull / 2) {
            this.current(i, j).setState(O);
          }
        }
      }
    }
}
