package dungeon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A cell in a dungeon has an x coordinate, y coordinate and a value assigned to it
 * for calculation and identification. Each cell can be updated with possible moves
 * treasure, arrows, thieves and monsters.
 */
final class CellClass implements Cell {
  private final int x;
  private final int y;
  private final int value;
  private final List<Treasure> treasureList;
  private final List<Directions> neighbourList;
  private final List<Monster> monsterInCave;
  private int noOfArrowsCell;
  private boolean thiefPresent;

  /**
   * A cell is created with an x coordinate, y coordinate, value,
   * empty neighbour list, treasure list, no thief and arrows.
   *
   * @param x height coordinate of cell
   * @param y width coordinate of cell
   * @param value value identifying the cell
   * @throws IllegalArgumentException if the arguments passed are negative
   */
  public CellClass(int x, int y, int value) {
    if (x < 0 || y < 0 || value < 0) {
      throw new IllegalArgumentException("Values cannot be negative");
    }
    this.x = x;
    this.y = y;
    this.value = value;
    treasureList = new ArrayList<>();
    neighbourList = new ArrayList<>();
    monsterInCave = new ArrayList<>();
    noOfArrowsCell = 0;
    thiefPresent = false;
  }

  /**
   * Copy constructor.
   *
   * @param c cell to be copied
   * @throws IllegalArgumentException if cell argument is null
   */
  public CellClass(Cell c) {
    if (c == null) {
      throw new IllegalArgumentException("Cell cannot be null");
    }
    x = c.getX();
    y = c.getY();
    value = c.getValue();
    treasureList = c.getTreasureList();
    neighbourList = c.possibleMoves();
    List<Monster> copyMonster = new ArrayList<>();
    copyMonster.add(c.getMonster());
    monsterInCave = copyMonster;
    noOfArrowsCell = c.getNumberOfArrows();
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public void addTreasureToLocation(Treasure t) {
    if (t == null) {
      throw new IllegalArgumentException("Treasure cannot be null");
    }
    treasureList.add(t);
  }

  @Override
  public void removeTreasureFromLocation(Treasure t) {
    if (t == null) {
      throw new IllegalArgumentException("Treasure cannot be null");
    }
    treasureList.remove(t);
  }

  @Override
  public List<Treasure> getTreasureList() {
    return new ArrayList<>(treasureList);
  }

  @Override
  public void addArrowToLocation() {
    noOfArrowsCell = noOfArrowsCell + 1;
  }

  @Override
  public void removeArrowFromLocation() {
    noOfArrowsCell = noOfArrowsCell - 1;
  }

  @Override
  public int getNumberOfArrows() {
    return noOfArrowsCell;
  }

  @Override
  public void addMonsterToLocation() {
    monsterInCave.add(new Otyugh());
  }

  @Override
  public void removeMonsterFromLocation() {
    monsterInCave.remove(0);
  }

  @Override
  public Monster getMonster() {
    if (monsterInCave.size() != 0) {
      return monsterInCave.get(0);
    }
    else {
      return null;
    }
  }

  @Override
  public void addThief() {
    thiefPresent = true;
  }

  @Override
  public void removeThief() {
    thiefPresent = false;
  }

  @Override
  public boolean getThief() {
    return thiefPresent;
  }

  @Override
  public void addNeighbour(Directions d) {
    if (d == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    neighbourList.add(d);
  }

  @Override
  public List<Directions> possibleMoves() {
    return new ArrayList<>(neighbourList);
  }

  @Override
  public int getNoOfEntrances() {
    return possibleMoves().size();
  }
}
