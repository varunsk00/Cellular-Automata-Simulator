package cellsociety.Models.Grids;

import cellsociety.Models.Cell;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HexGrid extends Grid{

  public HexGrid(int rows, int columns) {
    super(rows, columns);
  }

  public HexGrid(Map<String, String> data) {
    super(data);
  }

  @Override
  protected List<Cell> getAllNeighbors(int x, int y) {
    List<Cell> neighbors = super.getAllNeighbors(x, y);
    neighbors.add(getCell(x,y-1));
    neighbors.add(getCell(x,y+1));
    return neighbors;
  }
}
