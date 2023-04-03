package dungeon.model;


import java.util.List;

/**
 * This interface is package private to make it accessible only within package.
 * A cell is a location on the dungeon which holds the properties
 * of each location. A cell can be updated with neighbours, arrows,
 * treasure, thief and Otyughs according to dungeon requirements.
 */
interface Cell {

  /**
   * The percentage of treasure defined by user is added
   * using this function.
   *
   * @param t treasure to be added to the location
   * @throws IllegalArgumentException if treasure argument is null
   */
  void addTreasureToLocation(Treasure t);

  /**
   * Once a player picks up treasure from the location, it
   * has to be removed from that location.
   *
   * @param t treasure to be removed from the location
   * @throws IllegalArgumentException if treasure argument is null
   */
  void removeTreasureFromLocation(Treasure t);


  /**
   * The frequency of arrows added is based on percentage defined by user.
   */
  void addArrowToLocation();

  /**
   * Once player picks up an arrow, the number of arrows present in that location
   * reduces by 1.
   */
  void removeArrowFromLocation();


  /**
   * Adds an Otyugh to a cave in the dungeon.
   */
  void addMonsterToLocation();

  /**
   * Once an Otyugh is killed, it is removed from its cave.
   */
  void removeMonsterFromLocation();

  /**
   * If a player makes a wrong move more than 2 times, a
   * thief appears and steals one treasure from the player
   * if they have.
   */
  void addThief();

  /**
   * Once the thief steals treasure, he is to be removed
   * from its current location.
   */
  void removeThief();

  /**
   * Returns true if the location has a thief and false
   * if there is no thief.
   *
   * @return true or false based on presence of thief
   */
  boolean getThief();

  /**
   * During the creation of dungeon, each cell location is updated
   * with neighbouring cells.
   *
   * @param d the direction of the cell to be added as neighbour to current cell
   * @throws IllegalArgumentException if direction argument is null
   */
  void addNeighbour(Directions d);


  /**
   * A cell with 2 entrances is a tunnel, otherwise is a cave.
   *
   * @return integer value of the number of entrances to a cell
   */
  int getNoOfEntrances();

  /**
   * The height coordinate of the cell in the dungeon.
   *
   * @return x coordinate which corresponds to height
   */
  int getX();

  /**
   * The width coordinate of the cell in the dungeon.
   *
   * @return y coordinate which corresponds to width
   */
  int getY();

  /**
   * Each cell in the dungeon has a value which can be
   * used to identify the cell number.
   *
   * @return cell value in the dungeon
   */
  int getValue();

  /**
   * Each location can have treasure based on its properties. This
   * list of treasures is stored and can be returned using this
   * function.
   *
   * @return list of treasure present in the location
   */
  List<Treasure> getTreasureList();

  /**
   * Gets the number of arrows present in a cell.
   *
   * @return number of arrows in a cell
   */
  int getNumberOfArrows();

  /**
   * The neighbours that are added to a cell are stored in a list of possible moves.
   * This function is used to return the list.
   *
   * @return list of possible moves from the location
   */
  List<Directions> possibleMoves();

  /**
   * Gets Otyugh which is present in a cell. If not present,
   * the method returns null.
   *
   * @return Monster object present in cell
   */
  Monster getMonster();
}
