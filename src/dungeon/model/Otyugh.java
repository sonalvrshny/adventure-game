package dungeon.model;

/**
 * An Otyugh is a type of monster which stays in the dungeon.
 * Otyugh only occupy caves and are never found in tunnels or start cave.
 * Their caves can also contain treasure or other items. They can be
 * detected by their smell. A player entering a cave with an Otyugh
 * that has not been slayed will be killed and eaten.
 */
final class Otyugh implements Monster {
  private int health;

  /**
   * An Otyugh is created starting with health 2. It takes
   * 2 hits to kill an Otyugh.
   */
  public Otyugh() {
    health = 2;
  }

  @Override
  public void reduceHealth() {
    if (health != 0) {
      health = health - 1;
    }
  }

  @Override
  public int getHealth() {
    return health;
  }
}
