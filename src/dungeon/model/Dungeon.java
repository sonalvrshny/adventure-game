package dungeon.model;


/**
 * A dungeon is a network of caves and tunnels interconnected for a player to move through.
 * A random dungeon is created based on specs provided. The dungeon contains arrows and treasure
 * for the player to pick up. A player always starts with 3 arrows. A thief can steal treasure.
 * Every end location has an Otyugh which can eat the player. More Otyughs are added in random caves
 * based on difficulty level. A player can kill an Otyugh by shooting an arrow at it twice.
 * A player wins by reaching the end cave or dies by an Otyugh.
 */
public interface Dungeon extends ReadOnlyDungeon {

  /**
   * A player can move in 4 directions from a location - North, South, East, West.
   * From the player's current location, a player can only move in the possible moves of that
   * location. If a player tries to move in a direction which is not in the possible moves,
   * they stay in the same location.
   *
   * @param d direction to move player in
   * @throws IllegalArgumentException if d is not a valid move
   * @throws IllegalStateException if game is already over
   */
  void movePlayer(String d) throws IllegalArgumentException;

  /**
   * A player can pick up treasure present in the player's current location.
   * If the location has more than 1 treasure, the player picks up all the treasure.
   * If the location does not have any treasure, no treasure gets added to the player.
   *
   * @throws IllegalStateException if there is no treasure to pick up
   */
  void pickUpTreasure() throws IllegalStateException;


  /**
   * A player can pick up arrow present in the player's current location.
   * A player can pick up only one arrow at a time. If the location does not have
   * any arrows, no arrow gets added to the player.
   *
   * @throws IllegalStateException if there is no arrow to pick up
   */
  void pickUpArrow();


  /**
   * An Otyugh can be killed when a player shoots at it and hits it twice.
   * The first hit by a player using an arrow injures the Otyugh. The second hit
   * kills it. If there is no Otyugh present where the player shoots, the
   * arrow is lost in the darkness. An arrow travels the distance specified
   * by the player (number of caves).
   *
   * @param d the direction player wants to shoot
   * @param distance the number of caves the arrow should travel
   * @return a string indicating what happened after the player shot an arrow
   * @throws IllegalArgumentException if the distance is not between 1 and 5
   * @throws IllegalArgumentException if player has no more arrows to shoot
   * @throws IllegalArgumentException if direction specified is not valid
   */
  String shootArrow(String d, int distance) throws IllegalArgumentException;

}
