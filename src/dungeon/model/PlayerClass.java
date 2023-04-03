package dungeon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A player is created in the start location of the dungeon and moves
 * through the dungeon collecting treasure from caves and slaying monsters
 * present in the dungeon with arrows.
 */
final class PlayerClass implements Player {
  private final List<Treasure> playerTreasureList;
  private int noOfArrowsPlayer;
  private Cell playerLocation;

  /**
   * A player is created with an empty treasure list.
   */
  public PlayerClass() {
    this.playerTreasureList = new ArrayList<>();
    this.noOfArrowsPlayer = 3;
  }

  /**
   * Copy constructor.
   *
   * @param p player object to be copied
   * @throws IllegalArgumentException if player is null
   */
  public PlayerClass(Player p) {
    if (p == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    playerLocation = p.getPlayerLocation();
    playerTreasureList = p.getPlayerTreasureList();
  }

  @Override
  public void setPlayerLocation(Cell l) {
    if (l == null) {
      throw new IllegalArgumentException("Value cannot be null");
    }
    playerLocation = l;
  }

  @Override
  public Cell getPlayerLocation() {
    return playerLocation;
  }

  @Override
  public void addTreasureToPlayer(Treasure t) {
    if (t == null) {
      throw new IllegalArgumentException("Treasure cannot be null");
    }
    playerTreasureList.add(t);
  }

  @Override
  public void removeTreasureFromPlayer() {
    if (playerTreasureList.size() != 0) {
      playerTreasureList.remove(0);
    }
  }

  @Override
  public List<Treasure> getPlayerTreasureList() {
    return new ArrayList<>(playerTreasureList);
  }

  @Override
  public void addArrowToPlayer() {
    noOfArrowsPlayer = noOfArrowsPlayer + 1;
  }

  @Override
  public void removeArrowFromPlayer() {
    noOfArrowsPlayer = noOfArrowsPlayer - 1;
  }

  @Override
  public int getNumberOfArrows() {
    return noOfArrowsPlayer;
  }
}
