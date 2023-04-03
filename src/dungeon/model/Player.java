package dungeon.model;

import java.util.List;

/**
 * This interface is package private to make it readable only.
 * A player is created in the start location of the dungeon and moves
 * through the dungeon collecting treasure from caves.
 */
interface Player {

  /**
   * Sets the player's current location based on the player
   * moves from dungeon.
   *
   * @param l the cell the player is moved to
   * @throws IllegalArgumentException if cell is null
   */
  void setPlayerLocation(Cell l);

  /**
   * Gets the current location of the player.
   *
   * @return current cell the player is in
   */
  Cell getPlayerLocation();

  /**
   * When a player picks up treasure from a cell in the dungeon,
   * the treasure is added to the treasure list of the player.
   *
   * @param t treasure to be added
   * @throws IllegalArgumentException if treasure is null
   */
  void addTreasureToPlayer(Treasure t);

  /**
   * One treasure is removed from the player if they have.
   */
  void removeTreasureFromPlayer();

  /**
   * Gets the list of treasure the player has collected through
   * its movement in the dungeon.
   *
   * @return list of treasure
   */
  List<Treasure> getPlayerTreasureList();

  /**
   * Adds an arrow to the player once they pick it from the dungeon.
   */
  void addArrowToPlayer();

  /**
   * Removes arrow from the player when they shoot in the dungeon.
   */
  void removeArrowFromPlayer();

  /**
   * Gets the number of arrows the player currently has.
   *
   * @return integer value of number of arrows the player has
   */
  int getNumberOfArrows();
}
