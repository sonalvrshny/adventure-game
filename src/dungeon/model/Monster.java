package dungeon.model;

/**
 * Each dungeon can have monsters present in them which can eat the player.
 * There is always one monster present in the end cave. The number of monsters
 * in the dungeon is specified while creating the dungeon. A monster can only
 * be present in caves.
 */
interface Monster {

  /**
   * It takes a certain number of hits for a monster to be slayed.
   * This is used to track the number of hits a monster takes.
   */
  void reduceHealth();

  /**
   * Gets how many times more the monster has to be hit in order
   * to be slayed.
   *
   * @return the current health of the monster
   */
  int getHealth();
}
