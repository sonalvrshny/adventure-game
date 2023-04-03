package dungeon.model;

import java.util.Arrays;
import java.util.List;

/**
 * This class implements the RandomGenerator and is used
 * to generate numbers from a predefined list. This makes the
 * random number generation predictable.
 */
public class PredictableClass implements RandomGenerator {
  private int i;
  private final List<Integer> predefinedValues;


  /**
   * The predefined set of values are determined using
   * this constructor. These values can then be used to
   * predict what the random generator will give.
   *
   * @param values predefined list of values
   */
  public PredictableClass(Integer... values) {
    if (values == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    i = 0;
    predefinedValues = Arrays.asList(values);
  }

  @Override
  public int nextInt(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Number cannot be negative");
    }
    int output = predefinedValues.get(i);
    i++;
    return output;
  }
}
