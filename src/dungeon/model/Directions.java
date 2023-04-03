package dungeon.model;

/**
 * A player can make a move in 4 directions in the dungeon
 * - north, south, east, west. North and south are vertical moves in
 * the negative and positive direction. West and east are horizontal
 * moves in the negative and positive direction.
 */
public enum Directions {

  NORTH(-1, 0), EAST(0, 1), SOUTH(1, 0), WEST(0, -1);

  private final int x;
  private final int y;


  private Directions(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
