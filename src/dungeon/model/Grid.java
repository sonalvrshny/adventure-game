package dungeon.model;

import java.util.List;

/**
 * This interface is package private to make it readable only.
 * The grid to be used for dungeon is passed here for connection
 * of edges. The grid is created using the conditions passed in the
 * dungeon. After creation, the connected edges are returned
 * to the dungeon.
 */
interface Grid {

  /**
   * List of connected cells in the grid after its creation.
   *
   * @return list of connected cells
   */
  List<List<Cell>> getConnected();
}
