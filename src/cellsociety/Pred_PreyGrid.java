package cellsociety;

import java.util.List;
import java.util.Map;

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


}
