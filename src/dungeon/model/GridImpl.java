package dungeon.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Creation of a random dungeon is done here (based off Kruskal Algorithm).
 * The connected edges are then sent back to the dungeon class in
 * order to construct the game.
 */
final class GridImpl implements Grid {
  private final RandomGenerator r;
  private final Cell[][] grid;
  private final int noOfRows;
  private final int noOfColumns;
  private final List<List<Cell>> edges;
  private final int[] root;
  private final int[] rank;
  private final List<List<Cell>> connectedEdges;
  private final List<List<Cell>> leftoverEdges;


  /**
   * The grid creation is done using the values passed for
   * wrapping condition and interconnectivity in dungeon. The
   * connected edges are then returned after following the algorithm
   * to create the connected grid.
   *
   * @param grid the unconnected grid
   * @param isWrapping condition if grid should be wrapping or not
   * @param interconnectivity degree of connectivity
   * @param r random instance
   * @throws IllegalArgumentException if grid is null value
   * @throws IllegalArgumentException if interconnectivity is negative
   */
  public GridImpl(Cell[][] grid, boolean isWrapping, int interconnectivity, RandomGenerator r) {
    if (grid == null || r == null) {
      throw new IllegalArgumentException("Values cannot be null");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Interconnectivity cannot be negative");
    }
    this.r = r;
    this.grid = grid;
    noOfRows = (grid.length);
    noOfColumns = (grid[0].length);
    int size = (noOfRows - 1) * noOfColumns + (noOfColumns - 1) * noOfRows;
    root = new int[size];
    rank = new int[size];
    edges = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      root[i] = i;
      rank[i] = 1;
    }
    connectedEdges = new ArrayList<>();
    leftoverEdges = new ArrayList<>();
    createEdges(isWrapping);
    union();
    unionLeftover(interconnectivity);
    addNeighbourToCell();
  }


  // creates all the possible edges of a grid
  private void createEdges(boolean isWrapping) {
    List<Cell> visited = new ArrayList<>();
    for (int i = 0; i < noOfRows; i++) {
      for (int j = 0; j < noOfColumns; j++) {
        visited.add(grid[i][j]);
        if (getGridCell(i + 1, j) != null && !visited.contains(grid[i + 1][j])) {
          edges.add(Arrays.asList(grid[i][j], grid[i + 1][j]));
        }
        if (getGridCell(i - 1, j) != null && !visited.contains(grid[i - 1][j])) {
          edges.add(Arrays.asList(grid[i][j], grid[i - 1][j]));
        }
        if (getGridCell(i, j + 1) != null && !visited.contains(grid[i][j + 1])) {
          edges.add(Arrays.asList(grid[i][j], grid[i][j + 1]));
        }
        if (getGridCell(i, j - 1) != null && !visited.contains(grid[i][j - 1])) {
          edges.add(Arrays.asList(grid[i][j], grid[i][j - 1]));
        }
      }
    }
    if (isWrapping) {
      for (int j = 0; j < noOfColumns; j++) {
        edges.add(Arrays.asList(grid[noOfRows - 1][j], grid[0][j]));
      }
      for (int i = 0; i < noOfRows; i++) {
        edges.add(Arrays.asList(grid[i][noOfColumns - 1], grid[i][0]));
      }
    }
  }

  // checks if the cell is within the grid
  private Cell getGridCell(int x, int y) {
    try {
      return grid[x][y];
    } catch (ArrayIndexOutOfBoundsException e) {
      return null;
    }
  }

  // locates the cell in the grid
  private int find(int x) {
    if (x == root[x]) {
      return x;
    }
    return root[x] = find(root[x]);
  }


  // connection of edges
  private void union() {
    int ctr = 0;
    Set<Integer> s = new HashSet<>();
    while (ctr < edges.size()) {
      int k = r.nextInt(edges.size());
      if (!s.contains(k)) {
        s.add(k);
        int x = edges.get(k).get(0).getValue();
        int y = edges.get(k).get(1).getValue();

        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
          connectedEdges.add(Arrays.asList(edges.get(k).get(0), edges.get(k).get(1)));
          if (rank[rootX] > rank[rootY]) {
            root[rootY] = rootX;
          } else if (rank[rootX] < rank[rootY]) {
            root[rootX] = rootY;
          } else {
            root[rootY] = rootX;
            rank[rootX] += 1;
          }
        } else {
          leftoverEdges.add(Arrays.asList(edges.get(k).get(0), edges.get(k).get(1)));
        }
        ctr++;
      }
    }
  }


  // connection of leftover edges
  private void unionLeftover(int interconnectivity) {
    for (int i = 0; i < interconnectivity; i++) {
      connectedEdges.add(leftoverEdges.get(i));
    }
  }

  // once edges are connected, the corresponding neighbours are added to the cell
  private void addNeighbourToCell() {
    for (List<Cell> c : connectedEdges) {
      if ((c.get(0).getY() + 1) % noOfColumns == c.get(1).getY()) {
        c.get(0).addNeighbour(Directions.EAST);
        c.get(1).addNeighbour(Directions.WEST);
      }
      else if ((c.get(0).getX() + 1) % noOfRows == c.get(1).getX()) {
        c.get(0).addNeighbour(Directions.SOUTH);
        c.get(1).addNeighbour(Directions.NORTH);
      }
    }
  }

  @Override
  public List<List<Cell>> getConnected() {
    return new ArrayList<>(connectedEdges);
  }
}
