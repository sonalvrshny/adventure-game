package dungeon.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A dungeon is created with a size determined by rows and columns. The number of paths
 * from one location to another can be increased by interconnectivity. Treasure and arrows
 * are added to the defined percentage of caves. Otyughs are added in caves in the dungeon.
 * If an Otyugh is not slayed by the player, it can eat the player if enters its cave. A
 * dungeon can be wrapped or non wrapped based on wrapping input. Difficulty of game can be
 * 1, 2 or 3. Each level adds higher number of Otyughs to the dungeon. Wrong moves can lead
 * to appearance of thief.
 */
public final class DungeonClass implements Dungeon {
  private final Player p;
  private final RandomGenerator rand;
  private final Cell[][] grid;
  private final int[][] state;
  private final List<Cell> cells;
  private final List<List<Integer>> connectedEdges;
  private Cell startLocation;
  private Cell endLocation;
  private final int rows;
  private final int columns;
  private final int interconnectivity;
  private final int percentTreasure;
  private final boolean isWrapping;
  private final int difficulty;
  private boolean isGameOver;
  private boolean playerWon;
  private int wrongMoves;

  /**
   * The dungeon is created based on number of rows, columns, interconnectivity,
   * difficulty and wrapping condition. The treasures and arrows are then added
   * based on percentage. Otyughs are added based on difficulty level set.
   * A dungeon should be of minimum size 6x6.
   *
   * @param rows              number of rows of the dungeon
   * @param columns           number of columns of the dungeon
   * @param interconnectivity interconnectivity which increases number of paths
   * @param percentTreasure   percent of treasure in caves
   * @param isWrapping        true if dungeon should be wrapping, false if non wrapping
   * @param difficulty        defines the number of Otyughs to add to the dungeon
   * @param rand              type of randomness the dungeon is using
   * @throws IllegalArgumentException if the rows or columns is less than 6
   * @throws IllegalArgumentException if interconnectivity is high for rows and columns
   *                                  defined or is less than 0
   * @throws IllegalArgumentException if percent of treasure is <0 or >100
   * @throws IllegalArgumentException if interconnectivity or difficulty < 0
   */
  public DungeonClass(int rows, int columns, int interconnectivity,
                      int percentTreasure, boolean isWrapping, int difficulty, RandomGenerator rand)
          throws IllegalArgumentException {
    if (rows < 6 || columns < 6) {
      throw new IllegalArgumentException("Minimum size of grid should be 6x6");
    }
    if (!isWrapping && interconnectivity
            > (((rows - 1) * columns + (columns - 1) * rows) - ((rows * columns) - 1))) {
      throw new IllegalArgumentException("Interconnectivity to be less than :"
              + (((rows - 1) * columns + (columns - 1) * rows) - ((rows * columns) - 1)));
    }
    if (isWrapping && interconnectivity
            > ((((rows - 1) * columns + (columns - 1) * rows) + rows + columns)
            - ((rows * columns) - 1))) {
      throw new IllegalArgumentException("Interconnectivity to be less than :"
              + ((((rows - 1) * columns + (columns - 1) * rows) + rows + columns)
              - ((rows * columns) - 1)));
    }
    if (percentTreasure < 0 || percentTreasure > 100) {
      throw new IllegalArgumentException("Treasure percentage to be between 0 and 100");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Interconnectivity should be greater than 0");
    }
    if (difficulty < 1 || difficulty > 3) {
      throw new IllegalArgumentException("Difficulty level to be set as 1,2 or 3");
    }
    this.rand = rand;
    this.grid = new CellClass[rows][columns];
    this.state = new int[rows][columns];
    this.cells = new ArrayList<>();
    this.connectedEdges = new ArrayList<>();
    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = interconnectivity;
    this.percentTreasure = percentTreasure;
    this.isWrapping = isWrapping;
    this.difficulty = difficulty;
    this.p = new PlayerClass();
    this.isGameOver = false;
    this.playerWon = false;
    wrongMoves = 0;
    createGrid();
    createDungeonKruskal();
    createStart();
    createEnd();
    updateCellItems();
  }

  @Override
  public List<Integer> getSettings() {
    List<Integer> list = new ArrayList<>();
    list.add(rows);
    list.add(columns);
    list.add(interconnectivity);
    list.add(percentTreasure);
    list.add(isWrapping ? 0 : 1);
    list.add(difficulty);
    return list;
  }

  // create grid based on number of rows and columns
  private void createGrid() {
    int counter = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        CellClass c = new CellClass(i, j, counter++);
        cells.add(c);
        grid[i][j] = c;
      }
    }
  }

  // connects the edges of the grid based on Kruskal's algorithm
  private void createDungeonKruskal() {
    Grid uf = new GridImpl(grid, isWrapping, interconnectivity, rand);
    for (List<Cell> e : uf.getConnected()) {
      connectedEdges.add(Arrays.asList(e.get(0).getValue(), e.get(1).getValue()));
    }
  }

  // select a random cell as start position
  private void createStart() {
    int r = rand.nextInt(cells.size());
    while (cells.get(r).getNoOfEntrances() == 2) {
      r = rand.nextInt(cells.size());
    }
    startLocation = cells.get(r);
    p.setPlayerLocation(startLocation);
  }

  // find nodes which are at a distance > 5 from start and assign any random as end position
  private void createEnd() {
    Map<Integer, Integer> lessThanKNodes = new HashMap<>();
    nodesUnderK(connectedEdges, startLocation.getValue(), lessThanKNodes, 5);
    List<Cell> possibleEndCells = new ArrayList<>(cells);
    for (Integer i : lessThanKNodes.keySet()) {
      possibleEndCells.removeIf(c -> c.getValue() == i);
    }
    if (possibleEndCells.size() == 0) {
      throw new IllegalArgumentException("Increase size of grid or decrease interconnectivity");
    }
    int r = rand.nextInt(possibleEndCells.size());
    while (possibleEndCells.get(r).getNoOfEntrances() == 2) {
      r = rand.nextInt(possibleEndCells.size());
    }
    endLocation = possibleEndCells.get(r);
  }

  // helper method to get the nodes under distance of k from specified cell
  private void nodesUnderK(List<List<Integer>> graph, int node,
                           Map<Integer, Integer> lessThanKNodes, int k) {
    int v = rows * columns;
    List<List<Integer>> treeTry = new ArrayList<>();

    for (int i = 0; i < v + 1; i++) {
      treeTry.add(new ArrayList<>());
    }
    for (List<Integer> integers : graph) {
      int from = integers.get(0);
      int to = integers.get(1);
      treeTry.get(from).add(to);
      treeTry.get(to).add(from);
    }
    dfs(k, node, -1, treeTry, lessThanKNodes);
  }

  // traversal through graph
  private void dfs(int k, int node, int parent,
                   List<List<Integer>> tree, Map<Integer, Integer> lessThanKNodes) {
    if (k < 0) {
      return;
    }
    lessThanKNodes.put(node, k);
    List<Integer> temp = tree.get(node);

    for (int i : temp) {
      if (i != parent) {
        dfs(k - 1, i, node, tree, lessThanKNodes);
      }
    }
  }

  // adding treasure, arrow and monsters to the dungeon
  private void updateCellItems() {
    List<Cell> caves = new ArrayList<>();
    for (Cell c : cells) {
      if (c.getNoOfEntrances() == 1 || c.getNoOfEntrances() == 3 || c.getNoOfEntrances() == 4) {
        caves.add(c);
      }
    }
    int noToAdd = (percentTreasure * caves.size() / 100);
    int noMonstersToAdd = (difficulty * 20 * caves.size() / 100);
    addTreasure(caves, noToAdd);
    addArrow(noToAdd);
    addMonster(caves, noMonstersToAdd);
  }

  // adding treasure to random caves
  private void addTreasure(List<Cell> c, int n) {
    if (c == null || n < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    List<Treasure> treasures = Arrays.asList(Treasure.RUBY, Treasure.DIAMOND, Treasure.SAPPHIRE);
    for (int i = 0; i < n; i++) {
      c.get(rand.nextInt(c.size())).addTreasureToLocation(treasures.get(rand.nextInt(3)));
    }
  }

  // adding arrows to random locations
  private void addArrow(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    for (int i = 0; i < n; i++) {
      int r1 = rand.nextInt(cells.size());
      cells.get(r1).addArrowToLocation();
    }
  }

  // adding Otyughs to random caves
  private void addMonster(List<Cell> c, int noMonstersToAdd) {
    if (c == null || noMonstersToAdd < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    List<Cell> temp = new ArrayList<>(c);
    endLocation.addMonsterToLocation();
    temp.remove(startLocation);
    temp.remove(endLocation);
    for (int i = 0; i < noMonstersToAdd - 1; i++) {
      int randomCave = rand.nextInt(temp.size());
      temp.get(randomCave).addMonsterToLocation();
      temp.remove(randomCave);
    }
  }


  private Directions defineDirectionFromString(String d) {
    if (d.equalsIgnoreCase("n") || d.equalsIgnoreCase("north")) {
      return Directions.NORTH;
    } else if (d.equalsIgnoreCase("e") || d.equalsIgnoreCase("east")) {
      return Directions.EAST;
    } else if (d.equalsIgnoreCase("s") || d.equalsIgnoreCase("south")) {
      return Directions.SOUTH;
    } else if (d.equalsIgnoreCase("w") || d.equalsIgnoreCase("west")) {
      return  Directions.WEST;
    }
    else {
      throw new IllegalArgumentException("Illegal move!");
    }
  }

  private Cell updateCurrentLocation(Cell cell, Directions d) {
    if (cell == null || d == null) {
      throw new IllegalArgumentException("Invalid arguments!");
    }
    int verticalMove;
    int horizontalMove;
    if (cell.getX() + d.getX() == -1) {
      verticalMove = rows - 1;
    } else {
      verticalMove = (cell.getX() + d.getX()) % rows;
    }
    if (cell.getY() + d.getY() == -1) {
      horizontalMove = columns - 1;
    } else {
      horizontalMove = (cell.getY() + d.getY()) % columns;
    }
    return (grid[verticalMove][horizontalMove]);
  }

  @Override
  public void movePlayer(String direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    Cell currentCell = p.getPlayerLocation();
    Directions d = defineDirectionFromString(direction);
    if (!p.getPlayerLocation().possibleMoves().contains(d)) {
      wrongMoves++;
      if (wrongMoves == 3) {
        currentCell.addThief();
        p.removeTreasureFromPlayer();
        wrongMoves = 0;
        throw new IllegalArgumentException("A thief came and stole your "
                + "treasure cause of bad move choices");
      }
      throw new IllegalArgumentException(d + " does not have an exit. "
              + "Player stays in same location");
    }

    if (p.getPlayerLocation().possibleMoves().contains(d)) {
      p.setPlayerLocation(updateCurrentLocation(p.getPlayerLocation(), d));
    }
    currentCell.removeThief();
    gameOverCondition();
    gameState();
  }


  @Override
  public void pickUpTreasure() {
    if (p.getPlayerLocation().getTreasureList().size() != 0) {
      for (Treasure t : p.getPlayerLocation().getTreasureList()) {
        p.addTreasureToPlayer(t);
        p.getPlayerLocation().removeTreasureFromLocation(t);
      }
    }
    else {
      throw new IllegalStateException("There is no treasure to pick up here");
    }
  }

  @Override
  public void pickUpArrow() {
    if (p.getPlayerLocation().getNumberOfArrows() != 0) {
      p.addArrowToPlayer();
      p.getPlayerLocation().removeArrowFromLocation();
    }
    else {
      throw new IllegalStateException("There are no arrows to pick up here");
    }
  }

  @Override
  public String displayLocationDescription() {
    if (playerWon) {
      return "You have reached the end and won the game!";
    }
    else if (isGameOver) {
      return "Oh no! You have been eaten by the Otyugh. Game Over!";
    }
    else {
      if (displayItems(p.getPlayerLocation().getTreasureList(),
              p.getPlayerLocation().getNumberOfArrows()).size() != 0) {
        return "Here, you find: " + displayItems(p.getPlayerLocation().getTreasureList(),
                p.getPlayerLocation().getNumberOfArrows()) + "\n"
               + "You can move in the directions: " + locationPossibleMoves();
      }
      else {
        return "You can move in the directions: " + locationPossibleMoves();
      }
    }
  }

  @Override
  public String displayPlayerDescription() {
    return "You have collected: " + displayItems(p.getPlayerTreasureList(), p.getNumberOfArrows());
  }

  @Override
  public List<Treasure> locationTreasure(int n) {
    return cells.get(n).getTreasureList();
  }

  @Override
  public List<Directions> locationMoves(int n) {
    return cells.get(n).possibleMoves();
  }

  @Override
  public int locationArrows(int n) {
    return cells.get(n).getNumberOfArrows();
  }

  @Override
  public Monster locationMonster(int n) {
    return cells.get(n).getMonster();
  }

  @Override
  public boolean locationThief(int n) {
    return cells.get(n).getThief();
  }

  @Override
  public int playerArrows() {
    return p.getNumberOfArrows();
  }

  @Override
  public int playerRuby() {
    int count = 0;
    for (Treasure t : p.getPlayerTreasureList()) {
      if (t == Treasure.RUBY) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int playerSapphire() {
    int count = 0;
    for (Treasure t : p.getPlayerTreasureList()) {
      if (t == Treasure.SAPPHIRE) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int playerDiamond() {
    int count = 0;
    for (Treasure t : p.getPlayerTreasureList()) {
      if (t == Treasure.DIAMOND) {
        count++;
      }
    }
    return count;
  }

  private List<String> displayItems(List<Treasure> l, int a) {
    if (l == null || a < 0) {
      throw new IllegalArgumentException("Invalid arguments!");
    }
    List<String> output = new ArrayList<>();
    int countRuby = 0;
    int countSapphire = 0;
    int countDiamond = 0;
    int countArrow = 0;
    Map<Treasure, Integer> treasureMap = new HashMap<>();
    treasureMap.put(Treasure.RUBY, countRuby);
    treasureMap.put(Treasure.SAPPHIRE, countSapphire);
    treasureMap.put(Treasure.DIAMOND, countDiamond);
    for (Treasure t : l) {
      int count = treasureMap.getOrDefault(t, 0);
      treasureMap.put(t, count + 1);
    }
    for (int i = 0; i < a; i++) {
      countArrow++;
    }
    countRuby = treasureMap.get(Treasure.RUBY);
    countSapphire = treasureMap.get(Treasure.SAPPHIRE);
    countDiamond = treasureMap.get(Treasure.DIAMOND);
    if (countRuby != 0) {
      output.add(countRuby + " ruby");
    }
    if (countSapphire != 0) {
      output.add(countSapphire + " sapphire");
    }
    if (countDiamond != 0) {
      output.add(countDiamond + " diamond");
    }
    if (countArrow != 0) {
      output.add(countArrow + " arrow");
    }
    return output;
  }


  @Override
  public List<String> locationPossibleMoves() {
    List<String> output = new ArrayList<>();
    for (Directions d : p.getPlayerLocation().possibleMoves()) {
      output.add(d.toString());
    }
    return output;
  }

  private Map<Cell, Integer> detectMonster() {
    Map<Integer, Integer> lessThanKNode = new HashMap<>();
    Map<Cell, Integer> monsterNearby = new HashMap<>();
    nodesUnderK(connectedEdges, getCurrentLocation(), lessThanKNode, 2);
    for (Integer i : lessThanKNode.keySet()) {
      for (Cell c : cells) {
        if (c.getValue() == i && c.getMonster() != null) {
          monsterNearby.put(c, lessThanKNode.get(i));
        }
      }
    }
    return monsterNearby;
  }


  @Override
  public String isMonsterClose() {
    Map<Cell, Integer> monsterNearby = new HashMap<>(detectMonster());
    if (monsterNearby.size() == 1 && monsterNearby.containsValue(0)) {
      return ("You smell something bad nearby");
    } else if (monsterNearby.size() == 1 && monsterNearby.containsValue(1)) {
      return ("The smell just got worse");
    } else if (monsterNearby.size() > 1) {
      return ("The smell just got worse");
    }
    return "";
  }

  @Override
  public String shootArrow(String direction, int distance) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    if (distance < 1 || distance > 5) {
      throw new IllegalArgumentException("The arrow can only travel between 1 and 5 caves!");
    }
    if (p.getNumberOfArrows() == 0) {
      throw new IllegalStateException("No arrows to shoot");
    }
    Directions d = defineDirectionFromString(direction);
    Cell currentCell = p.getPlayerLocation();
    while (distance > 0) {
      if (currentCell.possibleMoves().contains(d)) {
        currentCell = (updateCurrentLocation(currentCell, d));
      }
      else {
        return "You shoot an arrow into the darkness";
      }
      if (currentCell.getNoOfEntrances() == 2) {
        d = updateDirection(d, currentCell.possibleMoves());
      }
      else {
        distance --;
      }
    }
    p.removeArrowFromPlayer();
    if (currentCell.getMonster() != null && currentCell.getMonster().getHealth() > 1) {
      currentCell.getMonster().reduceHealth();
      return "There is a loud howl from nearby!";
    } else if (currentCell.getMonster() != null && currentCell.getMonster().getHealth() == 1) {
      currentCell.getMonster().reduceHealth();
      currentCell.removeMonsterFromLocation();
      return "There is a loud howl from nearby! You killed the monster";
    } else {
      return "You shoot an arrow into the darkness";
    }
  }

  private Directions updateDirection(Directions d, List<Directions> moves) {
    if (d == null || moves == null) {
      throw new IllegalArgumentException("Invalid arguments!");
    }
    if (moves.contains(d)) {
      return d;
    }
    moves.removeIf(direction -> direction.getX() == d.getX() || direction.getY() == d.getY());
    return moves.get(0);
  }


  @Override
  public int getStartLocation() {
    return startLocation.getValue();
  }

  @Override
  public int getEndLocation() {
    return endLocation.getValue();
  }

  @Override
  public int getCurrentLocation() {
    return p.getPlayerLocation().getValue();
  }

  @Override
  public int getCurrentLocationX() {
    return p.getPlayerLocation().getX();
  }

  @Override
  public int getCurrentLocationY() {
    return p.getPlayerLocation().getY();
  }

  @Override
  public List<Cell> getCells() {
    List<Cell> copy = new ArrayList<>();
    for (Cell c : cells) {
      copy.add(new CellClass(c));
    }
    return copy;
  }

  @Override
  public List<List<Integer>> getConnectedEdges() {
    return new ArrayList<>(connectedEdges);
  }


  private void gameOverCondition() {
    if (p.getPlayerLocation() == endLocation && p.getPlayerLocation().getMonster() == null) {
      playerWon = true;
    }
    else if (p.getPlayerLocation() == endLocation
            && p.getPlayerLocation().getMonster().getHealth() == 1) {
      int gameEnd50Chance = rand.nextInt(2);
      if (gameEnd50Chance == 1) {
        isGameOver = true;
      }
      else {
        playerWon = true;
      }
    }
    if (p.getPlayerLocation().getMonster() != null) {
      if (p.getPlayerLocation().getMonster().getHealth() == 1) {
        int gameEnd50Chance = rand.nextInt(2);
        if (gameEnd50Chance == 1) {
          isGameOver = true;
        }
      }
      else if (p.getPlayerLocation().getMonster().getHealth() == 2) {
        isGameOver = true;
      }
    }
  }

  @Override
  public boolean gameOver() {
    return isGameOver || playerWon;
  }

  @Override
  public int[][] gameState() {
    state[startLocation.getX()][startLocation.getY()] = 1;
    state[p.getPlayerLocation().getX()][p.getPlayerLocation().getY()] = 1;
    return state;
  }
}
