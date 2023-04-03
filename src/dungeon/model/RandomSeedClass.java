package dungeon.model;

import java.util.Random;

/**
 * This class implements the RandomGenerator and is used
 * to generate numbers based on seed value passed. This makes the
 * random number generation predictable.
 */
public class RandomSeedClass implements RandomGenerator {
  private final Random rand;

  /**
   * The seed value is fixed here. These values can then be used to
   * predict what the random generator will give.
   */
  public RandomSeedClass(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Number cannot be negative");
    }
    rand = new Random(n);
  }

  @Override
  public int nextInt(int n) {
    return rand.nextInt(n);
  }
}
