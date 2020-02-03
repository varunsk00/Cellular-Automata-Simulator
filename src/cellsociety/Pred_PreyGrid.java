package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Pred_PreyGrid extends Grid {

  public static final List<String> DATA_FIELDS = List.of(
      "preyDeathRate",
      "predatorDeathRate",
      "preyBirthRate",
      "predatorBirthRate",
      "percentPredator",
      "percentPrey"
  );

  private static int predatorEnergy;
  private static int preyGenerationRate;
  private static int predatorGenerationRate;
  private static double percentPredator;
  private static double percentPrey;
  private Random r = new Random();

  public Pred_PreyGrid(int rows, int columns, int predatorEnergy, int preyGenerationRate,
      int predatorGenerationRate, double percentPredator, double percentPrey) {
    super(rows, columns);
    this.predatorEnergy = predatorEnergy;
    this.preyGenerationRate = preyGenerationRate;
    this.predatorGenerationRate = predatorGenerationRate;
    this.percentPredator = percentPredator;
    this.percentPrey = percentPrey;
  }

  public Pred_PreyGrid(Map<String, String> dataValues) {
    this(Integer.parseInt(dataValues.get(DATA_FIELDS.get(0))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(1))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(2))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(3))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(4))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(5))),
        Double.parseDouble(dataValues.get(DATA_FIELDS.get(6))));
  }

    @Override
    public void handleMiddleCell(int x, int y) {
        ArrayList<Cell> neighbors = getNeighbors(x, y);

        if(current(x,y).getState().equals("prey")){
            int rng = r.nextInt(4);
            while(getNeighbors(x,y).get(rng).equals("empty"))
            {

            }
            for (Cell c : neighbors) {
                if (c.getState().equals("empty")) {
                    c.update(Color.GREEN, "prey" + "1");
                    current(x,y).update(Color.WHITE,"empty");
                }
            }
        }
    }

}
