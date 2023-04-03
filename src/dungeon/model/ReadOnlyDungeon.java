package dungeon.model;

import java.util.List;

/**
 * This interface is read-only interface of the dungeon.
 * The information of the game's current state can be accessed
 * through this interface.
 */
public interface ReadOnlyDungeon {

  /**
   * Get the specs of the dungeon currently in play.
   *
   * @return list of the specs of the dungeon
   */
  List<Integer> getSettings();

  /**
   * List of the edges that are connected in the dungeon created.
   *
   * @return List of the values of the cells connected in the dungeon
   */
  List<List<Integer>> getConnectedEdges();

  /**
   * Location description is used to display what are the conditions
   * of the current location of the player. This includes what items are present
   * in that location, what are the possible moves and whether the player won or
   * got eaten by the Otyugh.
   *
   * @return A string displaying the current location description where player is
   */
  String displayLocationDescription();

  /**
   * Location description is used to display what are the conditions of the player.
   * This includes what items the player has collected till now.
   *
   * @return A string displaying the description of the player
   */
  String displayPlayerDescription();

  /**
   * Each location has a list of possible moves from which the player cna exit.
   * This method gets the possible moves from current location of player.
   *
   * @return List of directions in string format
   */
  List<String> locationPossibleMoves();

  /**
   * A monster smell can be detected when an Otyugh is close by. If an Otyugh is
   * 2 positions away, a strong smell is detected. Once the player gets 1 position
   * closer or there are multiple Otyughs within 2 positions, the smell gets worse.
   *
   * @return a string indicating whether an Otyugh is close
   */
  String isMonsterClose();

  /**
   * Gets the start location of the dungeon. This location
   * can only be a cave and is randomly chosen when the dungeon is created.
   *
   * @return integer value of the cell that is assigned as start location
   */
  int getStartLocation();

  /**
   * Gets the end location of the dungeon. This location
   * can only be a cave and is randomly chosen when the dungeon is created.
   * The minimum distance between start and end should be 5.
   *
   * @return integer value of the cell that is assigned as end location
   */
  int getEndLocation();


  /**
   * The current location of the player in the dungeon.
   *
   * @return integer value of the cell that the player is currently in
   */
  int getCurrentLocation();

  /**
   * The current location of the player in the dungeon in terms of width.
   *
   * @return width value of the cell that the player is currently in
   */
  int getCurrentLocationX();

  /**
   * The current location of the player in the dungeon in terms of height.
   *
   * @return height value of the cell that the player is currently in
   */
  int getCurrentLocationY();

  /**
   * When the dungeon is created, each location is stored as a cell object
   * which holds all the properties of that location. This method returns the
   * list of cells.
   *
   * @return list of the cell objects of the dungeon created
   */
  List<Cell> getCells();

  /**
   * Returns a true or false condition based on game condition.
   * A game is over if the player gets eaten by an Otyugh or the player
   * successfully reaches end location and wins the game.
   *
   * @return a true or false condition based on game
   */
  boolean gameOver();

  /**
   * Array of 1s and 0s indicating whether a player has
   * visited a certain location in the dungeon or not.
   *
   * @return array indicating visited cells
   */
  int[][] gameState();

  /**
   * Gets the list of treasure present in the location of cell
   * identified by the value n.
   *
   * @param n cell identifier
   * @return list of treasure
   */
  List<Treasure> locationTreasure(int n);

  /**
   * Gets the list of moves from the location of cell
   * identified by the value n.
   *
   * @param n cell identifier
   * @return list of possible moves
   */
  List<Directions> locationMoves(int n);

  /**
   * Gets the list of arrows present in the location of cell
   * identified by the value n.
   *
   * @param n cell identifier
   * @return number of arrows
   */
  int locationArrows(int n);

  /**
   * Gets the Monster present in the location of cell
   * identified by the value n.
   *
   * @param n cell identifier
   * @return Monster object
   */
  Monster locationMonster(int n);

  /**
   * Gets the boolean value for thief present in the location of cell
   * identified by the value n.
   *
   * @param n cell identifier
   * @return true or false depending on thief presence
   */
  boolean locationThief(int n);

  /**
   * Gets the number of arrows player has at the present state.
   *
   * @return number of arrows
   */
  int playerArrows();

  /**
   * Gets the number of rubies player has at the present state.
   *
   * @return number of rubies
   */
  int playerRuby();

  /**
   * Gets the number of sapphires player has at the present state.
   *
   * @return number of sapphires
   */
  int playerSapphire();

  /**
   * Gets the number of diamonds player has at the present state.
   *
   * @return number of diamonds
   */
  int playerDiamond();
}
