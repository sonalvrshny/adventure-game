package dungeon.controller.commands;

import dungeon.controller.CommandController;
import dungeon.model.Dungeon;
import dungeon.view.DungeonView;

import java.io.IOException;

/**
 * The move command is used to call the movePlayer method of the dungeon model.
 * Once a user types in the move command, the next prompt would ask in which
 * direction the player wants to move. If an invalid value is entered, user should
 * re-enter the next command.
 */
public final class Move implements CommandController {
  private final String direction;

  /**
   * Move in the direction the player wants to move.
   *
   * @param direction string value of direction to move in
   */
  public Move(String direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    this.direction = direction;
  }


  @Override
  public void commandGo(Dungeon dungeon, Appendable out) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (out == null) {
      throw new IllegalArgumentException("Log cannot be null");
    }
    try {
      try {
        dungeon.movePlayer(direction);
      } catch (IllegalArgumentException e) {
        out.append(e.getMessage()).append("\n");
        return;
      }
      if (!dungeon.gameOver()) {
        out.append("You moved towards ").append(direction).append("\n");
        out.append(dungeon.displayLocationDescription()).append("\n");
        if (!dungeon.isMonsterClose().equals("")) {
          out.append(dungeon.isMonsterClose()).append("\n");
        }
      }
      if (dungeon.gameOver()) {
        out.append(dungeon.displayLocationDescription()).append("\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Cannot append!");
    }
  }

  @Override
  public void mvcGo(Dungeon dungeon, DungeonView view) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("Log cannot be null");
    }
    try {
      dungeon.movePlayer(direction);
    } catch (IllegalArgumentException e) {
      view.displayStatus(e.getMessage());
      return;
    }
    if (!dungeon.gameOver()) {
      view.displayStatus("You moved towards " + direction);
    }
    else {
      view.displayStatus(dungeon.displayLocationDescription());
    }
  }
}
