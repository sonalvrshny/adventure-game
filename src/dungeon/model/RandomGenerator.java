package dungeon.model;

/**
 * A dungeon requires random number generation for various creation of the
 * dungeon grid. There are three types of implementations of the random generator.
 * One is for randomly creating numbers, one generates numbers based on a predefined
 * list of numbers which is useful for testing purposes, one uses seed value which
 * is a set of predefined values.
 */
public interface RandomGenerator {

  /**
   * Method to return a random number within the limits of 0 and
   * the number defined in the parameters. This limit is inclusive of 0
   * and exclusive of n.
   *
   * @param n the upper limit up to which random numbers generated can be
   * @return the random number integer
   */
  int nextInt(int n);
}
