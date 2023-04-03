package dungeon.model;

import java.util.Random;


/**
 * This class implements the RandomGenerator and is used
 * to generate random numbers as required by the dungeon, and it's
 * creation.
 */
public class RandomClass implements RandomGenerator {
  private final Random rand;

  /**
   * The rand variable initialised here uses the
   * nextInt method of Java.Random.
   */
  public RandomClass() {
    rand = new Random();
  }

  @Override
  public int nextInt(int n) {
    return rand.nextInt(n);
  }
}
